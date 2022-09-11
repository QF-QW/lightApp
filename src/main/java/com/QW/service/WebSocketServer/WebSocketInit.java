package com.QW.service.WebSocketServer;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.HashMap;

//public class WebSocketInit implements ServletContextListener {
//
//    /*获取我们存储的用户对像*/
//    ServletContext servletContext = null;
//
//    private class SimpleServer extends WebSocketServer {
//
//
//        public SimpleServer(InetSocketAddress address) {
//            super(address);
//        }
//
//        @Override
//        public void onOpen(WebSocket conn, ClientHandshake handshake) {
//            conn.send("Welcome to the server!"); //This method sends a message to the new client
//            broadcast("new connection: " + handshake.getResourceDescriptor()); //This method sends a message to all clients connected
//            System.out.println("new connection to " + conn.getRemoteSocketAddress());
//        }
//
//        @Override
//        public void onClose(WebSocket conn, int code, String reason, boolean remote) {
//            System.out.println("closed " + conn.getRemoteSocketAddress() + " with exit code " + code + " additional info: " + reason);
//
//
//        }
//
//        @Override
//        public void onMessage(WebSocket conn, String message) {
//            System.out.println("received message from " + conn.getRemoteSocketAddress() + ": " + message);
//            conn.send("Welcome to the server!"); //This method sends a message to the new client
//        }
//
//        @Override
//        public void onMessage(WebSocket conn, ByteBuffer message) {
//            System.out.println("received ByteBuffer from " + conn.getRemoteSocketAddress());
//        }
//
//        @Override
//        public void onError(WebSocket conn, Exception ex) {
//            System.err.println("an error occurred on connection " + conn.getRemoteSocketAddress() + ":" + ex);
//        }
//
//        @Override
//        public void onStart() {
//            System.out.println("server started successfully");
//        }
//
//    }
//    @Override
//    public void contextInitialized(final ServletContextEvent sce) {
//        /*增加线程监听*/
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                String host = "localhost";
//                int port = 10087;
//
//                WebSocketServer server = new SimpleServer(new InetSocketAddress(host, port));
//
//                ServletContext servletContext = sce.getServletContext();
//                server.run();
//            }
//        }).start();
//
//        System.out.println("触发");
//    }
//
//
//
//}

class SimpleServer extends WebSocketServer {


    public SimpleServer(InetSocketAddress address) {
        super(address);
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        conn.send("Welcome to the server!"); //This method sends a message to the new client
        broadcast("new connection: " + handshake.getResourceDescriptor()); //This method sends a message to all clients connected
        System.out.println("new connection to " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("closed " + conn.getRemoteSocketAddress() + " with exit code " + code + " additional info: " + reason);


    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("received message from " + conn.getRemoteSocketAddress() + ": " + message);
        conn.send("Welcome to the server!"); //This method sends a message to the new client
    }

    @Override
    public void onMessage(WebSocket conn, ByteBuffer message) {
        System.out.println("received ByteBuffer from " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.err.println("an error occurred on connection " + conn.getRemoteSocketAddress() + ":" + ex);
    }

    @Override
    public void onStart() {
        System.out.println("server started successfully");
    }

    public static void main(String[] args) {
        String host = "localhost";
        int port = 10087;

        HashMap<Integer, Object> active = new HashMap<>();



        WebSocketServer server = new SimpleServer(new InetSocketAddress(host, port));
        server.run();
    }

}