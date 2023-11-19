package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Content;
import com.patikadev.Model.Course;
import com.patikadev.Model.Educator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EducatorGUI extends JFrame {
    private JPanel wrapper;
    private JTabbedPane tab_educator;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JButton btn_logout;
    private JPanel pnl_courses_list;
    private JScrollPane scrl_crs_by_edctr_list;
    private JTable tbl_edctr_crs_list;
    private JPanel pnl_contents_list;
    private JPanel pnl_content_form;
    private JTextField fld_content_title;
    private JTextField fld_explanation;
    private JTextField fld_yt_link;
    private JButton btn_content_add;
    private JScrollPane scrl_content_list;
    private JTable tbl_content_list;
    private JTextField fld_sh_course_name;
    private JTextField fld_sh_title;
    private JButton btn_content_sh;
    private JTextField fld_edu_id;
    private JTextField fld_course_id;
    private JScrollPane scrl_update_delete;
    private JPanel pnl_search;
    private JPanel pnl_update_delete;
    private JTextField fld_update_title;
    private JTextField fld_update_course_id;
    private JTextField fld_update_edu_id;
    private JTextField fld_update_yt_link;
    private JTextField fld_update_explanation;
    private JButton btn_update_content;
    private JTextField fld_delete_content_id;
    private JButton btn_delete_content;
    private JPanel pnl_quiz;
    private JButton btn_show_quiz;
    private JTextField fld_quiz_content_id;
    private DefaultTableModel mdl_edctr_crs_list;
    private Object[] row_edctr_crs_list;
    private DefaultTableModel mdl_content_list;
    private Object[] row_content_list;


    private final Educator educator;


    public EducatorGUI(Educator educator){
        this.educator = educator;
        add(wrapper);
        setSize(900,600);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        lbl_welcome.setText("Welcome '" + educator.getName() + "'");
        fld_edu_id.setText(String.valueOf(educator.getId()));


        // ModelEducatorCourseList
        mdl_edctr_crs_list = new DefaultTableModel();
        Object[] col_edctr_crs_list = {"ID", "Course Language", "Course Name", "Educator Name"};
        mdl_edctr_crs_list.setColumnIdentifiers(col_edctr_crs_list);

        tbl_edctr_crs_list.setModel(mdl_edctr_crs_list);
        tbl_edctr_crs_list.getTableHeader().setReorderingAllowed(false);

        tbl_edctr_crs_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String select_course_id = tbl_edctr_crs_list.getValueAt(tbl_edctr_crs_list.getSelectedRow(), 0).toString();
                fld_course_id.setText(select_course_id);
            }  catch (Exception exception) {

            }
        });

        for (Course course : Course.getList()){
            Object[] row = new Object[col_edctr_crs_list.length];
            if (course.getUser_id() == educator.getId()){

                row[0] = course.getId();
                row[1] = course.getLang();
                row[2] = course.getName();
                row[3] = educator.getName();
                mdl_edctr_crs_list.addRow(row);
            }
        }

        // ModelContentList
        mdl_content_list = new DefaultTableModel();
        Object[] col_content_list = {"Id", "Course Name", "Title", "Explanation", "Youtube Link"};
        mdl_content_list.setColumnIdentifiers(col_content_list);
        row_content_list = new Object[col_content_list.length];

        for (Content obj : Content.getList()){
            Object[] row = new Object[col_content_list.length];
            row[0] = obj.getId();
            row[1] = Course.getFetch(obj.getCourse_id()).getName();
            row[2] = obj.getTitle();
            row[3] = obj.getExplanation();
            row[4] = obj.getYt_link();
            mdl_content_list.addRow(row);
        }

        row_content_list = new Object[col_content_list.length];
        loadContentList();
        tbl_content_list.setModel(mdl_content_list);
        tbl_content_list.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_content_list.getTableHeader().setReorderingAllowed(false);

        // Update - Delete Info (Content)
        tbl_content_list.getSelectionModel().addListSelectionListener(e -> {
            try{
                String select_content_id = tbl_content_list.getValueAt(tbl_content_list.getSelectedRow(), 0).toString();
                fld_delete_content_id.setText(select_content_id);
                fld_quiz_content_id.setText(select_content_id);

                int update_content_id = Integer.parseInt(select_content_id);
                fld_update_title.setText(Content.getFetch(update_content_id).getTitle());
                String courseId = String.valueOf(Content.getFetch(update_content_id).getCourse_id());
                fld_update_course_id.setText(courseId);
                String eduId = String.valueOf(educator.getId());
                fld_update_edu_id.setText(eduId);
                fld_update_yt_link.setText(Content.getFetch(update_content_id).getYt_link());
                fld_update_explanation.setText(Content.getFetch(update_content_id).getExplanation());

            } catch (Exception exception) {}
        });

        //LogOut;
        btn_logout.addActionListener(e -> {
            dispose();
            LoginGUI login = new LoginGUI();
        });

        // Add Content
        btn_content_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_course_id) || Helper.isFieldEmpty(fld_content_title) ||
                    Helper.isFieldEmpty(fld_yt_link) ||Helper.isFieldEmpty(fld_explanation)){
                Helper.showMessage("fill");
            } else {
                int eduId = Integer.parseInt(fld_edu_id.getText());
                int courseId = Integer.parseInt(fld_course_id.getText());
                String title = fld_content_title.getText();
                String ytLink = fld_yt_link.getText();
                String explanation = fld_explanation.getText();
                if (Content.add(eduId, courseId, title, ytLink, explanation)){
                    Helper.showMessage("done");
                } else {
                    Helper.showMessage("error");
                }
            }
            loadContentModel();
        });

        // Delete Content
        btn_delete_content.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_delete_content_id)){
                Helper.showMessage("fill");
            } else {
                int select_delete_content_id = Integer.parseInt(fld_delete_content_id.getText());
                if (Content.delete(select_delete_content_id)){
                    Helper.showMessage("done");
                    loadContentModel();
                } else {
                    Helper.showMessage("error");
                }
            }
            resetUpdateContentList();
        });

        // Update Content
        btn_update_content.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_update_title) || Helper.isFieldEmpty(fld_update_edu_id) ||
                    Helper.isFieldEmpty(fld_update_course_id) || Helper.isFieldEmpty(fld_update_yt_link) ||
                    Helper.isFieldEmpty(fld_update_explanation)) {
                Helper.showMessage("fill");
            } else {
                int id = Integer.parseInt(fld_delete_content_id.getText());
                int eduId = Integer.parseInt(fld_update_edu_id.getText());
                int courseId = Integer.parseInt(fld_update_course_id.getText());
                String title = fld_update_title.getText();
                String ytLink = fld_update_yt_link.getText();
                String explanation = fld_update_explanation.getText();
                if (Content.update(id, eduId, courseId, title, ytLink, explanation)){
                    Helper.showMessage("done");
                    loadContentModel();
                } else {
                    Helper.showMessage("error");
                }
            }
            resetUpdateContentList();
        });

        // Show The Quizzes
        btn_show_quiz.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_quiz_content_id)){
                Helper.showMessage("Please select a content!");
            } else {
                int select_content_id = Integer.parseInt(fld_quiz_content_id.getText());
                QuizGUI quiz = new QuizGUI(Content.getFetch(select_content_id));
            }
        });

        // Search Content
        btn_content_sh.addActionListener(e -> {
            String title = fld_sh_title.getText();
            String query = Content.searchQuery(title);
            ArrayList<Content> searchingContent = Content.searchContentList(query);
            loadContentModel(searchingContent);
        });


    }

    private void loadContentModel(){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_content_list.getModel();
        clearModel.setRowCount(0);
        Object[] col_content_list = {"Id", "Course Name", "Title", "Explanation", "Youtube Link"};
        mdl_content_list.setColumnIdentifiers(col_content_list);
        Object[] row = new Object[col_content_list.length];

        for (Content obj : Content.getList()){
            row[0] = obj.getId();
            row[1] = Course.getFetch(obj.getCourse_id()).getName();
            row[2] = obj.getTitle();
            row[3] = obj.getExplanation();
            row[4] = obj.getYt_link();
            mdl_content_list.addRow(row);
        }
    }

    public void loadContentModel(ArrayList<Content> list){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_content_list.getModel();
        clearModel.setRowCount(0);
        Object[] col_content_list = {"Id", "Course Name", "Title", "Explanation", "Youtube Link"};
        mdl_content_list.setColumnIdentifiers(col_content_list);
        Object[] row = new Object[col_content_list.length];
        for (Content obj : list){
            row[0] = obj.getId();
            row[1] = Course.getFetch(obj.getCourse_id()).getName();
            row[2] = obj.getTitle();
            row[3] = obj.getExplanation();
            row[4] = obj.getYt_link();
            mdl_content_list.addRow(row);
        }

    }
    private void resetUpdateContentList(){
        fld_update_title.setText(null);
        fld_update_edu_id.setText(null);
        fld_update_course_id.setText(null);
        fld_update_yt_link.setText(null);
        fld_update_explanation.setText(null);
        fld_delete_content_id.setText(null);
    }

    private void loadContentList() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_content_list.getModel();
        clearModel.setRowCount(0);
        int i = 0;
        for (Content obj : Content.getList()){
            i = 0;
            row_content_list[i++] = obj.getId();
            row_content_list[i++] = obj.getTitle();
            row_content_list[i++] = obj.getExplanation();
            row_content_list[i++] = obj.getYt_link();
        }
    }
}
