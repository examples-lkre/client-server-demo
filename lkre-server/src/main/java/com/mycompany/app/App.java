package com.mycompany.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Hello world!
 */
public class App {
    private static final int PORT = 8080;
    private static String param1;

    public static void main(String[] args) throws IOException {
        final ServerSocket server = new ServerSocket(PORT);
        System.out.println("Listenning connection on port " + PORT);
        while (true) {
            try (Socket socket = server.accept()) {
                systemOut(new InputStreamReader(socket.getInputStream()));
                Date today = new Date();
                StringBuilder builder = new StringBuilder();
                builder.append("HTTP/1.1 200 OK\r\n\r\n" + today);
                builder.append(" : ");
                builder.append(param1);
                String httpResponse = builder.toString();
                socket.getOutputStream().write(httpResponse.getBytes(UTF_8));
            }
        }
    }

    private static void systemOut(InputStreamReader inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(inputStream);
        //Header
        String line = reader.readLine();
        while (!line.isEmpty()) {
            System.out.println(line);
            line = reader.readLine();
        }

        //Payload
        StringBuilder payloadBuilder = new StringBuilder();
        while (reader.ready()) {
            payloadBuilder.append((char) reader.read());
        }
        String payload = payloadBuilder.toString();
        param1 = payload;
        System.out.println("Payload data is: " + payload);
    }
}

