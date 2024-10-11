
import org.webbitserver.BaseWebSocketHandler;
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

public class TanitaWebSockets extends BaseWebSocketHandler {
    private int connectionCount;

    public void onOpen(WebSocketConnection connection) {
        //connection.send("ACK");
        ProcessController.setSocket(connection);
        Log.info("Websocket client connected, counter:" + connectionCount );
        connectionCount++;
    }

    public void onClose(WebSocketConnection connection) {
        Log.info("Websocket client disconnected" );
        connectionCount--;
    }

    public void onMessage(WebSocketConnection connection, String message) {
        connection.send(message.toUpperCase()); // echo back message in upper case
        Log.info("Websocket message received:" +  message);

    }
}
