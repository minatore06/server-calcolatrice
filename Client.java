package com.mycompany.calcolatriceclient;
import java.io.*;
import java.net.*;
/**
 *
 * @author stei2
 */
public class Client {
    String nomeServer = "localhost";
    int portaServer = 5678;
    Socket mioSocket;
    BufferedReader tastiera;
    String stringaClient;
    String stringaServer;
    DataOutputStream outputServer;
    BufferedReader inputServer;
    
    public static void main(String[] args) {
        Client cliente = new Client();
        cliente.connetti();
        cliente.comunica();
    }
    
    public Socket connetti(){
        System.out.println("Client in esecuzione!");
        try {
            tastiera = new BufferedReader(new InputStreamReader(System.in));
            mioSocket = new Socket(nomeServer,portaServer);
            
            outputServer = new DataOutputStream((mioSocket.getOutputStream()));
            inputServer = new BufferedReader(new InputStreamReader(mioSocket.getInputStream()));
            
        } catch(UnknownHostException e){
            System.err.println("Host sconosciuto");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Errore durante la connessione");
            System.exit(1);
        }
        return mioSocket;
    }
    
    public void comunica(){
        String operando;
        try {
            System.out.println("Scegliere tra somma, sottrazione, moltiplicazione e divisione");
            stringaClient = tastiera.readLine();

            while(!(stringaClient.equals("somma")||stringaClient.equals("sottrazione")||stringaClient.equals("moltiplicazione")||stringaClient.equals("divisione"))){
                System.out.println("Input non valido");
                stringaClient = tastiera.readLine();
            }

            System.out.println("Invio stringa in corso...");
            outputServer.writeBytes(stringaClient+'\n');
            
            System.out.println("Inserire il primo operando");
            operando = tastiera.readLine();
            outputServer.writeBytes(operando+'\n');
            
            System.out.println("Inserire il secondo operando");
            operando = tastiera.readLine();
            
            while(stringaClient.equals("divisione")&&operando.equals("0")){
                System.out.println("Divisione per 0 vietata");
                operando = tastiera.readLine();
            }
            
            outputServer.writeBytes(operando+'\n');

            stringaServer = inputServer.readLine();
            System.out.println("Risultato: "+stringaServer);
            
            System.out.println("Chiusura connessione");
            outputServer.close();
            inputServer.close();
            mioSocket.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Errore durante la comunicazione col server");
            System.exit(1);
        }
    }
    
}
