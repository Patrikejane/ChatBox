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
public class Client extends ChatBoxSuper{
    
    private String message;
    private String serverIP;
    

    public Client(String host){
        super("Chat - Client Mode");
        serverIP = host;
        
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
    
    
    

    @Override
    public void onStart() {
        try{
            onDelayConnection();
            initStream();
            onConnection(); 
        }
        catch(EOFException eofException){
            showMessage("\n Client loss connection");
        }
        catch(IOException ioException){
            ioException.printStackTrace();
        }
        finally{
            onEndConnection();
        }
        
    }

    @Override
    public void onDelayConnection() throws IOException {
        showMessage("Connecting......");
        connection = new Socket(InetAddress.getByName(serverIP),6789);
        showMessage("connected to : " + connection.getInetAddress().getHostName());
    }

    @Override
    public void initStream() throws IOException {
        outMassage = new ObjectOutputStream(connection.getOutputStream());
        outMassage.flush();
        inMassage = new ObjectInputStream(connection.getInputStream());
        showMessage("\n ..streams are setup . . ");
    }

    @Override
    public void onConnection() throws IOException {
        activateFields(true);
        do {
            try {
                message = (String) inMassage.readObject(); // read the massage 
                showMessage("/n" + message);
            } catch (ClassNotFoundException CNFException) {
                showMessage("WTF");
            }
        } while (!message.equals("CLIENT - END"));
    }

    @Override
    public void onEndConnection() {
        showMessage("/n closing connection..");
        activateFields(false);
        try {
            outMassage.close();
            inMassage.close();
            connection.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    @Override
    public void sendMessage(String message) {
        try {
            outMassage.writeObject("CLIENT - " + message);
            outMassage.flush();
            showMessage("/nCLIENT - " + message);
        } catch (IOException ioException) {
            displayArea.append("/n ERROR");
        }
    }

    @Override
    public void showMessage(String text) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        displayArea.append(text);
                    }
                }
        );
    }

    @Override
    public void activateFields(boolean value) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        typeArea.setEditable(value);
                    }
                }
        );
    }

}
