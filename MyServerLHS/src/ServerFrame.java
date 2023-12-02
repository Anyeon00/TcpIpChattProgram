import org.w3c.dom.Text;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ServerFrame extends JFrame  {
    JPanel controlPanel;
    JButton startButton;
    JScrollPane mainPanel;
    JPanel inputPanel;
    JTextArea context;
    JTextField inputContext;
    ServerSocketRoll serverSocketRoll;
    ServerFrame(){
        setTitle("Server");
        setSize(400,600);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        Container contentPane = getContentPane();
        serverSocketRoll = new ServerSocketRoll(this);

        controlPanel = new JPanel();
        startButton = new JButton("Start");
        startButton.addActionListener((e) ->{
            serverSocketRoll.start();
            writeMessage("Server started...");
        });
        controlPanel.add(startButton);

        context = new JTextArea();
        mainPanel = new JScrollPane(context);
        //context.setBorder(BorderFactory.createLoweredBevelBorder());

        inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(1,1));
        inputContext = new JTextField(20);
        inputContext.addKeyListener(serverSocketRoll);
        inputPanel.add(inputContext);

        contentPane.add(mainPanel, "Center");
        contentPane.add(controlPanel, "North");
        contentPane.add(inputPanel, "South");
    }
    public void writeMessage(String msg){
        context.append(msg + "\r\n");
    }
}
