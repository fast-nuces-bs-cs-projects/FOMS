package pkg.foms.Api;


import android.os.StrictMode;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpEntity;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpResponse;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.NameValuePair;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.ResponseHandler;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.entity.UrlEncodedFormEntity;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpGet;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpPost;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.BasicResponseHandler;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.DefaultHttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.HttpClients;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class Call_Api {

    //--> Variables
    private String apiUrl = "http://192.168.10.2:8080";
    DefaultHttpClient httpClient = new DefaultHttpClient();

    //--> Function

    //-> Get Request
    public String ApiGetRequest(String url) throws IOException {

        HttpGet getRequest = new HttpGet(apiUrl+url);

        //Send the request; It will immediately return the response in HttpResponse object
        HttpResponse response = httpClient.execute(getRequest);

        //Now pull back the response object
        HttpEntity httpEntity = response.getEntity();
        String apiOutput = EntityUtils.toString(httpEntity);

        return apiOutput;
    }

    //-> Post Request
    public String ApiPostRequest(String url, List<NameValuePair> params) throws IOException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(apiUrl +url);

        httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        ResponseHandler<String> responseHandler=new BasicResponseHandler();
        String responseBody = httpclient.execute(httppost, responseHandler);

        return responseBody;

    }
}