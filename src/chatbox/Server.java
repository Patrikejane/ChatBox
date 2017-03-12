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
public class Server extends ChatBoxSuper {

    private ServerSocket server;


    public Server() {
        super("Chat- Server Mode");
        //user Type area and trger event and send
        typeArea = new JTextField();
        typeArea.setEditable(false);

        typeArea.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        sendMessage(event.getActionCommand());
                        typeArea.setText("");
                    }
                }
        );
        //chatWindow
        add(typeArea, BorderLayout.NORTH);
        displayArea = new JTextArea();
        setSize(300, 150);
        setVisible(true);
    }
    //sever running
    @Override
    public void onStart(){
        try{
            server = new ServerSocket(6789,100); // new connection with server port and no of max connections
            while(true){
                try{// conversation continue
                    onDelayConnection();
                    initStream();
                    onConnection();
                }
                catch(EOFException eofException){// connection lost
                    showMessage("connection Lost! ");
                    
                }
                finally{
                    onEndConnection();
                }
                
            }
        }catch(IOException ioException){
            ioException.printStackTrace();
            
        }
    }
    @Override
    public void onDelayConnection() throws IOException {
        showMessage("waiting for a client...");
        connection = server.accept();// socket creates between sever and client
        showMessage("connected " + connection.getInetAddress().getHostName());
    }
    
    //stream send and recive
    @Override
    public void initStream() throws IOException {
        outMassage = new ObjectOutputStream(connection.getOutputStream());
        outMassage.flush();
        inMassage = new ObjectInputStream(connection.getInputStream());
        showMessage("streams are setup . . ");
    }
    
    // Conversation
    @Override
    public void onConnection() throws IOException {
        String message = "Connected : ";
        sendMessage(message);
        activateFields(true);
        do {
            try{
                message = (String) inMassage.readObject();
                showMessage("/n" + message);
            }
            catch (ClassNotFoundException CNFException) {
                showMessage("WTF");
            }
        } 
        while (!message.equals("CLIENT - END"));
    }
    
    @Override
    public void onEndConnection(){
        showMessage("/n closing connection..");
        activateFields(false);
        try{
            outMassage.close();
            inMassage.close();
            connection.close();
        }
        catch (IOException ioException){
            ioException.printStackTrace();
        }
    }
    
    @Override
    public void sendMessage(String message){
        try{
            outMassage.writeObject("SERVER - " + message);
            outMassage.flush();
            showMessage("/nSERVER - " + message);
        }
        catch(IOException ioException){
            displayArea.append("/n ERROR");
        }
    }
    
    @Override
    public void showMessage(final String text) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        displayArea.append(text);
                    }
                }
        );
    }
    
    @Override
    public void activateFields(final boolean value){
        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        typeArea.setEditable(value);
                    }
                }
        );
    }
}
