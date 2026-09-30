package com.lake.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.Socket;
import java.util.Scanner;

import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class Client {

        public static void main(String[] args) {
                String host = "172.16.125.221";
                int porta = 2727;

                //
                try (
                                Socket socket = new Socket(host, porta);

                                // Input Tastiera

                                Scanner keyInput = new Scanner(
                                                System.in,
                                                StandardCharsets.UTF_8.name());

                                // oggetto per leggere messaggi dal server
                                BufferedReader input = new BufferedReader(
                                                new InputStreamReader(
                                                                socket.getInputStream(),
                                                                StandardCharsets.UTF_8));

                                // Oggetto per inviare messaggi al server
                                PrintWriter output = new PrintWriter(
                                                new OutputStreamWriter(
                                                                socket.getOutputStream(),
                                                                StandardCharsets.UTF_8),
                                                true);

                ) {

                        System.out.printf("Connesso al Server %s:%d\n\nScrivi un messaggio oppure `exit` per uscire: ",
                                        socket.getInetAddress().getHostAddress(), socket.getPort());

                        while (keyInput.hasNextLine()) {

                                // Legge una riga digitata dall'utente
                                String msg = keyInput.nextLine();

                                // Invia il messaggio al server
                                output.println(msg);

                                // exit command
                                if (msg.equalsIgnoreCase("exit"))
                                        break;


                                String response = input.readLine();


                                // Check se il server ha chiuso la connessione prima dell'invio del prossimo messaggio
                                if (response == null) {
                                        System.out.println("Il Server ha chiuso la connessione");
                                        break;
                                }

                                System.out.printf("\n[SERVER]: %s\n\nScrivi un altro messaggio: ", response);
                        }

                } catch (Exception e) {
                        System.out.println(e.getMessage());
                }
        }
}
