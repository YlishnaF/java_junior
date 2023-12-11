package lession1;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        /**
           * 1. Создать список из 1_000 рандомных чисел от 1 до 1_000_000
           * 1.1 Найти максимальное
           * 2.2 Все числа, большие, чем 500_000, умножить на 5, отнять от них 150 и просуммировать
           * 2.3 Найти количество чисел, квадрат которых меньше, чем 100_000
         */
        Stream<Integer> stream = Stream.generate(() -> new Random().nextInt(1000000)).limit(1000);
        List<Integer> list = stream.collect(Collectors.toList());
        Integer max = list.stream().max(Integer::compareTo).get();
        System.out.println("Максимальное число " + max);
        long sum = list.stream().filter(x -> x > 500000).map(x -> x * 5 - 150).mapToInt(Integer::intValue).sum();
        System.out.println("Сумма " + sum);
        long count = list.stream().map(x -> x * x).filter(x -> x < 100000).count();
        System.out.println("Квадрат числа меньше 100000, количество " + count);

        /**
         * 2. Создать класс Employee (Сотрудник) с полями: String name, int age, double salary, String department
         * 2.1 Создать список из 10-20 сотрудников
         * 2.2 Вывести список всех различных отделов (department) по списку сотрудников
         * 2.3 Всем сотрудникам, чья зарплата меньше 10_000, повысить зарплату на 20%
         * 2.4 * Из списка сотрудников с помощью стрима создать Map<String, List<Employee>> с отделами и сотрудниками внутри отдела
         * 2.5 * Из списока сорудников с помощью стрима создать Map<String, Double> с отделами и средней зарплатой внутри отдела
         */
        List<Employee> employees = List.of(
                new Employee("Иван", 34,120000, "А"),
                new Employee("Вера", 23, 35000, "Б"),
                new Employee("Катя", 23, 5000, "А"),
                new Employee("Женя", 23, 3000, "В"),
                new Employee("Юра", 23, 18000, "Б"),
                new Employee("Света", 23, 55000, "С"),
                new Employee("Алла", 23, 43000, "А"),
                new Employee("Саша", 23, 15000, "В"),
                new Employee("Виктор", 23, 75000, "С"),
                new Employee("Гена", 23, 38000, "Б")
        );

        employees.stream().map(e->e.department).distinct().forEach(System.out::println);
        employees.stream().filter(e->e.getSalary()<10000).map(e->{
            return new Employee(e.name, e.age, e.salary*1.2, e.department);
        }).forEach(e-> {
            System.out.printf("Сотрудник %s теперь получает %s\n", e.name, e.salary);
        });

        Map<String, List<Employee>> listDepartment= employees.stream().collect(Collectors.groupingBy(Employee::getDepartment));

        Map<String, Double> averageSalary = employees.stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.averagingDouble(Employee::getSalary)));

        averageSalary.forEach((key, value) -> System.out.println(key + " " + value));
    }


}