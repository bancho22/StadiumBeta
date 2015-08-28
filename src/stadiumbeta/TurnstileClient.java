/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stadiumbeta;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bancho
 */
public class TurnstileClient {

    //STILL NOT A TURNSTILE CLIENT
    //JUST CONTAINS THE BASIC INFRASTRUCTURE OF A CLIENT
    
    private Socket socket;
    private PrintWriter pw;
    private Scanner sc;
    private String IP;
    private int PORT;
    
    
    public void connect(String IP, int PORT) throws IOException {
        this.IP = IP;
        this.PORT = PORT;
        socket = new Socket(IP, PORT);
        sc = new Scanner(socket.getInputStream());
        pw = new PrintWriter(socket.getOutputStream(), true);
    }

    public void send(String msg) {
        pw.println(msg);
    }

    public String receive() {
        return sc.nextLine(); //blocks
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            new ClientThread(i).start();
        }
    }
}
