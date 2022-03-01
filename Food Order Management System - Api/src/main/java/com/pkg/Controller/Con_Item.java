package com.pkg.Controller;

import com.pkg.Model.Mod_Item;
import com.pkg.View.View_Item;
import org.json.JSONException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


public class Con_Item {

    //-> Call Class & Variables
    private Mod_Item modItem;
    private View_Item view;


    //-> Constructor
    public Con_Item(Mod_Item model, View_Item view){
        this.modItem = model;
        this.view    = view;
    }

    //-> Getter Functions
    public String getItemName() {return modItem.getItemName();}
    public String getItemDetail() {return modItem.getItemDetail();}
    public String getPrice() {return modItem.getPrice();}
    public String getFile() {return modItem.getFile();}

    //-> Setter Functions
    public void setItemName(String itemName) { modItem.setItemName(itemName);}
    public void setItemDetail(String itemDetail) {modItem.setItemDetail(itemDetail);}
    public void setPrice(String price) {modItem.setPrice(price);}
    public void setFile(String file) throws IOException {modItem.setFile(file);}

    //--> Get Requests

    //-> Get User List
    public String itemList() throws JSONException {
        return modItem.get_all_item();
    }


    //--> Post Requests

    //-> Add Operator
    public Boolean addItem(){
        return modItem.addItem();
    }

}
