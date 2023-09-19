package com.freeLearn;

import java.net.HttpURLConnection;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSocketFactory;
import java.net.URL;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import javax.ws.rs.core.Response;
import org.json.simple.parser.JSONParser;

import org.json.*;

/**
 *
 * @author wailf
 */
@Stateless
@LocalBean
public class controller {

    ////////////info de doaj solo busqueda de articulos
    public String getartsPage(String words, String size) throws IOException {
        String respuesta = "";
        String url = "https://doaj.org/api/search/articles/" + words;
        System.out.println("pagina a traer:: " + url);
        url += "?page=1&pageSize="+size;
        URL obj = new URL(url);
        System.out.println("url DOAJ:: " + url);
        try {
            SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, null, new SecureRandom());
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) obj.openConnection();
            httpsURLConnection.setSSLSocketFactory(sslContext.getSocketFactory());
            httpsURLConnection.setRequestMethod("GET");
            httpsURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
            httpsURLConnection.setRequestProperty("Content-Type", "text/plain");
            httpsURLConnection.setRequestProperty("charset", "utf-8");
            httpsURLConnection.connect();
            int responseCode = httpsURLConnection.getResponseCode();
            System.out.println("code from page:: " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream(), "UTF8"));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                JSONObject json = new JSONObject(response.toString());
                JSONArray jsonArray = (JSONArray) json.get("results");

                ////respuesta=""+jsonArray.getJSONObject(1);
                for (int i = 0; i < jsonArray.length(); i++) {
                    respuesta += "" + jsonArray.getJSONObject(i);
                }
                System.out.println("respuesta de API Doaj:: " + respuesta);
            } else {
                System.out.println("fallo al intentar obtener la pagina http:: " + url);
            }
            httpsURLConnection.disconnect();
        } catch (SSLException ss) {
            System.out.println("fallo la conexion ssl de Doaj::: " + url);
            ss.printStackTrace();
        } catch (Exception e) {
            System.out.println("fallo la conexion de Doaj::: " + url);
            e.printStackTrace();
        }
        return respuesta;
    }
    

    public Response getHeaders(String resultado) {
        return Response.ok("")
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Content-Type", "text/plain;charset=UTF-8")
                .header("Access-Control-Max-Age", "1209600")
                .entity(resultado)
                .build();
    }
}
