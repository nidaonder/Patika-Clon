package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Content;
import com.patikadev.Model.Quiz;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class QuizGUI extends JFrame {


    private JPanel wrapper;
    private JPanel pnl_top;
    private JLabel lbl_content_name;
    private JPanel pnl_quiz_list;
    private JScrollPane scrl_quiz_list;
    private JTable tbl_quiz_list;
    private JScrollPane scrl_add_quiz;
    private JPanel pnl_add_quiz;
    private JTextField fld_content_id;
    private JTextField fld_educator_id;
    private JTextField fld_question;
    private JButton btn_add;
    private JTextField fld_quiz_id;
    private JTextField fld_update_question;
    private JButton btn_update;
    private JTextField fld_delete_quiz_id;
    private JButton btn_delete;
    private DefaultTableModel mdl_quiz_list;
    private Object[] row_quiz_list;



    private Content content;



    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public QuizGUI(Content content){

        this.content = content;
        add(wrapper);
        setSize(800,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        lbl_content_name.setText("Quiz Questions Related To The Course ' " + content.getTitle() + " '");
        fld_content_id.setText(String.valueOf(content.getId()));
        fld_educator_id.setText(String.valueOf(content.getEdu_id()));

        // Model Quiz List;
        mdl_quiz_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column >= 0){
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };

        Object[] col_quiz_list = {"ID", "CONTENT", "EDUCATOR","QUESTION"};
        mdl_quiz_list.setColumnIdentifiers(col_quiz_list);

        for (Quiz quiz : Quiz.getList()){
            Object[] row = new Object[col_quiz_list.length];
            if (content.getId() == quiz.getContentId()){
                row[0] = quiz.getId();
                row[1] = quiz.getContentId();
                row[2] = quiz.getEduId();
                row[3] = quiz.getQuestion();
                mdl_quiz_list.addRow(row);
            }
        }
        tbl_quiz_list.setModel(mdl_quiz_list);
        tbl_quiz_list.getTableHeader().setReorderingAllowed(false);

        // Add - Update - Delete Info;
        tbl_quiz_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String select_quiz_id = tbl_quiz_list.getValueAt(tbl_quiz_list.getSelectedRow(), 0).toString();
                int selected_quiz_id = Integer.parseInt(select_quiz_id);
                fld_quiz_id.setText(select_quiz_id);
                fld_update_question.setText(Quiz.getFetch(selected_quiz_id).getQuestion());
                fld_delete_quiz_id.setText(select_quiz_id);
            } catch (Exception exception) {}
        });

        // Add Quiz
        btn_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_content_id) || Helper.isFieldEmpty(fld_educator_id) ||
                    Helper.isFieldEmpty(fld_question)){
                Helper.showMessage("fill");
            } else {
                int contentId = content.getId();
                int eduId = content.getEdu_id();
                String question = fld_question.getText();
                if (Quiz.add(contentId, eduId, question)){
                    Helper.showMessage("done");
                    loadQuizModel();
                } else {
                    Helper.showMessage("error");
                }
            }
            resetUpdateQuizList();
        });

        // Delete Quiz
        btn_delete.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_delete_quiz_id)){
                Helper.showMessage("Please select a quiz!");
            } else {
                int selected_quiz_id = Integer.parseInt(fld_delete_quiz_id.getText());
                if (Quiz.delete(selected_quiz_id)){
                    Helper.showMessage("done");
                    loadQuizModel();
                } else {
                    Helper.showMessage("error");
                }
            }
            resetUpdateQuizList();
        });

        // Update Quiz
        btn_update.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_quiz_id) || Helper.isFieldEmpty(fld_update_question)){
                Helper.showMessage("fill");
            } else {
                int id = Integer.parseInt(fld_quiz_id.getText());
                String question = fld_update_question.getText();
                if (Quiz.update(id, question)){
                    Helper.showMessage("done");
                    loadQuizModel();
                }
            }
            resetUpdateQuizList();
        });
    }

    private void loadQuizModel(){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_quiz_list.getModel();
        clearModel.setRowCount(0);
        Object[] col_quiz_list = {"ID", "CONTENT", "EDUCATOR", "QUESTION"};
        mdl_quiz_list.setColumnIdentifiers(col_quiz_list);

        for (Quiz quiz : Quiz.getList()){
            Object[] row = new Object[col_quiz_list.length];
            if (content.getId() == quiz.getContentId()){
                row[0] = quiz.getId();
                row[1] = quiz.getContentId();
                row[2] = quiz.getEduId();
                row[3] = quiz.getQuestion();
                mdl_quiz_list.addRow(row);
            }
        }
    }

    private void resetUpdateQuizList(){
        fld_question.setText(null);
        fld_quiz_id.setText(null);
        fld_update_question.setText(null);
        fld_delete_quiz_id.setText(null);
    }
}
