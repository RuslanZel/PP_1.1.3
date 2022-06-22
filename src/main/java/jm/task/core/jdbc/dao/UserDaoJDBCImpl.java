package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    // Создание таблицы User
    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users_new " +
                    "(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), lastName VARCHAR(255), age INT)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Удаление таблицы User
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users_new";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица удалена");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Добавление User в таблицу
    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users_new (name, lastName, age) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println(name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Удаление User из таблицы ( по id )
    public void removeUserById(long id) {
        String sql = "DELETE FROM users_new WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("User удален");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Получение всех User(ов) из таблицы
    public List<User> getAllUsers() {
        List<User> users_new = new ArrayList<>();
        String sql = "SELECT * FROM users_new";
        try (ResultSet resultSet = connection.createStatement().executeQuery(sql)) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users_new.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users_new;
    }

    // Очистка таблицы
    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE users_new";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица очищена");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
