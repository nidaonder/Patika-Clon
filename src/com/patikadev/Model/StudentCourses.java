package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class StudentCourses {

    private int id;
    private String name;
    private int studentId;

    private Student student;

    public StudentCourses(){}

    public StudentCourses(int id, String name, int studentId) {
        this.id = id;
        this.name = name;
        this.studentId = studentId;
        this.student = (Student) Student.getFetch(studentId);
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static ArrayList<StudentCourses> getList(){
        ArrayList<StudentCourses> studentCourses = new ArrayList<>();
        StudentCourses obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM student_courses");
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int studentId = rs.getInt("student_id");
                obj = new StudentCourses(id, name, studentId);
                studentCourses.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentCourses;
    }

    public static boolean add(int id, String name, int studentId){
        String query = "INSERT INTO student_courses (name, student_id) VALUES (?,?)";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1, name);
            pr.setInt(2, studentId);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
