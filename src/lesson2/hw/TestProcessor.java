package lesson2.hw;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TestProcessor {

  /**
   * Данный метод находит все void методы без аргументов в классе, и запускеет их.
   * <p>
   * Для запуска создается тестовый объект с помощью конструткора без аргументов.
   */
  public static void runTest(Class<?> testClass) {
    final Constructor<?> declaredConstructor;
    try {
      declaredConstructor = testClass.getDeclaredConstructor();
    } catch (NoSuchMethodException e) {
      throw new IllegalStateException("Для класса \"" + testClass.getName() + "\" не найден конструктор без аргументов");
    }

    final Object testObj;
    try {
      testObj = declaredConstructor.newInstance();
    } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
      throw new RuntimeException("Не удалось создать объект класса \"" + testClass.getName() + "\"");
    }


    List<Method> testMethods = new ArrayList<>();
    List<Method> beforeMethods = new ArrayList<>();
    List<Method> afterMethods = new ArrayList<>();
    for (Method method : testClass.getDeclaredMethods()) {
      checkTestMethod(method);
      if(method.isAnnotationPresent(BeforeEach.class)){
        beforeMethods.add(method);
      }
      if (method.isAnnotationPresent(Test.class)) {
        testMethods.add(method);
      }
      if(method.isAnnotationPresent(AfterEach.class)){
        afterMethods.add(method);
      }
    }
    testMethods.sort(new Comparator<Method>() {
      @Override
      public int compare(Method o1, Method o2) {
        return o1.getAnnotation(Test.class).order() - o2.getAnnotation(Test.class).order();
      }
    });
    beforeMethods.forEach(x-> System.out.println("Готовим к запуску метод " + x.getName()));
    testMethods.forEach(it -> runTest(it, testObj));
    afterMethods.forEach(x-> System.out.println("Завершаем метод " + x.getName()));
  }


  private static void checkTestMethod(Method method) {
    if (!method.getReturnType().isAssignableFrom(void.class) || method.getParameterCount() != 0) {
      throw new IllegalArgumentException("Метод \"" + method.getName() + "\" должен быть void и не иметь аргументов");
    }
  }

  private static void runTest(Method testMethod, Object testObj) {
    try {
      testMethod.invoke(testObj);
    } catch (InvocationTargetException | IllegalAccessException e) {
      throw new RuntimeException("Не удалось запустить тестовый метод \"" + testMethod.getName() + "\"");
    } catch (AssertionError e) {

    }
  }

}
