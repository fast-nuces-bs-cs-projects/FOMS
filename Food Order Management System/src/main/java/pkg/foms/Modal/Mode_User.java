package pkg.foms.Modal;

import org.json.JSONException;
import pkg.foms.Api.Api_User;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;

public class Mode_User {

    //-> Class Calls
    Api_User api_user = new Api_User();

    //-> Variables
    private String Name;
    private String Email;
    private String Pswd;
    private Path UserImagePath;

    //-> Setter Functions
    public void setName(String name) {Name = name;}
    public void setEmail(String email) {Email = email;}
    public void setPswd(String pswd) {Pswd = pswd;}
    public void setUserImagePath(Path userImagePath) {UserImagePath = userImagePath;}

    //-> Getter Functions
    public String getName() {return Name;}
    public String getEmail() {return Email;}
    public String getPswd() {return Pswd;}
    public Path getUserImagePath() {return UserImagePath;}


    //-> Register User
    public String add_user() throws JSONException, IOException, URISyntaxException {
        return api_user.ApiAddUser(getName(),getEmail(),getPswd(),getUserImagePath());
    }






}
