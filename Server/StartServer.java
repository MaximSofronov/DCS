import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StartServer {
    public static void main(String[] args) throws IOException {
        MultiThreadedServer server = new MultiThreadedServer(Integer.parseInt(args[0])); 
        new Thread(server).start();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            if (br.ready()) {
                String line = br.readLine();
                if (line.equalsIgnoreCase("stop")) {
                    break;
                }
            }
        }

        System.out.println("Stopping Server");
        server.stop();
        System.exit(0);
    }
}