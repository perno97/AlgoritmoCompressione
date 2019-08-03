package it.unibs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainForm implements ActionListener {
    private static final String START_COMMAND = "Start";
    private static final String FRAME_TITLE = "LZCompressor";
    private final JFrame frame;

    private JTextField txtSource;
    private JRadioButton btnEncode;
    private JRadioButton btnDecode;
    private JButton startButton;
    private JProgressBar progressBar1;
    private JPanel mainPanel;
    private JLabel lblResult;

    private Controller controller;

    public MainForm(Controller controller){
        this.controller=controller;
        this.frame = new JFrame();
        initiate();
    }

    private void initiate() {
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setMinimumSize(new Dimension(500,300));
        frame.getRootPane().setDefaultButton(startButton);//start quando viene premuto invio
        frame.setTitle(FRAME_TITLE);
        startButton.setActionCommand(START_COMMAND);
        startButton.addActionListener(this);
    }

    private void execute(){
        boolean command;
        if(btnEncode.isSelected()) command = Controller.ENCODE;
        else command = Controller.DECODE;
        controller.execute(txtSource.getText(), command);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (START_COMMAND.equals(e.getActionCommand())) {
            execute();
        }
    }

    public void showString(String text){
        lblResult.setText(text);
    }

    public void setProgress(int percent) {
        progressBar1.setValue(percent);
    }
}
