package com.mycompany.calcolatriceserver;
import java.io.*;
import java.net.*;
import java.util.*;

/**
 *
 * @author stei2
 */
public class Server {
    ServerSocket server = null;
    Socket client = null;
    String stringaRicevuta = null;
    String risultato = null;
    BufferedReader inputClient;
    DataOutputStream outputClient;
    
    public static void main(String args[]){
        Server server = new Server();
        for(;;){
            server.attendi();
            server.comunica();
        }
    }
    
    public Socket attendi(){
        try{
            System.out.println("Server in esecuzione");
            server = new ServerSocket(5678);
            client = server.accept();
            server.close();
            inputClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
            outputClient = new DataOutputStream(client.getOutputStream());
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("Errore durante l'instanza del server");
            System.exit(1);
        }
        return client;
    }
    
    public void comunica(){
        int a;
        int b;
        try {
            System.out.println("In attesa dell'input dal client...");
            stringaRicevuta = inputClient.readLine();
            System.out.println("Stringa ricevuta: "+stringaRicevuta);
            System.out.println("In attesa degli operandi...");
            a = Integer.parseInt(inputClient.readLine());
            System.out.println(a);
            b = Integer.parseInt(inputClient.readLine());
            System.out.println(b);
            
            if(stringaRicevuta.equals("somma")){
                risultato = Integer.toString(a+b);
            }else if(stringaRicevuta.equals("sottrazione")){
                risultato = Integer.toString(a-b);
            }else if(stringaRicevuta.equals("moltiplicazione")){
                risultato = Integer.toString(a*b);
            }else if(stringaRicevuta.equals("divisione")){
                risultato = Integer.toString(a/b);
            }
            
            outputClient.writeBytes(risultato+'\n');
            System.out.println("Risultato inviato al client");
            outputClient.close();
            inputClient.close();
            client.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Errore durante la comunicazione col client");
            System.exit(1);
        }
    }
}
