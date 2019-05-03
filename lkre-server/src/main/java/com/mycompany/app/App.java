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

    public static void main(String[] args) throws IOException {
        final ServerSocket server = new ServerSocket(PORT);
        System.out.println("Listenning connection on port " + PORT);
        while (true) {
            try (Socket socket = server.accept()) {
                systemOut(new InputStreamReader(socket.getInputStream()));
                Date today = new Date();
                String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + today;
                socket.getOutputStream().write(httpResponse.getBytes(UTF_8));
            }
        }
    }

    private static void systemOut(InputStreamReader inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(inputStream);
        String line = reader.readLine();
        while (!line.isEmpty()) {
            System.out.println(line);
            line = reader.readLine();
        }
    }
}

