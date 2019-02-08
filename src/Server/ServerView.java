package Server;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ServerView extends JFrame {

    private static JTextArea messageField = new JTextArea();


    public ServerView() {
        setTitle("Server Monitor");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 1));

//        this.add(serverStatusPanel());
        this.add(messageAreaPanel());
        this.setResizable(false);
        this.setVisible(true);
    }


//    private JPanel serverStatusPanel (){
//        JPanel serverStatus = new JPanel();
//        serverStatus.setBorder(new EmptyBorder(10,10,10,10));
//        serverStatus.setLayout(new GridLayout(1,1,0, 0));
//        serverStatus.add(serverStatLabel);
//        return serverStatus;
//    }

    private JPanel messageAreaPanel () {
        JPanel msgPanel = new JPanel();
        JScrollPane scrollMessageBoard = new JScrollPane(messageField);
        scrollMessageBoard.setPreferredSize(new Dimension(800, 600));
        msgPanel.add(scrollMessageBoard);
        messageField.setEditable(false);
        return msgPanel;
    }


//    public void setServerStatLabel(JLabel serverStatLabel) {
//        this.serverStatLabel = serverStatLabel;
//    }

    public static void print(String msg) {
        messageField.append("\n" + msg);
    }


}
