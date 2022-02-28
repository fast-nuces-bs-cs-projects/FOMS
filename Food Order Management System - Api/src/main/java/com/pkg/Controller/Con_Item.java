package com.pkg.Controller;

import com.pkg.Model.Mod_Item;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@RestController
@RequestMapping(path = "/item")
public class Con_Item {

    //-> Variables
    private String UPLOADED_FOLDER = System.getProperty("user.dir") + "/src/main/resources/public/FoodImg/";
    Mod_Item mod_item = new Mod_Item();

    @PostMapping(path = "/", produces = "application/json")
    public String addUser(@RequestParam("Name") String Name, @RequestParam("Detail") String Detail,
                          @RequestParam("file") String file) throws IOException {
        //-> Decode File
        byte[] bytes = Base64.getDecoder().decode(file);
        Path path = Paths.get(UPLOADED_FOLDER + Name+".png");
        Files.write(path, bytes);
        // return string message
        return mod_item.addItem(Name, Detail, Name+".png");
    }
}
