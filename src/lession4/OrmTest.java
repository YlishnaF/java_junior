package lession4;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;


public class OrmTest {


    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("resources/hibernate.cfg.xml")
                .build();
        SessionFactory sessionFactory = new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();

        Session session = sessionFactory.openSession();
        Books book = new Books("Гарри Поттер 1","Джоан Роулинг");
        session.beginTransaction();
        session.save(book);
        book = new Books("Гарри Поттер 2","Джоан Роулинг");
        session.save(book);
        book = new Books("Гарри Поттер 3","Джоан Роулинг");
        session.save(book);
        book = new Books("Гарри Поттер 4","Джоан Роулинг");
        session.save(book);
        book = new Books("Гарри Поттер 5","Джоан Роулинг");
        session.save(book);
        book = new Books("Гарри Поттер 6","Джоан Роулинг");
        session.save(book);
        book = new Books("Смерть на Ниле","Агата Крсисти");
        session.save(book);
        book = new Books("Смерть на Ниле","Агата Крсисти");
        session.save(book);
        book = new Books("Война и мир","Лев Толстой");
        session.save(book);
        book = new Books("Преступление и наказание","Федор Достоевский");
        session.save(book);
        book = new Books("Братья Карамазовы","Федор Достоевский");
        session.save(book);
        session.getTransaction().commit();

        List<Books> booksList = session.createQuery("from Books", Books.class).getResultList();
        booksList.stream().filter(b->b.getAuthor().equals("Федор Достоевский")).forEach(System.out::println);

        Author author = new Author("Рей Бредбери");
        Book book1 = new Book("451 по Фаренгейту");
        book1.setAuthor(author);
        session.save(book1);
        author.addBook(book1);
        Book book2 = new Book("Вино из одуванчиков");
        book2.setAuthor(author);
        author.addBook(book2);
        session.save(book2);
        session.save(author);
        session.getTransaction().commit();
        session.close();
    }
}
