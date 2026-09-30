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
        String homeString = String.format("Server in ascolto sulla porta %d...\n", porta);
        

        // Creazione Socket sulla porta
        try (ServerSocket serverSocket = new ServerSocket(porta)) {

            // Mostra che il server è pronto ad accettare connessioni
            System.out.print(homeString);


            while (true) {
                // Attende una connessione dal Client (sincrono)
                try(Socket socket = serverSocket.accept()){
                    String client = getInfoClientSocket(socket);

                    // Conferma che un client si è connesso
                    System.out.printf("\nClient Connesso ---> %s\n", client);

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


                    //Messaggio ricevuto dal client
                    String msg;

                    while ((msg = input.readLine()) != null) {
                        //Check "EXIT" command
                        if(msg.equalsIgnoreCase("exit")) throw new Exception(String.format("%s ---> DISCONNECTED\n\n%s", client, homeString));
                        
                        // Invia una risposta al client
                        System.out.printf("%s ---> %s\n", client, msg);
                        output.println(msg.toUpperCase());
                    }

                    socket.close();

                }
                catch(Exception e){
                    System.out.println(e.getMessage());
                }
                
            }
        }

        catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static String getInfoClientSocket(Socket s) {
        return String.format("%s:%d", s.getInetAddress().getHostAddress(), s.getPort());
    }
}
