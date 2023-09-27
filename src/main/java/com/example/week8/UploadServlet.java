package com.example.week8;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.util.Objects;


public class UploadServlet extends HttpServlet {

    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        InputStream is = getServletContext().getResourceAsStream("/WEB-INF/classes/q10_7.html");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));


        String text;
        while ((text = reader.readLine()) != null) {
            out.println(text);
        }


    }


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String radio = request.getParameter("selection");
        String eid = request.getParameter("eid");
        String name = request.getParameter("name");
        String job_title = request.getParameter("job-title");
        String dob = request.getParameter("dob");
        String joining_date = request.getParameter("joining-date");
        String salary = request.getParameter("salary");
        String dept_id = request.getParameter("did");
        String dname = request.getParameter("dname");
        String location = request.getParameter("location");

        System.out.println(radio);

        String output;
        if(Objects.equals(radio, "emp")){
            System.out.println("emp");
            output = UploadData.UploadEmployee(eid, name, job_title, dob, joining_date, Integer.parseInt(salary), dept_id);
        }
        else{

            output = UploadData.UploadDepartment(dept_id, dname, location);
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        InputStream is = getServletContext().getResourceAsStream("/WEB-INF/classes/q10_7.html");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));


        String text;
        while ((text = reader.readLine()) != null) {
            out.println(text);
        }

        out.println("<p>" + output + "</p>");



    }
}
