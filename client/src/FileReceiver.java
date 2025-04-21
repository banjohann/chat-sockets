import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileReceiver {

    public static void receive(String header, byte[] contentBytes) {
        try {
            String[] headerParts = header.split(":");
            String from = headerParts[0];
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String timestamp = formatter.format(new Date());

            String[] fileParts = headerParts[4].split("\\.");
            String filePath = fileParts[0] + "_" + timestamp + "." + fileParts[1];

            Path path = Paths.get("client/files", filePath).toAbsolutePath();
            Files.write(path, contentBytes);

            System.out.println("Recebido arquivo de " + from);
            System.out.println("Arquivo salvo em: " + path);
        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
