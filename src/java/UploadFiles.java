import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class UploadFiles {
    static boolean isAddResource = false;

    public static void main(String[] args) {
        File file = null;
        File[] files1 = new File(System.getProperty("user.dir")).listFiles();
        if (files1 != null) {
            for (File files : files1) {
                if (files.getName().equals("src")) {
                    file = new File(files.getName());
                }
            }
        } else {
            System.out.println("找不到目标文件夹");
        }
        //false不上传资源文件夹
        isAddResource = true;

        File[] files = new File[0];
        if (file != null) {
            files = file.listFiles();
        }
        if (files != null) {
            iterate(files);
        }
    }

    public static void sand(File file) {
//        try (Socket socket = new Socket("127.0.0.1", 33394);
        try (Socket socket = new Socket("123.56.82.129", 33394);
             OutputStream outputStream = socket.getOutputStream();
             FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] bytes = new byte[1024];
            int length;
            outputStream.write(file.getName().getBytes().length);
            outputStream.write(file.getName().getBytes());
            while ((length = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, length);
            }
            socket.close();
            System.out.println("上传成功：" + "\"" + file.getName() + "\"");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void iterate(File[] files) {
        for (File file : files) {
            if (file.getName().equals(".git") || file.getName().equals("java")) {
                continue;
            }
            if (!isAddResource)
                if (file.getName().equals("resource")) continue;
            if (file.isDirectory()) {
                File[] listFiles = file.listFiles();
                if (listFiles != null) {
                    iterate(listFiles);
                }
            } else {
                sand(file);
            }
        }
    }
}