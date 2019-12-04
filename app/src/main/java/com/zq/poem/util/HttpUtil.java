package com.zq.poem.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;
import java.util.Map;

/**
 * Created by SGXM on 2019/11/21.
 */

/**
* @Description: My custom class to send http request
* @Author: SGXM
* @Date: 2019/11/19
*/
public class HttpUtil {

/**host of my serve*/
private static final String BASE_URL = "http://sgxm.tech";

/** Singleton design pattern */
private static volatile HttpUtil ourInstance;

/** stream writer */
private BufferedWriter bufferedWriter;

/** stream reader */
private BufferedReader bufferedReader;

    /**
     * double check thread-safe and efficient
     *
     * @return the instance of this class
     */
public static HttpUtil getInstance() {
    if (ourInstance == null) {
        synchronized (HttpUtil.class) {
            if (ourInstance == null) {
                ourInstance = new HttpUtil();
            }
        }
    }
    return ourInstance;
}

private HttpUtil() {}


    /**
     * Socket coding
     *
     * Use socket to connect my serve and send http get request
     * then send data back to my model
     *
     * @param path request api
     * @param port which port will be request
     * @param query query params of get request
     * @return return the response of request which has been clipped
     */
public String httpGet(String path, int port, Map<String, String> query) {
    StringBuilder url = new StringBuilder(BASE_URL + ":" + String.valueOf(port) + path);
    if (!query.isEmpty()) {
        url.append("?");
        for (Map.Entry<String, String> entry : query.entrySet()) {
            url.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        url.deleteCharAt(url.length() - 1);
    }
    StringBuilder res = new StringBuilder();
    try {
        SocketAddress dest = new InetSocketAddress(new URL(url.toString()).getHost(), port);
        Socket socket = new Socket();
        socket.connect(dest);
        OutputStreamWriter streamWriter = new OutputStreamWriter(socket.getOutputStream());
        bufferedWriter = new BufferedWriter(streamWriter);
        bufferedWriter.write("GET " + url + " HTTP/1.0\r\n");
        bufferedWriter.write("Host:" + BASE_URL + "\r\n");
        bufferedWriter.write("\r\n");
        bufferedWriter.flush();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream());
        bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "utf-8"));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            res.append(line);
        }
        bufferedReader.close();
        bufferedWriter.close();
        socket.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
    return res.substring(res.indexOf("GMT") + 3);

}
}
