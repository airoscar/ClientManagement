package Server;

import javax.swing.*;
import java.awt.*;

/**
 * Server monitor view which shows logging messages, closing the view would shut down the server.
 */
public class ServerView extends JFrame {

    private static JTextArea messageField = new JTextArea();


    public ServerView() {
        setTitle("Server Monitor");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 1));

        this.add(messageAreaPanel());
        this.setResizable(false);
        this.setVisible(true);
    }

    private JPanel messageAreaPanel () {
        JPanel msgPanel = new JPanel();
        JScrollPane scrollMessageBoard = new JScrollPane(messageField);
        scrollMessageBoard.setPreferredSize(new Dimension(800, 550));
        msgPanel.add(scrollMessageBoard);
        messageField.setEditable(false);
        return msgPanel;
    }

    public static void print(String msg) {
        messageField.append("\n" + msg);
    }


}
