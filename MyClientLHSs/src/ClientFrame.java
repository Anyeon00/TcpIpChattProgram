import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ClientFrame extends JFrame implements ActionListener{
    JPanel addressPanel;
    JLabel serverIPAddress;
    JTextField inputIP;
    JButton connectButton;
    JScrollPane contextPane;
    JTextArea context;
    JPanel inputPanel;
    JButton requestDataButton;
    JTextField inputText;

    String serverIP;

    ReceiveSocketRoll receiveSocket;

    ClientFrame(){
        setTitle("Client");
        setSize(400,600);
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        Container contentPane = getContentPane();

        addressPanel = new JPanel();
        serverIPAddress = new JLabel("Server's IP : ");
        inputIP = new JTextField(15);
        connectButton = new JButton("Connect");
        connectButton.addActionListener(this);
        addressPanel.add(serverIPAddress);
        addressPanel.add(inputIP);
        addressPanel.add(connectButton);

        context = new JTextArea();
        contextPane = new JScrollPane(context);

        inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2,1));
        requestDataButton = new JButton("RequestData");
        requestDataButton.addActionListener(this);
        inputText = new JTextField();
        inputText.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_ENTER) {
                    try {
                        String msgToSend = inputText.getText();

                        Socket server = new Socket(serverIP, 7000);
                        PrintWriter to_server = new PrintWriter(new OutputStreamWriter(server.getOutputStream()));

                        to_server.println("2");
                        to_server.flush();

                        to_server.println(msgToSend);
                        to_server.flush();
                        writeMessage("[Me] : " + msgToSend);
                        setTextNull();

                        server.close();
                        to_server.close();
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }
            }
        });
        inputPanel.add(requestDataButton);
        inputPanel.add(inputText);

        contentPane.add(addressPanel, "North");
        contentPane.add(contextPane, "Center");
        contentPane.add(inputPanel, "South");

    }
    public void writeMessage(String msg){
        context.append(msg + "\r\n");
    }
    public void setTextNull(){
        inputText.setText(null);
    }
    public void actionPerformed(ActionEvent e) {
        // 위에 connect버튼 anonymous오브젝트 일로옮기기
        Object source = e.getSource();
        if (source == connectButton) {
            try{
                receiveSocket = new ReceiveSocketRoll(this);
                receiveSocket.start();

                serverIP = inputIP.getText();
                Socket server = new Socket(serverIP, 7000);
                BufferedReader from_server = new BufferedReader(new InputStreamReader(server.getInputStream()));
                PrintWriter to_server = new PrintWriter(new OutputStreamWriter(server.getOutputStream()));

                to_server.println("0");
                to_server.flush();

                String msgFromServer = from_server.readLine();
                writeMessage("[Server] : " + msgFromServer);

                to_server.close();
                server.close();
            }catch (Exception ex){
                System.out.println(ex);
            }
        } else if (source == requestDataButton) {
            try {
                Socket server = new Socket(serverIP, 7000);
                BufferedReader from_server = new BufferedReader(new InputStreamReader(server.getInputStream()));
                PrintWriter to_server = new PrintWriter(new OutputStreamWriter(server.getOutputStream()));

                to_server.println("1");
                to_server.flush();

                String msgFromServer = from_server.readLine();
                writeMessage("[Server] : " + msgFromServer);

                to_server.close();
                server.close();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        } else if (source == requestDataButton) {
            try {
                Socket server = new Socket(serverIP, 7000);
                BufferedReader from_server = new BufferedReader(new InputStreamReader(server.getInputStream()));
                PrintWriter to_server = new PrintWriter(new OutputStreamWriter(server.getOutputStream()));

                to_server.println("1");
                to_server.flush();

                String msgFromServer = from_server.readLine();
                writeMessage("[Server] : " + msgFromServer);

                to_server.close();
                server.close();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }
}
