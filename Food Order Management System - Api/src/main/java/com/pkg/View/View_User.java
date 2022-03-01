package com.pkg.View;

import com.pkg.Controller.Con_User;
import com.pkg.Model.Mod_User;
import org.json.JSONException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@RestController
@RequestMapping(path = "/user")
public class View_User {

    //-> Call Class
    Mod_User  model_user = new Mod_User();
    View_User view_user;
    Con_User controller_User = new Con_User(model_user,view_user);

    //--> Get Request

    //-> Get All Users
    @GetMapping(path = "/", produces = "application/json")
    public String UserList() throws JSONException {
        return controller_User.userList();
    }

    //--> Post Request

    //-> Add User i.e Operator or Customer
    @PostMapping(path = "/", produces = "application/json")
    public Boolean addUser(@RequestParam("Name") String Name, @RequestParam("Email") String Email, @RequestParam("Pswd") String Pswd,
                           @RequestParam("Type") String Type, @RequestParam("File") String File, @RequestParam(value = "PhoneNo",defaultValue = "NULL") String PhoneNo,
                           @RequestParam(value = "Address",defaultValue = "NULL") String Add) throws IOException {

        controller_User.setName(Name);
        controller_User.setEmail(Email);
        controller_User.setPswd(Pswd);
        controller_User.setType(Type);
        controller_User.setFile(File);
        controller_User.setPhno(PhoneNo);
        controller_User.setAdd(Add);

        if(Type.equals("Operator")) {return controller_User.addOperator();}
        else{ return controller_User.addCustomerDetails();}
    }

    //-> Verify Credentials of Operator & Customer
    @PostMapping(path = "/login", produces = "application/json")
    public Map<String, String> login(@RequestParam("Email") String Email, @RequestParam("Pswd") String Pswd) {
        controller_User.setEmail(Email);
        controller_User.setPswd(Pswd);
        return controller_User.verifyCredentials();
    }





}
