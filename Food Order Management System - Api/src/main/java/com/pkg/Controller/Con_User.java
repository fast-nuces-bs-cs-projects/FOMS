package com.pkg.Controller;



import com.pkg.Model.Mod_User;

import org.json.JSONException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Map;



@RestController
@RequestMapping(path = "/user")
public class Con_User {

    //--> Variables
    private String UPLOADED_FOLDER = System.getProperty("user.dir") + "/src/main/resources/public/UserImg/";
    Mod_User model_user = new Mod_User();

    //--> Functions

    //-> Get All Users
    @GetMapping(path = "/", produces = "application/json")
    public String getAllUser() throws JSONException {
        return model_user.getAllUser();
    }

    //-> Add User
    @PostMapping(path = "/", produces = "application/json")
    public String addUser(@RequestParam("Name") String Name, @RequestParam("Email") String Email, @RequestParam("Pswd") String Password,
                          @RequestParam("file") String file) throws IOException {
        //-> Decode File
        byte[] bytes = Base64.getDecoder().decode(file);
        Path path = Paths.get(UPLOADED_FOLDER + Name+".png");
        Files.write(path, bytes);
        // return string message
        return model_user.addUser(Name, Email, Password, Name+".png");
    }

    //-> User Login
    @PostMapping(path = "/login", produces = "application/json")
    public Map<String, String> getSingleUser(@RequestParam("Email") String email, @RequestParam("Pswd") String pswd) {
        return  model_user.login(email,pswd);
    }


}
