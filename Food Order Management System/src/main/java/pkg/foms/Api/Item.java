package pkg.foms.Api;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class Item {

    public String ApiAddItem(String Name, String Detail, Path ItemImgPath) throws IOException, URISyntaxException, JSONException {

        String url = "http://127.0.0.1:8080/item/";

        // Now creating byte array of same length as file
        byte[] bytes = Files.readAllBytes(Paths.get(String.valueOf(ItemImgPath)));
        String s = Base64.getEncoder().encodeToString(bytes);

        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);
        // Request parameters and other properties.
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("Name", Name));
        params.add(new BasicNameValuePair("Detail", Detail));
        params.add(new BasicNameValuePair("file",s));
        httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        ResponseHandler<String> responseHandler=new BasicResponseHandler();
        String responseBody = httpclient.execute(httppost, responseHandler);
        return responseBody;
    }
}
