import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Scanner;

public class WorkerRunnable implements Runnable {

    protected Socket clientSocket = null;
    protected String serverText = null;

    public WorkerRunnable(Socket clientSocket, String serverText) {
        this.clientSocket = clientSocket;
        this.serverText = serverText;
    }

    @Override
    public void run() {

        try {
            System.out.println("Client " + clientSocket.getInetAddress() + " was connected to server");
            BufferedReader input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            PrintWriter output = new PrintWriter(this.clientSocket.getOutputStream(), true);
            ArrayList<Integer> integers = new ArrayList<>();
            Scanner scanner = null;

            try {
                scanner = new Scanner(new File("data.txt"));
            } catch (Exception ex) {
                System.out.println("Cannot open file data.txt");
                ex.printStackTrace();
            }

            while (scanner.hasNextInt()) {
                int number = scanner.nextInt();
                integers.add(number);
            }

            int i = 0;

            while (!clientSocket.isClosed()) {
                String request = "";

                try {
                    request = input.readLine();
                } catch (SocketException e) {
                    System.out.println();
                    input.close();
                    output.close();
                    clientSocket.close();
                    System.out.println("Connection for client" + clientSocket.getInetAddress() + " was closed");
                    break;
                }

                if (request == null || request.equalsIgnoreCase("quit") || i == integers.size()) {
                    output.println("Connection was closed");
                    input.close();
                    output.close();
                    clientSocket.close();
                    System.out.println("Connection for client" + clientSocket.getInetAddress()+" was closed");
                    break;
                }

                System.out.println(clientSocket.getInetAddress() + " Request: " + request);
                output.println(integers.get(i));

                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}