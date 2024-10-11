
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.webbitserver.WebSocketConnection;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author para
 */
public class ProcessController extends Thread{
    
    ProcessController() throws InterruptedException
    {
        ProcessSemaphore.acquire();
        dataModel = new TanitaDataModel();
    }
    private final TanitaDataModel dataModel;
    private static WebSocketConnection connection = null;
    private static final Lock lock = new ReentrantLock();;
    private static final Semaphore ProcessSemaphore = new Semaphore(1);

    private static String message = null;
    public static void setSocket( WebSocketConnection aConnection)
    {
        lock.lock();
        try {
            connection = aConnection;
            Log.info( "Socket set");
        } finally {
            lock.unlock();
        }
    }
    
    static public void sendMessage( String aMessage)
    {
        lock.lock();
        try {
            if (null != message)
            {
                Log.warn( "Message overwritten, problem with web socket ?");
            }
            message = aMessage;
            
            Log.info( "Message from tanita stored");
        } finally {
            lock.unlock();
        }
        ProcessSemaphore.release();
    }
    
     @Override
    public void run() {
        String dataToBesent;
        try {
            while (true) {
                //blocked and waiting for message
                ProcessSemaphore.acquire();

                if( null !=connection)
                {
                    List<TanitaDataModel.OneEntry> parsedData = null;

                    try
                    {
                        parsedData = dataModel.parseFromString(message);
                    }
                    catch( Exception ex)
                    {
                        Log.error("Problem during parsing input string:" + ex);
                    }
                    
                    for (TanitaDataModel.OneEntry item : parsedData) {
                        if( item.JavaScriptID.length() > 1) 
                        {
                            dataToBesent = item.JavaScriptID+";"+item.Value;
                            connection.send(dataToBesent);
                            Log.info("Entry send to browser:" + dataToBesent);
                        }
                    }
                    
                    Log.info( "Lock released, message send to websocket:" + message);
                    lock.lock();
                    try {
                        message = null;
                    } finally {
                        lock.unlock();
                    }

                }
                else
                {
                    Log.error( "Null WEB socket");
                }
            }
        } catch (InterruptedException e) {
        }
    }
}
