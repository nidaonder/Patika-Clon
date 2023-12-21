package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Course;
import com.patikadev.Model.Patika;
import com.patikadev.Model.StudentCourses;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CourseGUI extends JFrame{
    private JPanel wrapper;
    private JPanel pnl_top;
    private JPanel pnl_course_list;
    private JLabel lbl_patika_name;
    private JScrollPane scrl_course_list;
    private JTable tbl_course_list;
    private JButton btn_select_signup;
    private JTextField fld_course_id;
    private DefaultTableModel mdl_course_list;
    private Object[] row_course_list;



    private Patika patika;

    public Patika getPatika() {
        return patika;
    }

    public void setPatika(Patika patika) {
        this.patika = patika;
    }

    public CourseGUI(Patika patika) {
        this.patika = patika;
        add(wrapper);
        setSize(800,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        lbl_patika_name.setText(patika.getName());

        // Model Course List
        mdl_course_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column >= 0){
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };

        Object[] col_course_list = {"ID", "PATIKA NAME", "COURSE NAME"};
        mdl_course_list.setColumnIdentifiers(col_course_list);
        for (Course course : Course.getList()){
            Object[] row = new Object[col_course_list.length];
            if (patika.getId() == course.getPatika_id()){
                row[0] = course.getId();
                row[1] = course.getPatika().getName();
                row[2] = course.getName();
                mdl_course_list.addRow(row);
            }
        }

        tbl_course_list.setModel(mdl_course_list);
        tbl_course_list.getTableHeader().setReorderingAllowed(false);

        // Select
        tbl_course_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String select_course_id = tbl_course_list.getValueAt(tbl_course_list.getSelectedRow(), 0).toString();
                fld_course_id.setText(select_course_id);
            } catch (Exception exception){}
        });

        // Select And Sign Up Button;
        btn_select_signup.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_course_id)){
                Helper.showMessage("Please select a course!");
            } else {
                int select_course_id = Integer.parseInt(fld_course_id.getText());
            }
        });
    }
}
