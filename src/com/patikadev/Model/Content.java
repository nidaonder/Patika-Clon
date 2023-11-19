package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Content {
    private int id;
    private int edu_id;
    private int course_id;
    private String title;
    private String yt_link;
    private String explanation;

    private User user;
    private Course course;

    public Content(int id, int edu_id, int course_id, String title, String yt_link, String explanation) {
        this.id = id;
        this.edu_id = edu_id;
        this.course_id = course_id;
        this.title = title;
        this.yt_link = yt_link;
        this.explanation = explanation;
        this.user = User.getFetch(edu_id);
        this.course = Course.getFetch(course_id);
    }

    public Content() {}

    public int getEdu_id() {
        return edu_id;
    }

    public void setEdu_id(int edu_id) {
        this.edu_id = edu_id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getYt_link() {
        return yt_link;
    }

    public void setYt_link(String yt_link) {
        this.yt_link = yt_link;
    }

    public static ArrayList<Content> getList(){
        ArrayList<Content> contentArrayList = new ArrayList<>();
        Content obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM content");
            while (rs.next()){
                int id = rs.getInt("id");
                int edu_id = rs.getInt("edu_id");
                int course_id = rs.getInt("course_id");
                String title = rs.getString("title");
                String yt_link = rs.getString("yt_link");
                String explanation = rs.getString("explanation");
                obj = new Content(id, edu_id, course_id, title, yt_link, explanation);
                contentArrayList.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contentArrayList;
    }

    public static Content getFetch(int id){
        Content obj = null;
        String query = "SELECT * FROM content WHERE id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                obj = new Content(rs.getInt("id"), rs.getInt("edu_id"),
                        rs.getInt("course_id"), rs.getString("title"),
                        rs.getString("yt_link"), rs.getString("explanation"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    public static boolean add(int edu_id, int course_id, String title, String yt_link, String explanation){
        String query = "INSERT INTO content (edu_id, course_id, title, yt_link, explanation) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, edu_id);
            pr.setInt(2,course_id);
            pr.setString(3,title);
            pr.setString(4,yt_link);
            pr.setString(5,explanation);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean delete(int id){
        String query = "DELETE FROM content WHERE id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, id);
            return  pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean update(int id, int eduId, int courseId, String title, String ytLink, String explanation){
        String query = "UPDATE content SET edu_id = ?, course_id = ?, title = ?, yt_link = ?, explanation = ? WHERE id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, eduId);
            pr.setInt(2, courseId);
            pr.setString(3, title);
            pr.setString(4, ytLink);
            pr.setString(5, explanation);
            pr.setInt(6, id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String searchQuery(String title){
        String query = "SELECT * FROM content WHERE title LIKE '%{{title}}%'";
        query = query.replace("{{title}}", title);
        return query;
    }

    public static ArrayList<Content> searchContentList(String query){
        ArrayList<Content> contentArrayList = new ArrayList<>();
        Content obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                obj = new Content();
                obj.setId(rs.getInt("id"));
                obj.setEdu_id(rs.getInt("edu_id"));
                obj.setCourse_id(rs.getInt("course_id"));
                obj.setTitle(rs.getString("title"));
                obj.setYt_link(rs.getString("yt_link"));
                obj.setExplanation(rs.getString("explanation"));
                contentArrayList.add(obj);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contentArrayList;
    }


}
