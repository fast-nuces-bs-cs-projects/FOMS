package com.pkg.View;

import com.pkg.Controller.Con_Item;
import com.pkg.Model.Mod_Item;
import org.json.JSONException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(path = "/item")
public class View_Item {

    //-> Call Class
    Mod_Item model_item = new Mod_Item();
    View_Item view_item;
    Con_Item controller_Item = new Con_Item(model_item,view_item);

    //--> Get Request

    //-> Get All Items
    @GetMapping(path = "/", produces = "application/json")
    public String ItemList() throws JSONException {
        return controller_Item.itemList();
    }

    //--> Post Request

    //-> Add User i.e Operator or Customer
    @PostMapping(path = "/", produces = "application/json")
    public Boolean addUser(@RequestParam("Name") String Name, @RequestParam("Detail") String Detail,
                           @RequestParam("Price") String Price, @RequestParam("File") String File) throws IOException {

        controller_Item.setItemName(Name);
        controller_Item.setItemDetail(Detail);
        controller_Item.setPrice(Price);
        controller_Item.setFile(File);

        return controller_Item.addItem();
    }

}
