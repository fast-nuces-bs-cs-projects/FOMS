package pkg.foms.Api;


import java.io.IOException;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;


public class Api_User {

    //--> Variables
    private String url = "http://127.0.0.1:8080";

    //--> Call Class
    Call_Api callApi = new Call_Api();
    //--> Functions

    //-> Api Add User
    public String ApiAddUser(String Name, String Email, String Pswd, Path UserImgPath) throws IOException, URISyntaxException, JSONException {
        String  apiurl = url+"/user/";

        // Now creating byte array of same length as file
        byte[] bytes = Files.readAllBytes(Paths.get(String.valueOf(UserImgPath)));
        String s = Base64.getEncoder().encodeToString(bytes);

        // Request parameters and other properties.
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("Name" , Name));
        params.add(new BasicNameValuePair("Email", Email));
        params.add(new BasicNameValuePair("Pswd" , Pswd));
        params.add(new BasicNameValuePair("Type" ,"Operator"));
        params.add(new BasicNameValuePair("File" ,s));

        return callApi.ApiPostRequest(apiurl,params);
    }

    //-> Api Login User
    public String ApiLoginUser(String Email,String Pswd) throws IOException {
        String  apiurl = url+"/user/login";

        // Request parameters and other properties.
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("Email", Email));
        params.add(new BasicNameValuePair("Pswd", Pswd));


        return callApi.ApiPostRequest(apiurl,params);
    }

}
