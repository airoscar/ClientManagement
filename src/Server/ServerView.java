package Server;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class ServerView extends JFrame {

    private static JLabel serverStatLabel = new JLabel("Hello!", SwingConstants.CENTER);
    private JButton startServerButton = new JButton("Start Server");
    private JButton stopServerButton = new JButton("Stop Server");
    private static JTextArea messageField = new JTextArea();


    public ServerView() {
        setTitle("Server Monitor");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new GridLayout(4, 1));

        this.add(serverStatusPanel());
        this.add(serverButtonsPanel());
        this.add(messageAreaPanel());
        this.setResizable(false);
        this.setVisible(true);
    }

    private JPanel serverStatusPanel (){
        JPanel serverStatus = new JPanel();
        serverStatus.setBorder(new EmptyBorder(20,20,20,20));
        serverStatus.setLayout(new GridLayout(1,1,0, 0));
        serverStatus.add(serverStatLabel);
        return serverStatus;
    }

    private JPanel serverButtonsPanel () {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBorder(new EmptyBorder(50, 60, 20, 60));
        buttonsPanel.setLayout(new GridLayout(1, 2, 40, 0));
        buttonsPanel.add(startServerButton);
        buttonsPanel.add(stopServerButton);
        return buttonsPanel;
    }

    private JPanel messageAreaPanel () {
        JPanel msgPanel = new JPanel();
        JScrollPane scrollMessageBoard = new JScrollPane(messageField);
        scrollMessageBoard.setPreferredSize(new Dimension(500, 500));
        msgPanel.setBorder(new EmptyBorder(10, 10, 2, 10));
        msgPanel.add(scrollMessageBoard);
        messageField.setEditable(false);
        return msgPanel;
    }


    public void setServerStatLabel(JLabel serverStatLabel) {
        this.serverStatLabel = serverStatLabel;
    }

    public static void addMessage(String msg) {
        messageField.append(msg);
    }

    public void setActionListeners (ActionListener startServer, Action stopServer){
        startServerButton.addActionListener(startServer);
        stopServerButton.addActionListener(stopServer);
    }

    public void disableStartButton(){
        startServerButton.setEnabled(false);
    }

    public void enableStartButton(){
        startServerButton.setEnabled(true);
    }

    public void disableStopButton() {
        stopServerButton.setEnabled(false);
    }

    public void enableStopButton(){
        stopServerButton.setEnabled(true);
    }

    public static void main(String[] args) {
        ServerView a = new ServerView();
    }

}
