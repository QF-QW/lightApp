package Test;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

/*
* 我们需要进行用户配对  先获取用户对象
* websocketServer 开启时间
* */

public class WebSocketTest extends HttpServlet {
    ServletContext servletContext = null;





//        public static void main(String[] args) {
//            String host = "localhost";
//            int port = 10087;
//
//            WebSocketServer server = new SimpleServer(new InetSocketAddress(host, port));
//            server.run();
//        }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {



    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}

