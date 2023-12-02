import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.ref.Cleaner;
import java.net.*;

public class ReceiveSocketRoll extends Thread{
    ClientFrame mainFrame;
    ServerSocket receiveSocket;
    ReceiveSocketRoll(ClientFrame mainFrame){
        this.mainFrame = mainFrame;
    }
    public void run(){
        try {
            receiveSocket = new ServerSocket(7000);
            
            for (; ; ) {
                Socket server = receiveSocket.accept();
                BufferedReader from_server = new BufferedReader(new InputStreamReader(server.getInputStream()));

                String msg = from_server.readLine();
                mainFrame.writeMessage("[Server] : " + msg);

                server.close();
                from_server.close();
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
