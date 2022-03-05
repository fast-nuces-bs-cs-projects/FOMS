package pkg.foms.Model;

import java.io.IOException;

import pkg.foms.Api.Api_Login;
import pkg.foms.Api.Api_Register;

public class Model_User {

    //--> Call Class
    Api_Login apiLogin = new Api_Login();
    Api_Register apiRegister = new Api_Register();

    //--> Variables
    private String Id;
    private String Name;
    private String Email;
    private String Password;
    private String Address;
    private String PhNo;

    //--> Setter Functions
    public void setId(String id) { Id = id; }
    public void setName(String name) { Name = name; }
    public void setEmail(String email) { Email = email; }
    public void setPassword(String password) { Password = password; }
    public void setAddress(String address) { Address = address; }
    public void setPhNo(String phNo) { PhNo = phNo; }

    //--> Getter Functions
    public String getId() {return  Id;}
    public String getName() { return Name; }
    public String getEmail() { return Email; }
    public String getPassword() { return Password; }
    public String getAddress() { return Address; }
    public String getPhNo() { return PhNo; }

    //->  Verify Credentials
    public String verifyCredentials() throws IOException {
        return  apiLogin.verifyCredentials(getEmail(),getPassword());
    }

    //-> Register User
    public String register_customer() throws IOException {
        return apiRegister.RegisterCustomer(getName(),getEmail(),getPassword(),getAddress(),getPhNo());
    }



}
