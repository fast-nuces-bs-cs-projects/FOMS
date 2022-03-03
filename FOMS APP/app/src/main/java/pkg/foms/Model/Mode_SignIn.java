package pkg.foms.Model;

import pkg.foms.Api.Api_Login;

public class Mode_SignIn {

    //--> Variables
    private String email;
    private String pswd;

    //--> Call Class
    Api_Login apiLogin = new Api_Login();

    //--> Functions

    //-> Setter
    public void setEmail(String email) { this.email = email; }
    public void setPswd(String pswd) { this.pswd = pswd; }

    //-> Getter
    public String getEmail() { return email; }
    public String getPswd() { return pswd; }


    //->  Verify Credentials
    public Boolean verifyCredentials(){
        return  apiLogin.verifyCredentials(getEmail(),getPswd());
    }
}
