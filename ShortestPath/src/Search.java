import java.io.IOException;
import java.net.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Search {
    // Search, will allow user to pass ANY city in the US and receive
    // corresponding geocode for future distance calculations

    private int port;
    private String location;
    private URL url;
    private HttpURLConnection connection;
    private final String domain = "https://projects04sp.herokuapp.com";

    Search(int port) throws IOException {
        this.port = port;
    }


    public double[] geocoding(Map<String, String> location) throws IOException, ParseException {
        // our nodejs server, make get request ".../api/search/geocoding?city="CITY"&state="STATE"
        // receive geocode coordinates

        StringBuilder url_string = new StringBuilder(domain);

        url_string.append("/api/search/geocoding");

        String city = location.get("city").replaceAll(" ", "+").toUpperCase();

        // Build params ?city="CITY"&state="state"
        url_string.append("?city=" + city);
        url_string.append("&state=" + location.get("state"));

        url = new URL(url_string.toString());
        //System.out.println("REQUEST URL: " +  url_string);

        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("accept", "application/json");
        connection.setRequestMethod("GET");

        int status = connection.getResponseCode();

        // GET RESPONSE (json format, receive as string, convert back to json)
        StringBuilder res_string = new StringBuilder();
        JSONObject response = null;
        boolean success;


        switch(status){
            case 200:
            case 201:

                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = br.readLine()) != null) {
                    res_string.append(line+"\n");
                }
                br.close();

                JSONParser parser = new JSONParser();
                response = (JSONObject) parser.parse(res_string.toString());
                success = true;
                break;

            default:
                success = false;
                break;
        }

        // check valid coordinates received
        String lng = success ? response.get("lng").toString() : "";
        String lat = success ? response.get("lat").toString() : "";

        double[] lat_lng = {Double.parseDouble(lat), Double.parseDouble(lng)};

        return lat_lng;
    }
}
