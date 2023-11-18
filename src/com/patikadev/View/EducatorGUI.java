package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Content;
import com.patikadev.Model.Course;
import com.patikadev.Model.Educator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
    private JPanel pnl_quiz_list;
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
    private DefaultTableModel mdl_edctr_crs_list;
    private Object[] row_edctr_crs_list;
    private DefaultTableModel mdl_content_list;
    private Object[] row_content_list;


    private final Educator educator;


    public EducatorGUI(Educator educator){
        this.educator = educator;
        add(wrapper);
        setSize(1000,500);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        lbl_welcome.setText("Welcome '" + educator.getName() + "'");

        // ModelEducatorCourseList
        mdl_edctr_crs_list = new DefaultTableModel();
        Object[] col_edctr_crs_list = {"Number", "Course Language", "Course Name", "Educator Name"};
        mdl_edctr_crs_list.setColumnIdentifiers(col_edctr_crs_list);

        tbl_edctr_crs_list.setModel(mdl_edctr_crs_list);
        tbl_edctr_crs_list.getTableHeader().setReorderingAllowed(false);

        int i = 1;
        for (Course course : Course.getList()){
            Object[] row = new Object[col_edctr_crs_list.length];
            if (course.getUser_id() == educator.getId()){

                row[0] = i++;
                row[1] = course.getLang();
                row[2] = course.getName();
                row[3] = educator.getName();
                mdl_edctr_crs_list.addRow(row);
            }
        }
        // ## ModelEducatorCourseList

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

        //LogOut;
        btn_logout.addActionListener(e -> {
            dispose();
            LoginGUI login = new LoginGUI();
        });

        /*
        // Search Content
        btn_content_sh.addActionListener(e -> {
            String course_name = fld_sh_course_name.getText();
            String title = fld_sh_title.getText();
            String query = Content.searchQuery(course_name, title);
            loadContentList(Content.searchContentList(query));

        }); */


        // ContentAdd;
        btn_content_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_edu_id) || Helper.isFieldEmpty(fld_course_id) ||
                    Helper.isFieldEmpty(fld_content_title) || Helper.isFieldEmpty(fld_yt_link) ||Helper.isFieldEmpty(fld_explanation)){
                Helper.showMessage("fill");
            } else {
                Helper.showMessage("done");
            }
        });
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

    private void loadContentList(ArrayList<Content> list) {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_content_list.getModel();
        clearModel.setRowCount(0);
        int i = 0;
        for (Content obj : list){
            i = 0;
            row_content_list[i++] = obj.getId();
            row_content_list[i++] = obj.getTitle();
            row_content_list[i++] = obj.getExplanation();
            row_content_list[i++] = obj.getYt_link();
        }
    }

    public Educator getEducator() {
        return educator;
    }

}
