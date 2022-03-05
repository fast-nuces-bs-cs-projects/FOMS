package pkg.foms.Api;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.NameValuePair;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Api_Login {

    //--> Call Class
    Call_Api callApi = new Call_Api();

    public String verifyCredentials(String Email, String Pswd) throws IOException {

        String url = "/user/login";

        // Request parameters and other properties.
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("Email", Email));
        params.add(new BasicNameValuePair("Pswd", Pswd));


        return callApi.ApiPostRequest(url,params);
    }




}
