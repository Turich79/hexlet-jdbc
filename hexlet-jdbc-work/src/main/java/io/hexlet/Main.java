package io.hexlet;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws SQLException {
        var conn = DriverManager.getConnection("jdbc:h2:mem:hexlet_test");

        var sql = "CREATE TABLE users (id BIGINT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(255), phone VARCHAR(255))";
        try (var statement = conn.createStatement()) {
            statement.execute(sql);
        }

        var dao = new UserDAO(conn);

        var user = new User("Ivan", "888999888");
        System.out.println("Пустой ID:" + user.getId());
        dao.save(user);
        System.out.println("Заполненный ID:" + user.getId());

        var user2 = dao.find(user.getId()).get();
        System.out.println("Равенство " + (user2.getId() == user.getId()));

        var user3 = new User("Lena", "8889233888");
        dao.save(user3);
        dao.del(user);

        var sql3 = "SELECT * FROM users";
        try (var statement3 = conn.createStatement()) {
            var resultSet = statement3.executeQuery(sql3);
            while (resultSet.next()) {
                System.out.println(resultSet.getLong("id"));
                System.out.println(resultSet.getString("username"));
                System.out.println(resultSet.getString("phone"));
            }
        }
        conn.close();
    }

    public static void met1() throws SQLException {
        // Создаем соединение с базой в памяти
        // База создается прямо во время выполнения этой строчки
        // Здесь hexlet_test — это имя базы данных
        var conn = DriverManager.getConnection("jdbc:h2:mem:hexlet_test");

        var sql = "CREATE TABLE users (id BIGINT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(255), phone VARCHAR(255))";
        // Чтобы выполнить запрос, создадим объект statement
        ////Вариант 1
//        var statement = conn.createStatement();
//        statement.execute(sql);
//        statement.close(); // В конце закрываем
        ////Вариант 2
        try (var statement = conn.createStatement()) {
            statement.execute(sql);
        }

        var sql2 = "INSERT INTO users (username, phone) VALUES ('tommy', '123456789')";
//        var statement2 = conn.createStatement();
//        statement2.executeUpdate(sql2);
//        statement2.close();
        try (var statement2 = conn.createStatement()) {
            statement2.executeUpdate(sql2);
        }

        var sql3 = "SELECT * FROM users";
//        var statement3 = conn.createStatement();
//        // Здесь вы видите указатель на набор данных в памяти СУБД
//        var resultSet = statement3.executeQuery(sql3);
//        // Набор данных — это итератор
//        // Мы перемещаемся по нему с помощью next() и каждый раз получаем новые значения
//        while (resultSet.next()) {
//            System.out.println(resultSet.getString("username"));
//            System.out.println(resultSet.getString("phone"));
//        }
//        statement3.close();
        try (var statement3 = conn.createStatement()) {
            var resultSet = statement3.executeQuery(sql3);
            while (resultSet.next()) {
                System.out.println(resultSet.getString("username"));
                System.out.println(resultSet.getString("phone"));
            }
        }

        System.out.println("Vstavka 2 users");
        var sql4 = "INSERT INTO users (username,phone) VALUES(?,?)";
        try (var preperedStatement = conn.prepareStatement(sql4, Statement.RETURN_GENERATED_KEYS)) {
            preperedStatement.setString(1, "Pasha");
            preperedStatement.setString(2, "888999888");
            preperedStatement.executeUpdate();

            preperedStatement.setString(1, "Misha");
            preperedStatement.setString(2, "877999778");
            preperedStatement.executeUpdate();
        }

        System.out.println("vyvod users");
        sql3 = "SELECT * FROM users";
        try (var statement3 = conn.createStatement()) {
            var resultSet = statement3.executeQuery(sql3);
            while (resultSet.next()) {
                System.out.println(resultSet.getString("username"));
                System.out.println(resultSet.getString("phone"));
            }
        }

        System.out.println("delete 1 users");
        sql4 = "DELETE FROM users WHERE username = ?;";
        try (var preperedStatement = conn.prepareStatement(sql4, Statement.RETURN_GENERATED_KEYS)) {
            preperedStatement.setString(1, "Pasha");
            preperedStatement.executeUpdate();
        }

        System.out.println("vyvod users");
        sql3 = "SELECT * FROM users";
        try (var statement3 = conn.createStatement()) {
            var resultSet = statement3.executeQuery(sql3);
            while (resultSet.next()) {
                System.out.println(resultSet.getString("username"));
                System.out.println(resultSet.getString("phone"));
            }
        }
        // Закрываем соединение
        conn.close();
    }
}

//var sql2 = "INSERT INTO films (title,release_year,duration) VALUES ('Godfather',1972,175),('The Green Mile',1999,189)";
//var statement2 = conn.createStatement();
//        statement2.executeUpdate(sql2);
//        statement2.close();