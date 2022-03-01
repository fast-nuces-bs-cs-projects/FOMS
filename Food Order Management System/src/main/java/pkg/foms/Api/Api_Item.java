package pkg.foms.Api;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class Api_Item {

    //--> Call Class
    Call_Api callApi = new Call_Api();

    //--> Functions

    //-> Api Add Item
    public String ApiAddItem(String Name,String Price,String Detail, Path ItemImgPath) throws IOException {

        String url = "/item/";

        // Now creating byte array of same length as file
        byte[] bytes = Files.readAllBytes(Paths.get(String.valueOf(ItemImgPath)));
        String s = Base64.getEncoder().encodeToString(bytes);

        // Request parameters and other properties.
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("Name", Name));
        params.add(new BasicNameValuePair("Detail", Detail));
        params.add(new BasicNameValuePair("file",s));

        return callApi.ApiPostRequest(url,params);
    }
}
