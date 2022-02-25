package com.pkg.Controller;



import com.pkg.Model.Mod_User;
import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONObject;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Map;
import java.util.Scanner;


@RestController
@RequestMapping(path = "/user")
public class Con_User {

    //--> Variables
    public static String UPLOADED_FOLDER = System.getProperty("user.dir") + "/src/main/resources/public/UserImg/";
    Mod_User model_user = new Mod_User();

    //--> Functions

    //-> Get All Users
    @GetMapping(path = "/", produces = "application/json")
    public String getAllUser() {
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
