/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stadiumbeta;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bancho
 */
public class StadiumServer {

    /**
     * @param args the command line arguments
     */
    
    public static final String IP = "localhost";
    public static final int PORT = 8080;

    public ServerSocket servSocket;
    
    private AtomicInteger connectedClientsATM;
    private AtomicInteger connectionsMade;
    private int counter;
    private ArrayList<Integer> c;

    
    private void startServer() throws IOException {
        connectedClientsATM = new AtomicInteger(0);
        connectionsMade = new AtomicInteger(0);
        counter = 0;
        c = new ArrayList<Integer>();
        
        servSocket = new ServerSocket();
        servSocket.bind(new InetSocketAddress(IP, PORT));
        System.out.println("Server started: " + new Date());
        while (true) {
            Socket socket = servSocket.accept(); //Blocks call until a client connects
            //System.out.println("A new client has connected");
            startNewThread(socket);
        }
    }

    public void handleClient(Socket s) {
        try {
            Scanner sc = new Scanner(s.getInputStream());
            PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
            boolean doRun = true;
            do {
                String msg = sc.nextLine(); //Blocks call until it gets a msg
                //System.out.println("Msg recieved: " + msg);
                switch(msg){
                    case "+1":
                        counter++;
                        break;
                    case "r1":
                        pw.println("All connections that were made: " + connectionsMade.get());
                        break;
                    case "r2":
                        pw.println("Count: " + counter);
                        break;
                    case "r3":
                        pw.println("Highest number of connected clients at the same time: " + Collections.max(c));
                        break;
                    case "r4":
                        pw.println("Current number of connections: " + connectedClientsATM.get());
                        break;
                    default:
                        s.close();
                        connectedClientsATM.decrementAndGet();
                        //System.out.println("A client has disconnected");
                        //System.out.println("Current number of connected clients: " + connectedClientsATM);
                        doRun = false;
                        break;
                }
            } while (doRun);
        } catch (Exception ex) {
            connectedClientsATM.decrementAndGet();
        }
    }

    private void startNewThread(Socket s) {
        ServerThread t = new ServerThread(this);
        t.setServSocket(s);
        t.start();
        connectedClientsATM.incrementAndGet();
        connectionsMade.incrementAndGet();
        c.add(connectedClientsATM.get());
        //System.out.println("Current number of connected clients: " + connectedClientsATM.get());
    }

    public static void main(String[] args) {
        try {
            new StadiumServer().startServer();
        } catch (IOException ex) {
            Logger.getLogger(StadiumServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    //borrowed method, just to check sth
    private void printNumOfRunningThreads() {
        int nbRunning = 0;
            for (Thread th : Thread.getAllStackTraces().keySet()) {
                if (th.getState() == Thread.State.RUNNABLE) {
                    nbRunning++;
                }
            }
            System.out.println("" + nbRunning);
    }
    
}
