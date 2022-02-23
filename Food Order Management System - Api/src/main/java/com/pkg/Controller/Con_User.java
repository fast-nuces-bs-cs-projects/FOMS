package com.pkg.Controller;


import com.pkg.Model.Mod_User;
import com.pkg.View.Vie_User;
import org.springframework.web.bind.annotation.*;

@RestController
public class Con_User {

    //--> Variables
    private String Name;
    private String Email;
    private String Password;
    private Mod_User model_user;
    private Vie_User view_user;

    //---> Functions

    //-> Constructor
    public Con_User(Mod_User model,Vie_User view){
        this.model_user = model;
        this.view_user  = view;
    }

    //-> Setter Functions
    public void setName(String name) {this.Name = name;}
    public void setEmail(String email) {this.Email = email;}
    public void setPassword(String password) {this.Password = password;}

    //-> Getter Functions
    public String getName() {return Name;}
    public String getEmail() {return Email;}
    public String getPassword() {return Password;}








}
