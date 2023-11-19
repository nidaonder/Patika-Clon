package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Quiz {
    private int id;
    private int contentId;
    private int eduId;
    private String question;
    private User user;
    private Content content;

    public Quiz(int id, int contentId, int eduId, String question) {
        this.id = id;
        this.contentId = contentId;
        this.eduId = eduId;
        this.question = question;
        this.user = User.getFetch(eduId);
        this.content = Content.getFetch(contentId);

    }

    public Quiz () {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getEduId() {
        return eduId;
    }

    public void setEduId(int eduId) {
        this.eduId = eduId;
    }

    public static ArrayList<Quiz> getList(){
        ArrayList<Quiz> quizList = new ArrayList<>();
        Quiz obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM quiz");
            while (rs.next()){
                int id = rs.getInt("id");
                int contentId = rs.getInt("content_id");
                int eduId = rs.getInt("edu_id");
                String question = rs.getString("question");
                obj = new Quiz(id, contentId, eduId, question);
                quizList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return quizList;
    }

    public static Quiz getFetch(int id){
        Quiz obj = null;
        String query = "SELECT * FROM quiz WHERE id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                obj = new Quiz(rs.getInt("id"), rs.getInt("content_id"),
                        rs.getInt("edu_id"), rs.getString("question"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    public static boolean add (int contentId, int eduId, String question){
        String query = "INSERT INTO quiz (content_id, edu_id, question) VALUES (?, ?, ?)";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, contentId);
            pr.setInt(2, eduId);
            pr.setString(3, question);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean delete(int id){
        String query = "DELETE FROM quiz WHERE id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean update (int id, String question){
        String query = "UPDATE quiz SET question = ? WHERE id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1, question);
            pr.setInt(2, id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
