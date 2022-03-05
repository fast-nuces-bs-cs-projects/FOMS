package pkg.foms.Model;

import org.json.JSONException;

import java.io.IOException;

import pkg.foms.Api.Api_Item;

public class Model_Item {

    //--> Call Class
    Api_Item apiItem = new Api_Item();

    //--> Variables
    private String itemID;
    private String itemName;
    private String itemDetail;
    private String itemPrice;
    private String itemImage;

    //--> Setter
    public void setItemID(String itemID) { this.itemID = itemID; }
    public void setItemName(String itemName) { this.itemName = itemName; }
    public void setItemDetail(String itemDetail) { this.itemDetail = itemDetail; }
    public void setItemPrice(String itemPrice) { this.itemPrice = itemPrice; }
    public void setItemImage(String itemImage) { this.itemImage = itemImage; }

    //--> Getter
    public String getItemID() { return itemID; }
    public String getItemName() { return itemName; }
    public String getItemDetail() { return itemDetail; }
    public String getItemPrice() { return itemPrice; }
    public String getItemImage() { return itemImage; }


    public String getItem() throws IOException, JSONException {
        return apiItem.GetItem();
    }


}
