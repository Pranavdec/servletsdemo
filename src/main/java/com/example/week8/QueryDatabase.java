package com.example.week8;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;

public class QueryDatabase extends HttpServlet {

    public void init() {
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        InputStream is = getServletContext().getResourceAsStream("/WEB-INF/classes/q10_8.html");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));


        String text;
        while ((text = reader.readLine()) != null) {
            out.println(text);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String joining_date = request.getParameter("joining-date");
        Integer age = Integer.parseInt(request.getParameter("age"));

        String output = Query.Query1(joining_date, age);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        InputStream is = getServletContext().getResourceAsStream("/WEB-INF/classes/q10_8.html");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));


        String text;
        while ((text = reader.readLine()) != null) {
            out.println(text);
        }

        out.println(output);

    }

}
