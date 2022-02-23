package com.pkg.View;

import com.pkg.Controller.Con_User;
import com.pkg.Model.Mod_User;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "/user")
public class Vie_User {

    //--> Variables
    private Mod_User model_user;
    private Vie_User view_user;
    Con_User controller_user = new Con_User(model_user,view_user);

    //--> Functions

    //-> Get All Users
    @GetMapping(path= "/", produces = "application/json")
    public String getAllUser(){
        return "User Details";
    }

    //-> Add User
    @PostMapping(path = "/", consumes = "application/json", produces = "application/json")
    public String addUser(@RequestBody String user){

        JSONObject userInfo = new JSONObject(user);

        controller_user.setName(String.valueOf(userInfo.get("Name")));
        controller_user.setEmail(String.valueOf(userInfo.get("Email")));
        controller_user.setPassword(String.valueOf(userInfo.get("Pswd")));


        //System.out.println(userInfo.get("Name"));
        //model_user.addUser("Rohan","@mail.com","1234","Img");

        return "ddf";
    }





}
