package pkg.foms.Api;


import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.NameValuePair;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Api_Register {



    //--> Call Class
    Call_Api callApi = new Call_Api();

    public String RegisterCustomer(String Name, String Email, String Pswd, String Address,String PhoneNum) throws IOException {
        String  url = "/user/";

        // Request parameters and other properties.
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("Name" , Name));
        params.add(new BasicNameValuePair("Email", Email));
        params.add(new BasicNameValuePair("Pswd" , Pswd));
        params.add(new BasicNameValuePair("Type" ,"Customer"));
        params.add(new BasicNameValuePair("File" ,"No Image"));
        params.add(new BasicNameValuePair("Address" ,Address));
        params.add(new BasicNameValuePair("PhoneNo" ,PhoneNum));



        return callApi.ApiPostRequest(url,params);
    }
}
