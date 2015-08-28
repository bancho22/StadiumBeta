/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stadiumbeta;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bancho
 */
public class ClientThread extends Thread {
    
    private int id;

    public ClientThread(int id) {
        this.id = id;
    }
    

    @Override
    public void run() {
        TurnstileClient c = new TurnstileClient();
        try {
            c.connect("localhost", 8080);
            Thread.sleep(1000);
            //System.out.println(id);
            c.send("+1");
            //System.out.println(id);
            c.send("hoi");
        } catch (IOException ex) {
            Logger.getLogger(TurnstileClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
