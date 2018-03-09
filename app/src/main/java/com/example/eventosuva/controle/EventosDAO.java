package com.example.eventosuva.controle;

import com.google.gson.stream.MalformedJsonException;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by DrGreend on 29/10/2017.
 */

public class EventosDAO {
    private int TIMEOUT_MILLISEC = 300000;
    private static final String USER_AGENT = "Mozilla/5.0";

    public String request(String stringUrl){
        HttpURLConnection conn = null;
        BufferedReader in = null;
        try {
            URL obj = new URL(stringUrl);
            conn = (HttpURLConnection) obj.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", USER_AGENT);
            int responseCode = conn.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
                System.out.println("CONECTOU! " + responseCode);
            } else {
                //System.out.println("DEU RUIM!!");
            }
            System.out.println("\nSending 'GET' request to URL : " + stringUrl);
            System.out.println("Response Code : " + responseCode);

            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println("CONECTOU!"+response.toString());
            return response.toString();
        } catch (MalformedJsonException e) {
            System.out.println("Erro: " + e.getMessage());
            return null;
        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
            return null;
        } finally {
            if(conn != null) {
                conn.disconnect();
            }
            try{
                if(in != null){
                    in.close();
                }
            } catch(IOException e){
                System.out.println("Erro!! "+ e.getMessage());
            }
        }
    }

    public String post(String url, String tipo, String urlParameters) {
        HttpsURLConnection conn = null;
        BufferedReader in = null;
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod(tipo);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Post parameters : " + urlParameters);
            System.out.println("Response Code : " + responseCode);

            in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            System.out.println(response.toString());
            return response.toString();

        } catch (MalformedJsonException e) {
            System.out.println("Erro: " + e.getMessage());
            return null;
        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
            return null;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                System.out.println("Erro!! " + e.getMessage());
            }
        }
    }
}