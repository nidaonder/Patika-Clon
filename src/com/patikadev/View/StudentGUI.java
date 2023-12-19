package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Patika;
import com.patikadev.Model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class StudentGUI extends JFrame {
    private JPanel wrapper;
    private JPanel pnl_top;
    private JButton btn_logout;
    private JLabel lbl_welcome;
    private JTabbedPane tab_student;
    private JPanel pnl_patika_list;
    private JTable tbl_patika_list;
    private JScrollPane scrl_patika_list;
    private JTextField fld_select_patika;
    private JButton btn_show_course;
    private DefaultTableModel mdl_patika_list;
    private Object[] row_patika_list;

    private final Student student;

    public StudentGUI(Student student){
        this.student = student;
        add(wrapper);
        setSize(900,600);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        lbl_welcome.setText("Welcome '" + student.getName() + "'");

        // Model Patika List
        mdl_patika_list = new DefaultTableModel();
        Object[] col_patika_list = {"ID", "NAME"};
        mdl_patika_list.setColumnIdentifiers(col_patika_list);
        tbl_patika_list.setModel(mdl_patika_list);
        tbl_patika_list.getTableHeader().setReorderingAllowed(false);
        tbl_patika_list.getColumnModel().getColumn(0).setMaxWidth(50);
        tbl_patika_list.getColumnModel().getColumn(1).setMinWidth(150);
        Object[] row = new Object[col_patika_list.length];

        for (Patika patika : Patika.getList()){
            row[0] = patika.getId();
            row[1] = patika.getName();
            mdl_patika_list.addRow(row);
        }

        tbl_patika_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String select_patika_id = tbl_patika_list.getValueAt(tbl_patika_list.getSelectedRow(), 0).toString();
                fld_select_patika.setText(select_patika_id);
            } catch (Exception exception) {}
        });

        // Show Button
        btn_show_course.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_select_patika)){
                Helper.showMessage("Please select a patika!");
            } else {
                int select_patika_id = Integer.parseInt(fld_select_patika.getText());
                CourseGUI course = new CourseGUI(Patika.getFetch(select_patika_id));
            }
        });

        // Logout Button
        btn_logout.addActionListener(e -> {
            dispose();
            LoginGUI login = new LoginGUI();
        });
    }
}
