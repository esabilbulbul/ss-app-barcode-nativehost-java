/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativehost;

import java.io.IOException;

/**
 *
 * @author esabil
 */
public class main {

    /**
     * @param args the command line arguments
     * 
     * 
     * This is NATIVE MESSAGING HOST of Chrome Extension for Barcode Printing (SHIPSHUK)
     * 
     */
    public static void main(String[] args)
    {
        // TODO code application logic here
        //System.out.println("NativeHost app is starting");
        
        String sIncomingMsg = receiveMessage();
        
        String sOutgoingMsg = "{\"text\":\"java host\"}";
        
        sendMessage(sOutgoingMsg);
        
    }

    //Convert length from Bytes to int
    public static int getInt(byte[] bytes) 
    {
        return (bytes[3] << 24) & 0xff000000|
                (bytes[2] << 16)& 0x00ff0000|
                (bytes[1] << 8) & 0x0000ff00|
                (bytes[0] << 0) & 0x000000ff;
    }

    // Read an input from Chrome Extension
    static public String receiveMessage()
    {

        byte[] b = new byte[4];

        try
        {
            System.in.read(b);
            int size = getInt(b);

            byte[] msg = new byte[size];
            System.in.read(msg);

            // make sure to get message as UTF-8 format
            String msgStr = new String(msg, "UTF-8");

            return msgStr;

        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }

    }
    
    public static byte[] getBytes(int length) 
    {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) ( length      & 0xFF);
        bytes[1] = (byte) ((length>>8)  & 0xFF);
        bytes[2] = (byte) ((length>>16) & 0xFF);
        bytes[3] = (byte) ((length>>24) & 0xFF);
        return bytes;
    }

    static public void sendMessage(String pMsg)
    {
        try 
        {
            System.out.write(getBytes(pMsg.length()));
            
            byte[] bytes = pMsg.getBytes();
            
            System.out.write(bytes);
        } 
        catch (IOException ex) 
        {
            ex.printStackTrace();
        }
    }


}
