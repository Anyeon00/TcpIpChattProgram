import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.PrimitiveIterator;

public class ServerSocketRoll extends Thread implements KeyListener {
    static int connect = 0;
    static int returnDataService = 1;
    static int dialogueService = 2;

    ServerFrame mainFrame;
    ServerSocket myPort;
    Socket client;
    String clientIP;
    ServerSocketRoll(ServerFrame mainFrame){
        this.mainFrame = mainFrame;
    }

    public void run(){
        try {
            myPort = new ServerSocket(7000);
            clientIP = "";

            for (; ; ) {
                client = myPort.accept();

                BufferedReader from_client = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter to_client = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));

                String serviceType = from_client.readLine();
                int typeNum = Integer.parseInt(serviceType);
                if (typeNum == connect) {
                    clientIP = client.getInetAddress().getHostAddress();
                    mainFrame.writeMessage("Connected with [" + clientIP + "]...");

                    to_client.println("Welcome!");
                    to_client.flush();
                } else if (typeNum == returnDataService) {
                    to_client.println("fuckyou");
                    to_client.flush();
                    mainFrame.writeMessage("Send \"fuckyou\"");
                } else if (typeNum == dialogueService) {
                    String receivedMsg = from_client.readLine();
                    mainFrame.writeMessage("[Client] : " + receivedMsg);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        try {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_ENTER) {
                Socket sendToClient = new Socket(clientIP, 7000);
                PrintWriter to_client = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));

                String msg = mainFrame.inputContext.getText();
                to_client.println(msg);
                to_client.flush();

                mainFrame.inputContext.setText(null);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
