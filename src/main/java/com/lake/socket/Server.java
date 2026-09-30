package com.lake.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.ServerSocket;
import java.net.Socket;

import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class Server {
    public static void main(String[] args) {

        // port number
        int porta = 2727;

        // Creazione Socket sulla porta
        try (ServerSocket serverSocket = new ServerSocket(porta)) {

            // Mostra che il server è pronto ad accettare connessioni
            System.out.printf("Server in ascolto sulla porta %d...", porta);

            // Attende una connessione dal Client (sincrono)
            Socket socket = serverSocket.accept();



            // Conferma che un client si è connesso
            System.out.printf("\n\nClient Connesso ---> %s\n\n", getInfoClientSocket(socket));




            // Lettura Bytes (dal Client) -> Caratteri
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream(),
                            StandardCharsets.UTF_8));

            // Oggetto per scrittura messaggio (Server -> Client)
            PrintWriter output = new PrintWriter(
                    new OutputStreamWriter(
                            socket.getOutputStream(),
                            StandardCharsets.UTF_8),
                    true);




            // Messaggio ricevuto dal Client
            String msg;

            while ((msg = input.readLine()) != null) {
                // Invia una risposta al client
                System.out.printf("%s ---> %s\n", getInfoClientSocket(socket), msg);
                output.println(msg.toUpperCase());
            }

            socket.close();

        }

        catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


    public static String getInfoClientSocket(Socket s){
        return String.format("%s:%d", s.getInetAddress().getHostAddress(), s.getPort());
    }
}
