package edu.io.logic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpDataSource implements DataSource {

    private static final String API_URL = "https://api.metals.live/v1/spot/platinum";

    @Override
    public DataPack getData() {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            String json = response.toString();


            double price = extractDouble(json, "\"platinum\":");
            long timestamp = (long) extractDouble(json, "\"timestamp\":");

            if (timestamp == 0) {
                timestamp = System.currentTimeMillis() / 1000;
            }

            return new DataPack(price, timestamp);

        } catch (Exception e) {

            return new DataPack(Math.random() * 1000 + 800, System.currentTimeMillis() / 1000);
        }
    }

    private double extractDouble(String text, String key) {
        int idx = text.indexOf(key);
        if (idx == -1) return 0;
        int start = idx + key.length();
        int end = start;
        while (end < text.length() &&
                (Character.isDigit(text.charAt(end)) || text.charAt(end) == '.' || text.charAt(end) == '-')) {
            end++;
        }
        try {
            return Double.parseDouble(text.substring(start, end).trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
