import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class UploadFilesServer {
    static ServerSocket serverSocket;
    static InputStream inputStream;

    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(33394);
            while (true) {
                Socket socket = serverSocket.accept();
                reception(socket);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (inputStream != null) inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void reception(Socket socket) throws IOException {
        inputStream = socket.getInputStream();
        byte[] bytes = new byte[1024];
        int length;
        byte[] fileBytes = new byte[inputStream.read()];
        inputStream.read(fileBytes);
        String fileName = new String(fileBytes);
        if (fileName.endsWith(".css")) {
//            try (FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\AfeiN\\Desktop\\Test\\css\\" + fileName)) {
            try (FileOutputStream fileOutputStream = new FileOutputStream("/var/www/html/css/" + fileName)) {
                while ((length = inputStream.read(bytes)) != -1) {
                    fileOutputStream.write(bytes, 0, length);
                }
            }
        } else if (fileName.endsWith(".html")) {
//            try (FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\AfeiN\\Desktop\\Test\\" + fileName)) {
            try (FileOutputStream fileOutputStream = new FileOutputStream("/var/www/html/" + fileName)) {
                while ((length = inputStream.read(bytes)) != -1) {
                    fileOutputStream.write(bytes, 0, length);
                }
            }
        } else {
//            try (FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\AfeiN\\Desktop\\Test\\resource\\" + fileName)) {
            try (FileOutputStream fileOutputStream = new FileOutputStream("/var/www/html/resource/" + fileName)) {
                while ((length = inputStream.read(bytes)) != -1) {
                    fileOutputStream.write(bytes, 0, length);
                }
            }
        }
        socket.close();
    }
}