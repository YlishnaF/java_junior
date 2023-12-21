package lession3;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.stream.Collectors;

public class SerTest {

    static String fileName;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Person person = new Person("Name", 4);
        writeObject(person);
        System.out.println(readObject(fileName));
    }

    public static void writeObject(Serializable ob) throws IOException {
        fileName = ob.getClass().getCanonicalName() + "_" + UUID.randomUUID().toString() + ".txt";
        try (FileOutputStream fo = new FileOutputStream(fileName); ObjectOutputStream os = new ObjectOutputStream(fo);){
            os.writeObject(ob);
        }
    }

    public static String readObject(String fileName) throws IOException, ClassNotFoundException {

        if (findFile("C:\\Users\\ylish\\IdeaProjects\\java_junior")) {
            try(FileInputStream fi = new FileInputStream(fileName); ObjectInputStream os = new ObjectInputStream(fi)){
                Person person = (Person) os.readObject();
                return person.toString();
            }
        }
        return "Файла не существует";

    }

    public static boolean findFile(String dir) throws IOException {
        return Files.walk(Paths.get(dir))
                .filter(Files::isRegularFile)
                .filter(x -> x.getFileName().toString().equals(fileName)).count() > 0;

    }

    public static class Person implements Serializable {
        String name;
        int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
