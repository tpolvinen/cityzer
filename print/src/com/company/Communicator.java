package com.company;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.URL;

public class Communicator {

    public String getToken(String usr, String pwd) throws IOException{
        String token ="";

        try{
            SSLContextBuilder builder = new SSLContextBuilder();
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build());
            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            HttpPost request = new HttpPost("https://cityzerdb.fmi.fi/api/authenticate");
            request.addHeader("Content-Type", "application/json; charset=UTF-8");
            StringEntity parameters = new StringEntity("{\n" +
                    "    \"name\": \""+usr+"\",\n" +
                    "    \"password\": \""+pwd+"\"\n" +
                    "}");
            request.setEntity(parameters);
            CloseableHttpResponse response = httpclient.execute(request);
            String body = EntityUtils.toString(response.getEntity());
            token = body.substring(body.lastIndexOf(":")+2, body.length()-2);

        }catch (Exception ex){
            System.out.print(ex);
        }

        return token;
    }

    public void getData(String token){

        byte[] response = null;

       try{
           // Create a new trust manager that trust all certificates
           TrustManager[] trustAllCerts = new TrustManager[]{
                   new X509TrustManager() {
                       public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                           return null;
                       }
                       public void checkClientTrusted(
                               java.security.cert.X509Certificate[] certs, String authType) {
                       }
                       public void checkServerTrusted(
                               java.security.cert.X509Certificate[] certs, String authType) {
                       }
                   }
           };

            // Activate the new trust manager
           try {
               SSLContext sc = SSLContext.getInstance("SSL");
               sc.init(null, trustAllCerts, new java.security.SecureRandom());
               HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
           } catch (Exception e) {
           }



           SSLContextBuilder builder = new SSLContextBuilder();
           builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
           SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build());
           CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
           HttpGet request = new HttpGet("https://cityzerdb.fmi.fi/api/getLatest?type=HIRLAM");
           request.addHeader("x-access-token", token);
           URL url = new URL("https://cityzerdb.fmi.fi/api/getLatest?type=HIRLAM&token="+token);
           InputStream in = new BufferedInputStream(url.openStream());
           DataOutputStream out = new DataOutputStream(new FileOutputStream("C:\\Users\\a1500908\\Documents\\data.bin"));
           byte[] buffer = new byte[16000000];
           int n = 0;
           while(-1!=(n=in.read(buffer))){
               out.write(buffer, 0, n);
           }
           out.close();
           in.close();


       }catch (Exception ex) {
           System.out.print(ex);
       }


    }


}
