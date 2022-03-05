package pkg.foms.Api;

import org.json.JSONException;

import java.io.IOException;

public class Api_Item {

    //--> Call Class
    Call_Api callApi = new Call_Api();

    public String GetItem() throws IOException, JSONException {
        String  url = "/item/";

        String result = callApi.ApiGetRequest(url);

        return result;

    }

}
