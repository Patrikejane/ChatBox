/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatbox;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author sunimal.malkakulage
 */
abstract class ChatBoxSuper extends JFrame{
    public JTextField typeArea;
    public JTextArea displayArea;
    public ObjectOutputStream outMassage;
    public ObjectInputStream inMassage;
    public Socket connection;
    
    public ChatBoxSuper(){
        super("MyChatAPP");
    }
    
    public ChatBoxSuper(String appTitle){
        super(appTitle);
    }
    
    
    public abstract void onStart();
    public abstract void onDelayConnection() throws IOException;
    public abstract void initStream() throws IOException;
    public abstract void onConnection() throws IOException;
    public abstract void onEndConnection();
    public abstract void sendMessage(String message);
    public abstract void showMessage(final String text);
    public abstract void activateFields(final boolean value);
}
