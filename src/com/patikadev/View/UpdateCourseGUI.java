package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Course;

import javax.swing.*;

public class UpdateCourseGUI extends JFrame{
    private JPanel wrapper;
    private JTextField fld_course_name;
    private JButton btn_update;
    private Course course;

    public UpdateCourseGUI(Course course){
        this.course = course;
        add(wrapper);
        setSize(300,150);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        fld_course_name.setText(course.getName());
        btn_update.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_course_name)){
                Helper.showMessage("fill");
            } else {
                if (Course.update(course.getId(), fld_course_name.getText())){
                    Helper.showMessage("done");
                }
                dispose();
            }
        });
    }
}
