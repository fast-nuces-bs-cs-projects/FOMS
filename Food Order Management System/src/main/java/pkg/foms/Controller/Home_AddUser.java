package pkg.foms.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Home_AddUser extends  Home{

    //--------------------------- Add User ---------------------------
    @FXML
    void uploadUserImg(ActionEvent event){
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image", "*.png*"));
        File file = fc.showOpenDialog(null);
        //->Set Image Path
        txt_ImgPathUser.setText(String.valueOf(file.getAbsoluteFile()));
    }

    @FXML
    void add_User(ActionEvent event) throws IOException, URISyntaxException, JSONException {
        String Name  = txt_name.getText();
        String Email = txt_email.getText();
        String Pswd  = txt_pswd.getText();
        Path UserImgPath = Path.of(txt_ImgPathUser.getText());

        if(Name.isEmpty()) {displayMessageBox("Please input name ..!!","WARNING");}
        else if(Email.isEmpty()){displayMessageBox("Please input valid mail ..!!","WARNING");}
        else if(Pswd.isEmpty()){displayMessageBox("Please input password ..!!","WARNING");}
        else if(!Files.exists(UserImgPath)){displayMessageBox("File not exists ..!!","WARNING");}
        else{
            String msg = ApiUser.ApiAddUser(Name,Email,Pswd,UserImgPath);
            if(msg.equals("true")){
                displayMessageBox("Registered Successfully ..!!","INFORMATION");
                txt_name.setText("");
                txt_email.setText("");
                txt_pswd.setText("");
                txt_ImgPathUser.setText("");
            }
            else{
                displayMessageBox("Error ..!! User Already Exists","WARNING");
            }
        }
    }

}
