package com.example.simplehttpserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import sun.security.util.IOUtils;
public class EchoPostHandler implements HttpHandler {

    @Override

    public void handle(HttpExchange he) throws IOException {
        // parse request
        Map<String, Object> parameters = new HashMap<String, Object>();
        InputStreamReader isr = new InputStreamReader(he.getRequestBody(), "utf-8");
        Scanner s = new Scanner(isr).useDelimiter("\\A");
        String reqBodyString = s.hasNext() ? s.next() : "";
        s.close();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map = null;

        try {

            // convert JSON string to Map
             map = mapper.readValue(reqBodyString, Map.class);

//            System.out.println(map);

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(reqBodyString);
        // send response
        String response = "";
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "/" + entry.getValue());
        }
//
        String json=null;
//        json = mapper.writeValueAsString(map);

        System.out.println(json);   // compact-print

        json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);

        System.out.println(json);
        System.out.println(json.getBytes());

//        catch (JsonProcessingException e){
//            System.out.println("json exception");
//            e.printStackTrace();
//
//        }
        OutputStream os = he.getResponseBody();

        he.sendResponseHeaders(202, json.length());

        os.write(json.getBytes());
        os.close();
    }

    public static void parseQuery(String query, Map<String,
            Object> parameters) throws UnsupportedEncodingException {

        if (query != null) {
            String pairs[] = query.split("[&]");
            for (String pair : pairs) {
                String param[] = pair.split("[=]");
                String key = null;
                String value = null;
                if (param.length > 0) {
                    key = URLDecoder.decode(param[0],
                            System.getProperty("file.encoding"));
                }

                if (param.length > 1) {
                    value = URLDecoder.decode(param[1],
                            System.getProperty("file.encoding"));
                }

                if (parameters.containsKey(key)) {
                    Object obj = parameters.get(key);
                    if (obj instanceof List<?>) {
                        List<String> values = (List<String>) obj;
                        values.add(value);

                    } else if (obj instanceof String) {
                        List<String> values = new ArrayList<String>();
                        values.add((String) obj);
                        values.add(value);
                        parameters.put(key, values);
                    }
                } else {
                    parameters.put(key, value);
                }
            }
        }
    }
}