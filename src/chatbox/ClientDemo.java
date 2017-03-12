/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatbox;

import javax.swing.JFrame;

/**
 *
 * @author sunimal.malkakulage
 */
public class ClientDemo {
    public static void main (String[] args){
        Client sampleClient = new Client("127.0.0.1");
        sampleClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        sampleClient.onStart();
        
    }
}
