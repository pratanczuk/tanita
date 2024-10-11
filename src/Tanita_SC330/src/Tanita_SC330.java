import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.webbitserver.BaseWebSocketHandler;
import org.webbitserver.WebServer;
import org.webbitserver.WebServers;
import org.webbitserver.handler.StaticFileHandler;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author para
 */
public class Tanita_SC330 {
    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
    
    
        
        // TODO code application logic here
        
        ProcessController process = new ProcessController();
        process.start();
        
        SerialPortReader mySerialPort = new SerialPortReader();
    
        WebServer webServer;
        webServer = WebServers.createWebServer(80);
        webServer.add("/tanita", new TanitaWebSockets());
        webServer.add(new StaticFileHandler("/web"));
        webServer.start();

        Log.set(Log.LEVEL_INFO);
        Log.info("Server running at " + webServer.getUri());

    // load application properties with default
    Properties applicationProps = new Properties();
     try (
        FileInputStream in = new FileInputStream("appProperties.conf")) {
        applicationProps.load(in);
     }
     catch( Exception ex)
     {
         try
         {
             try ( // file not exist, create default
                 FileOutputStream out = new FileOutputStream("appProperties.conf")) {
                 applicationProps.setProperty("ComPort", "COM1");
                 applicationProps.store(out, null);
             }
         }
         catch(Exception e)
         {
             Log.error("Error reading config file");
         }
     }
    Log.info("Connecting to com port:" + applicationProps.getProperty("ComPort"));
    
    mySerialPort.SetPort( applicationProps.getProperty("ComPort"));
    mySerialPort.Connect();
    
    
    }
    
}
