import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TestServer {
    public static void main(String[] args) {
        new Thread(new Server()).start();
        try (Socket socket = new Socket("127.0.0.1", 33393)) {
            OutputStream outputStream = socket.getOutputStream();
                outputStream.write("nihao\n".getBytes());
                outputStream.write("a\n".getBytes());
                outputStream.write("nd\n".getBytes());
                outputStream.write("ahao\n".getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

class Server implements Runnable {

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(33393)) {
            Socket socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String string = "";
            while ((string = bufferedReader.readLine()) != null)
                System.out.println(string);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}