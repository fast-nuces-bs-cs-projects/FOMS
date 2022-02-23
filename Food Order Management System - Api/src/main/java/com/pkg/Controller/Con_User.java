package com.pkg.Controller;


import com.pkg.Model.Mod_User;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user")
public class Con_User {

    //--> Variables
    private String Name;
    private String Email;
    private String Password;


    //---> Functions

    //-> Get All Users
    @GetMapping(path= "/", produces = "application/json")
    public String getAllUser(){
        return "User Details";
    }

    @PostMapping(path = "/", consumes = "application/json", produces = "application/json")
    public String addUser(@RequestBody String user){

        JSONObject userInfo = new JSONObject(user);

        //controller_user.setName(String.valueOf(userInfo.get("Name")));
        //controller_user.setEmail(String.valueOf(userInfo.get("Email")));
        //controller_user.setPassword(String.valueOf(userInfo.get("Pswd")));


        //System.out.println(userInfo.get("Name"));
        //model_user.addUser("Rohan","@mail.com","1234","Img");

        return "ddf";
    }






}
