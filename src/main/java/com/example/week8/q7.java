package com.example.week8;

import java.sql.*;
import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class q7 {

    private static Properties getDbProperties(){
        Properties prop = new Properties();

        try(InputStream input = q7.class.getClassLoader().getResourceAsStream("db.properties")) {
            prop.load(input);
        } catch (IOException ex) {
            Logger.getLogger(q7.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  prop;
    }
    public static void main(String[] args) {
        Properties props = getDbProperties();

        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.passwd");

        try (Connection con = DriverManager.getConnection(url, user, password);) {
            System.out.println("Connected to MySQL database");


            String template_department = "INSERT INTO Department VALUES(?, ?, ?)";
            String template_employee = "INSERT INTO Employee VALUES(?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement insert_department = con.prepareStatement(template_department);
            PreparedStatement insert_employee = con.prepareStatement(template_employee);
            Scanner sc = new Scanner(System.in);

            while (true){
                System.out.println("Enter 1 for inserting department, 2 for inserting employee, 3 for exit");
                int choice = sc.nextInt();
                if (choice == 1){
                    System.out.println("Enter department id, name, and location");
                    String id = sc.next();
                    String name = sc.next();
                    String location = sc.next();

                    insert_department.setString(1, id);
                    insert_department.setString(2, name);
                    insert_department.setString(3, location);

                    try {
                        insert_department.executeUpdate();
                        System.out.println("Department inserted");
                    }
                    catch (SQLException e){
                        System.out.println("Error in inserting: " + e.getMessage());
                    }


                } else if (choice == 2) {
                    System.out.println("Enter employee id,employee name, jobTitle, dateofBirth, joiningDate, salary, departmentId");
                    String id = sc.next();
                    String name = sc.next();
                    String jobTitle = sc.next();
                    String dateofBirth = sc.next();
                    String joiningDate = sc.next();
                    String salary = sc.next();
                    String departmentId = sc.next();


                    insert_employee.setString(1, id);
                    insert_employee.setString(2, name);
                    insert_employee.setString(3, jobTitle);
                    insert_employee.setString(4, dateofBirth);
                    insert_employee.setString(5, joiningDate);
                    insert_employee.setString(6, salary);
                    insert_employee.setString(7, departmentId);

                    try {
                        insert_employee.executeUpdate();
                        System.out.println("Employee inserted");
                    }
                    catch (SQLException e){
                        System.out.println("Error in inserting: " + e.getMessage());
                    }

                } else if (choice == 3) {
                    break;
                } else {
                    System.out.println("Invalid choice");
                }
            }


        }
        catch (SQLException ex) {
            Logger lgr = Logger.getLogger(q7.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}
