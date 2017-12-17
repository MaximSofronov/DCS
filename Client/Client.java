import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {
        String address = args[0];
        String port = args[1];
        InetAddress ipAddress = InetAddress.getByName(address);

        Socket socket = new Socket(ipAddress,Integer.parseInt(port));
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        System.out.println("Enter integer and action (a - addition, s - subtraction, m - multiplication, d - division)");
        System.out.println("Input example: 10 a (space between number and action is required)");
        System.out.println("To close connection write \"quit\"");

        while(!socket.isOutputShutdown()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            if (br.ready()) {
                String request_line = br.readLine();
                String numberStr = "";
                String expStr = "";

                if (request_line.split(" ").length == 2) {
                    numberStr = request_line.split(" ")[0];
                    expStr = request_line.split(" ")[1];
                }

                if (numberStr.matches("^-?\\d+$")) {
                    
                    if (expStr.equals("a")) {
                        String response = "";
                        out.println("addition");

                        try {
                            response = in.readLine();
                        } catch (SocketException es) {
                            System.out.println("Server disconnected.");
                            break;
                        }

                        if (response.equals("Connection was closed")) {
                            System.out.println("Numbers were ended.");
                            break;
                        }

                        System.out.println("Server response: " + response);
                        System.out.println("Result: " + numberStr + " + " + response + " = " + (Integer.parseInt(numberStr) + Integer.parseInt(response)));
                    }

                    if (expStr.equals("s")) {
                        String response="";
                        out.println("subtraction");

                        try {
                            response = in.readLine();
                        } catch (SocketException es) {
                            System.out.println("Server disconnected.");
                            break;
                        }

                        if (response.equals("Connection was closed")) {
                            System.out.println("Numbers were ended.");
                            break;
                        }

                        System.out.println("Server response: " + response);
                        System.out.println("Result: " + numberStr + " - " + response + " = " + (Integer.parseInt(numberStr) - Integer.parseInt(response)));
                    }

                    if (expStr.equals("m")) {
                        String response = "";
                        out.println("multiplication");

                        try {
                            response = in.readLine();
                        } catch (SocketException es) {
                            System.out.println("Server disconnected.");
                            break;
                        }

                        if (response.equals("Connection was closed")) {
                            System.out.println("Numbers were ended.");
                            break;
                        }

                        System.out.println("Server response: " + response);
                        System.out.println("Result: " + numberStr + " * " + response + " = " + (Integer.parseInt(numberStr) * Integer.parseInt(response)));
                    }

                    if (expStr.equals("d")) {
                        String response="";
                        out.println("division");

                        try {
                            response = in.readLine();
                        } catch (SocketException es) {
                            System.out.println("Server disconnected.");
                            break;
                        }

                        if (response.equals("Connection was closed")) {
                            System.out.println("Numbers were ended.");
                            break;
                        }

                        System.out.println("Server response: " + response);
                        System.out.println("Result: " + numberStr + " / " + response + " = " + (Integer.parseInt(numberStr) / Integer.parseInt(response)));
                    }
                } else {
                    if (request_line.equalsIgnoreCase("quit")) {
                        out.println("quit");
                        System.out.println("Client kill connections");
                        break;
                    }
                }
                System.out.println("Enter number");
            }

        }
    }
}

