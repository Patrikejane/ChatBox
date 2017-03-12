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
public class ServerDemo {
    public static void main (String[] args){
        Server sampleServer = new Server();
        sampleServer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        sampleServer.onStart();
        
    }
}
