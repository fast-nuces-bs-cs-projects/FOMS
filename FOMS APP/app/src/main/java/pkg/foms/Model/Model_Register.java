package pkg.foms.Model;

import java.io.IOException;

import pkg.foms.Api.Api_Register;

public class Model_Register {

    //--> Call Class
    Api_Register apiRegister = new Api_Register();

    //--> Variables
    private String Name;
    private String Email;
    private String Password;
    private String Address;




    private String PhNo;

    //--> Setter Functions
    public void setName(String name) { Name = name; }
    public void setEmail(String email) { Email = email; }
    public void setPassword(String password) { Password = password; }
    public void setAddress(String address) { PhNo = address; }
    public void setPhNo(String phNo) { PhNo = phNo; }

    //--> Getter Functions
    public String getName() { return Name; }
    public String getEmail() { return Email; }
    public String getPassword() { return Password; }
    public String getAddress() { return Address; }
    public String getPhNo() { return PhNo; }


    //--> Functions
    //String Name,String Email,String Pswd,String Address, String UserImage
    public String register_customer() throws IOException {
        return apiRegister.RegisterCustomer(getName(),getEmail(),getPassword(),getAddress(),getPhNo());
    }






}
