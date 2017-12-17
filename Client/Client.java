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
        System.out.println("Enter number");
        while(!socket.isOutputShutdown()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            if (br.ready()) { //данные на консоли - работаем
                String request_line = br.readLine();
                String numberStr="";
                String expStr="";

                if (request_line.split(" ").length==2) {
                    numberStr = request_line.split(" ")[0];
                    expStr = request_line.split(" ")[1];
                }

                if(numberStr.matches("^-?\\d+$") && numberStr.length() < 100000) {
                    if(expStr.equals("+")) {
                        String response = "";
                        out.println("request '+'");

                        try {
                            response = in.readLine();
                        } catch (SocketException es) {
                            System.out.println("Server disconnected.");
                            break;
                        }

                        if(response.equals("Connection was closed")) {
                            System.out.println("Numbers were ended.");
                            break;
                        }

                        System.out.println("Server response: " + response);
                        System.out.println("Result: " + numberStr + " + "+response + "=" + (Integer.parseInt(numberStr) + Integer.parseInt(response)));
                    }

                    if (expStr.equals("-")) {
                        String response="";
                        out.println("request '-'");

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

                    if(expStr.equals("*")) {
                        String response = "";
                        out.println("request '*'");

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

                    if (expStr.equals("/")) {
                        String response="";
                        out.println("request '/'");

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
                    if(request_line.equalsIgnoreCase("quit")) {
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

