package pkg.foms.Modal;

import pkg.foms.Api.Api_Item;
import pkg.foms.Api.Api_User;

import java.io.IOException;
import java.nio.file.Path;

public class Mod_Item {

    //-> Class Calls
    Api_Item api_item = new Api_Item();

    //--> Variables
    private String ItemName;
    private String ItemPrice;
    private String ItemDetail;
    private Path ItemImagePath;

    //--> Functions

    //-> Setter Functions
    public void setItemName(String itemName) {ItemName = itemName;}
    public void setItemPrice(String itemPrice) {ItemPrice = itemPrice;}
    public void setItemDetail(String itemDetail) {ItemDetail = itemDetail;}
    public void setItemImagePath(Path itemImagePath) {ItemImagePath = itemImagePath;}

    //-> Getter Functions
    public String getItemName() {return ItemName;}
    public String getItemPrice() {return ItemPrice;}
    public String getItemDetail() {return ItemDetail;}
    public Path getItemImagePath() {return ItemImagePath;}

    //-> Add Item
    public String add_Item() throws IOException {
        return api_item.ApiAddItem(getItemName(),getItemPrice(),getItemDetail(),getItemImagePath());
    }

}
