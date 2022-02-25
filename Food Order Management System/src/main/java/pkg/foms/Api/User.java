package pkg.foms.Api;


import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;


public class User {



    public String ApiAddUser(String Name, String Email, String Pswd, Path UserImgPath) throws IOException, URISyntaxException, JSONException {

        String url = "http://127.0.0.1:8080/user/";

        // Now creating byte array of same length as file
        byte[] bytes = Files.readAllBytes(Paths.get(String.valueOf(UserImgPath)));
        String s = Base64.getEncoder().encodeToString(bytes);
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);
        // Request parameters and other properties.
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("Name", Name));
        params.add(new BasicNameValuePair("Email", Email));
        params.add(new BasicNameValuePair("Pswd", Pswd));
        params.add(new BasicNameValuePair("file",s));
        httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        ResponseHandler<String> responseHandler=new BasicResponseHandler();
        String responseBody = httpclient.execute(httppost, responseHandler);
        return responseBody;
    }

}
