package com.example.week8;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Query {
    private static Properties getDbProperties(){
        Properties prop = new Properties();

        ClassLoader classLoader = Query.class.getClassLoader();

        try(InputStream inputStream = classLoader.getResourceAsStream("db.properties")) {
            prop.load(inputStream);
        } catch (IOException ex) {
            Logger.getLogger(UploadData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  prop;
    }

    public static String Query1(String joining_date, Integer age) {
        Properties props = getDbProperties();

        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.passwd");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, password);

            String query = "select * from Employee where JoiningDate > ? and year(curdate()) - year(DateofBirth) > ?";


            StringBuilder output = new StringBuilder();

            output.append("<table border='1'>");
            output.append("<tr>");
            output.append("<th>EmployeeID</th>");
            output.append("<th>EmployeeName</th>");
            output.append("<th>JobTitle</th>");
            output.append("<th>DateofBirth</th>");
            output.append("<th>JoiningDate</th>");
            output.append("<th>Salary</th>");
            output.append("<th>DepartmentID</th>");
            output.append("</tr>");

            try (PreparedStatement st = con.prepareStatement(query)) {
                st.setString(1, joining_date);
                st.setInt(2, age);

                ResultSet rs = st.executeQuery();

                while (rs.next()) {
                    output.append("<tr>");
                    output.append("<td>").append(rs.getString(1)).append("</td>");
                    output.append("<td>").append(rs.getString(2)).append("</td>");
                    output.append("<td>").append(rs.getString(3)).append("</td>");
                    output.append("<td>").append(rs.getString(4)).append("</td>");
                    output.append("<td>").append(rs.getString(5)).append("</td>");
                    output.append("<td>").append(rs.getString(6)).append("</td>");
                    output.append("<td>").append(rs.getString(7)).append("</td>");
                    output.append("</tr>");
                }
            }

            output.append("</table>");

            return output.toString();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }


        return "Query 1";

    }
}
