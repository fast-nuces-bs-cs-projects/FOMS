package com.pkg.Controller;
import com.pkg.Model.Mod_User;
import com.pkg.View.View_User;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Con_User {

    //-> Call Class & Variables
    private Mod_User model_user;
    private View_User view;


    //-> Constructor
    public Con_User(Mod_User model, View_User view){
        this.model_user = model;
        this.view = view;
    }

    //-> Getter Functions
    public String getUPLOADED_FOLDER() {return UPLOADED_FOLDER;}
    public String getName() {return model_user.getName();}
    public String getEmail() {return model_user.getEmail();}
    public String getAdd() {return model_user.getAdd();}
    public String getPhno() {return model_user.getPhno();}
    public String getPswd() {return model_user.getPswd();}
    public String getType() {return model_user.getType();}
    public String getFile() {return model_user.getFile();}
    //-> Setter Functions
    public void setName(String name) {model_user.setName(name);}
    public void setEmail(String email) {model_user.setEmail(email);}
    public void setAdd(String add) {model_user.setAdd(add);}
    public void setPhno(String phno) {model_user.setPhno(phno);}
    public void setPswd(String pswd) {model_user.setPswd(pswd);}
    public void setType(String type) {model_user.setType(type);}
    public void setFile(String file) throws IOException {model_user.setFile(file);}

    //-> Add Operator
    public Boolean addOperator(){
        return model_user.add_user();
    }

    //-> Add Customer
    public Boolean addCustomerDetails(){
        boolean a =  model_user.add_user();
        boolean b =  model_user.add_customer_details();

        if(a == true && b== true){return true;}
        else{return false;}
    }
}
