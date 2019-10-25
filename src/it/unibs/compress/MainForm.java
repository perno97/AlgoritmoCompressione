package it.unibs.compress;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
    private JLabel lblCodebook;

    private Controller controller;

    MainForm(Controller controller){
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

    void showResult(ArrayList<String> output, ArrayList<String> codebook){
        StringBuilder builderOutput = new StringBuilder(), builderCodebook = new StringBuilder();
        builderCodebook.append("Codebook: [");
        for (int i = 0; i < output.size() - 1; i++)
            builderOutput.append(output.get(i)).append(",");
        for (int i = 0; i < codebook.size() -1; i++)
            builderCodebook.append(codebook.get(i)).append(",");
        builderCodebook.append(codebook.get(codebook.size() - 1)).append("]");
        builderOutput.append(output.get(output.size() - 1));
        lblResult.setText(builderOutput.toString());
        lblCodebook.setText(builderCodebook.toString());
    }

    void showString(String string){
        lblResult.setText(string);
    }

    void setProgress(int percent) {
        progressBar1.setValue(percent);
    }
}
