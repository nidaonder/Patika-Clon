package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Educator;

import javax.swing.*;

public class EducatorGUI extends JFrame {
    private JPanel wrapper;
    private JTabbedPane tab_educator;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JButton btn_logout;
    private JPanel pnl_courses_list;

    private final Educator educator;


    public EducatorGUI(Educator educator){
        this.educator = educator;
        add(wrapper);
        setSize(1000,500);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        lbl_welcome.setText("Welcome " + educator.getName());

    }

    public Educator getEducator() {
        return educator;
    }

}
