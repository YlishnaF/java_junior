package lession4;

import java.sql.*;

public class JDBSTest {

    // JDBC URL, username and password of MySQL server
    private static final String url = "jdbc:mysql://localhost:3306";
    private static final String user = "root";
    private static final String password = "17485133";

    // JDBC variables for opening and managing connection
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    public static void main(String args[]) {
        String query = "CREATE TABLE IF NOT EXISTS test.book (\n" +
                "    id INT NOT NULL,\n" +
                "    name VARCHAR(255),\n" +
                "    author VARCHAR(255),\n" +
                " PRIMARY KEY (`id`));";

        try {
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);

            // getting Statement object to execute query
            stmt = con.createStatement();
            stmt.execute("drop table test.book");
            stmt.execute(query);
            stmt.execute("INSERT INTO test.book (`id`,`name`,`author`)\n"+
                          "VALUES (1,'Гарри Поттер 1','Джоан Роулинг');");
            stmt.execute("INSERT INTO test.book (`id`,`name`,`author`)\n"+
                    "VALUES (2,'Гарри Поттер 2','Джоан Роулинг');");
            stmt.execute("INSERT INTO test.book (`id`,`name`,`author`)\n"+
                    "VALUES (3,'Гарри Поттер 3','Джоан Роулинг');");
            stmt.execute("INSERT INTO test.book (`id`,`name`,`author`)\n"+
                    "VALUES (4,'Гарри Поттер 4','Джоан Роулинг');");
            stmt.execute("INSERT INTO test.book (`id`,`name`,`author`)\n"+
                    "VALUES (5,'Гарри Поттер 5','Джоан Роулинг');");
            stmt.execute("INSERT INTO test.book (`id`,`name`,`author`)\n"+
                    "VALUES (6,'Гарри Поттер 6','Джоан Роулинг');");
            stmt.execute("INSERT INTO test.book (`id`,`name`,`author`)\n"+
                    "VALUES (7,'Смерть на Ниле','Агата Крсисти');");
            stmt.execute("INSERT INTO test.book (`id`,`name`,`author`)\n"+
                    "VALUES (8,'Война и мир','Лев Толстой');");
            stmt.execute("INSERT INTO test.book (`id`,`name`,`author`)\n"+
                    "VALUES (9,'Преступление и наказание','Федор Достоевский');");
            stmt.execute("INSERT INTO test.book (`id`,`name`,`author`)\n"+
                    "VALUES (10,'Братья Карамазовы','Федор Достоевский');");
            rs = stmt.executeQuery("select*from test.book where author='Джоан Роулинг'");
            while (rs.next()){
                System.out.println(rs.getString(3) + " " + rs.getString(2) + " " + rs.getInt(1));
            }


        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //close connection ,stmt and resultset here
            try {
                con.close();
            } catch (SQLException se) { /*can't do anything */ }
            try {
                stmt.close();
            } catch (SQLException se) { /*can't do anything */ }
            try {
                rs.close();
            } catch (SQLException se) { /*can't do anything */ }
        }
    }

}
