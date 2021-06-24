package sang.mobileprogramming.iotapp;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Handle {
    private static final String TAG = Handle.class.getSimpleName();

    public Handle(){

    }

    public String httpCall(String requestUrl){
        String result = null;
        try{
            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            InputStream inputStream = new BufferedInputStream(connection.getInputStream());
            result = conVertResultToString(inputStream);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String conVertResultToString(InputStream inputStream) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String li;

        while (true){
            try{
                if(!((li = bufferedReader.readLine()) != null)){
                    stringBuilder.append("\n");

                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
            return stringBuilder.toString();
        }
    }

}
