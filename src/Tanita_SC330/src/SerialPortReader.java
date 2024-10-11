/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author para
 */

import jssc.SerialPortList;
import jssc.SerialPort;
import jssc.SerialPortException;
import java.util.Arrays;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import org.apache.commons.codec.binary.Hex;


public class SerialPortReader 
{
    private
    String Port;
    int baudRate    =      SerialPort.BAUDRATE_9600;
    int dataBits    =      SerialPort.DATABITS_8;
    int stopBits    =      SerialPort.STOPBITS_1;
    int parity      =      SerialPort.PARITY_NONE;
    int flowControl =      SerialPort.FLOWCONTROL_NONE;    
    
    static SerialPort serialPort;

    public SerialPortReader( ) {
        this.Port = "";
    }
    
    public void SetPort( String aPort)
    {
        Port = aPort;
    }
    
    public static String [] GetPorts()
    {
                                    
        String[] portNames = SerialPortList.getPortNames();

        return portNames;    
    }
    
    boolean Connect()
    {
        if(serialPort != null)  Disconnect();

        try {
            serialPort = new SerialPort( Port);
            serialPort.openPort();//Open serial port
            serialPort.setParams( baudRate, dataBits, stopBits, parity);//Set params.
            int mask = SerialPort.MASK_RXCHAR;//Prepare mask
            serialPort.setEventsMask(mask);//Set mask
            serialPort.addEventListener(new SerialPortListener());//Add SerialPortEventListener
            Log.info( "Port succesfully conected");
        }
        catch (SerialPortException ex) {
            Log.error(ex.toString());
            return false;
        }
    return true;
    }
    
    boolean Disconnect()
    {
        try {
            if( serialPort.isOpened()) 
            {
                serialPort.closePort();
                Log.info( "Info: Prot succesfully closed");

            }//Close serial port
            else
            {
                Log.error( "Error: Prot already closed");
                return false;
            }
        }
        catch (SerialPortException ex) {
            Log.error(ex.toString());
            return false;
        }
    return true;
    }

    String read()
    {
        byte[] buffer = new byte[512];
        if( !serialPort.isOpened())
        {
            Log.error( "Error: Prot not opened");
        }
        else
        {
            try {
                serialPort.openPort();//Open serial port
                serialPort.setParams( baudRate, dataBits, stopBits, parity);//Set params.
                buffer = serialPort.readBytes();//Read 10 bytes from serial port
                Log.info( "Info: Data received");
            }
            catch (SerialPortException ex) {
                Log.error(ex.toString());
            }
        }
        return Arrays.toString(buffer);
     }

  static class SerialPortListener implements SerialPortEventListener 
  {
        StringBuilder message = new StringBuilder();
        byte previousByte = ' ';
        @Override
        public void serialEvent(SerialPortEvent event) 
        {
            if(event.isRXCHAR())
            {//If data is available
                Log.info( "Event received, data len:" + event.getEventValue());
                
                if(event.getEventValue() > 0)
                {//Check bytes count in the input buffer
                    //Read data, if 10 bytes available 
                    try 
                    {
                        byte buffer[] = serialPort.readBytes();
                        if( null != buffer) Log.info( "Data Len:" + buffer.length + "\t\tBuffer:" + Hex.encodeHexString(buffer) + "\n\tString:" + buffer.toString());
                        for (byte b: buffer) 
                        {
                            //expected end CR LF
                            if ( ( previousByte == '\r' && b == '\n') && message.length() > 0) 
                            {
                                ProcessController.sendMessage(message.toString());
                                Log.info("Data from device:" + message.toString());
                                message.setLength(0);
                            }
                            else 
                            {
                                message.append((char)b);
                            }
                            previousByte = b;
                        }                
                    }
                    catch (SerialPortException ex) 
                    {
                        Log.error(ex.toString());
                    }
                }
            }
            else
            {
                Log.error( "Info: unknown event:" + event.toString());
            }
        }
    }
}    