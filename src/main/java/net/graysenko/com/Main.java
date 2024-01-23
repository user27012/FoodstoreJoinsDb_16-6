package net.graysenko.com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    private static String URL = "jdbc:mysql://localhost:3306/myjoinsdb";
    private static String LOGIN = "root";
    private static String PASSWORD = "root";

    public static void main(String[] args) {
        DriverRegister();

        Connection connection = null;
        Statement statement = null;
        try{
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            statement = connection.createStatement();

            //Выводим контактные данные: номера, имена, место жительства.
            statement.execute("SELECT c.name, c.phonenumber, i.place FROM Contact c JOIN info i ON c.name = i.name");
            //Выводим информацию про день рождения всех не женатых сотрудников и их номера.
            statement.execute("SELECT c.name, i.birth FROM Contact c LEFT JOIN info i ON c.name = i.name WHERE i.married = 'No'");
            //Выводим информацию про всех менеджеров компании, их номера телефонов и дни рождения.
            statement.execute("SELECT c.name, i.birth, c.phonenumber FROM Contact c JOIN Status s ON c.name = s.name LEFT JOIN info i ON c.name = i.name WHERE s.position = 'Project manager' OR s.position = 'Manager'");

            Boolean closed = statement.isClosed();
            System.out.println(closed);


        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                statement.close();
                boolean fclose = statement.isClosed();
                System.out.println(fclose);
            } catch (SQLException e){
                e.printStackTrace();
            }
        }

    }
    public static void DriverRegister(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver connected!");
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}