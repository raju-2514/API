package API_CAll;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class APICaller {

    public static void main(String[] args) {
        String apiUrl = "https://dummyjson.com/auth/login";
        String postData = "key1=kminchelle & key2=0lelplR";

        try {
            String response = makePostRequest(apiUrl, postData);
            System.out.println("API Response: " + response);
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    public static String makePostRequest(String apiUrl, String postData) throws IOException {
        HttpURLConnection connection = getHttpURLConnection(apiUrl, postData);

        int responseCode = connection.getResponseCode();
        System.out.println("Response Code: " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    private static HttpURLConnection getHttpURLConnection(String apiUrl, String postData) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        byte[] postDataBytes = postData.getBytes(StandardCharsets.UTF_8);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));

        try (DataOutputStream out = new DataOutputStream(connection.getOutputStream())) {
            out.write(postDataBytes);
        }
        return connection;
    }
}
