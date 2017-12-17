import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.ArrayList;

public class MultiThreadedServer implements Runnable{

    protected int serverPort   = 8080;
    protected ServerSocket serverSocket = null;
    protected boolean isStopped    = false;
    protected Thread runningThread= null;
    protected ArrayList<Thread> arrThreads = null;

    public MultiThreadedServer(int port) {
        this.serverPort = port;
        this.arrThreads = new ArrayList<Thread>();
    }

    public void run(){
        synchronized(this) {
            this.runningThread = Thread.currentThread();
        }
        openServerSocket();
        while (!isStopped()){
            Socket clientSocket = null;
            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                if (isStopped()) {
                    System.out.println("Server Stopped.") ;
                    return;
                }
                throw new RuntimeException(
                        "Error accepting client connection", e);
            }

            Thread thread = new Thread(
                    new WorkerRunnable(
                            clientSocket, "Multithreaded Server")
            );
            thread.start();
        }

        System.out.println("Server Stopped.") ;
    }


    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop(){
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server ", e);
        }
    }

    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port 8080 ", e);
        }

        System.out.println("Server is listening...");
    }
}