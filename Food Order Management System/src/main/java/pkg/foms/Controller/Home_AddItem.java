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

public class Home_AddItem extends Home{

    //--------------------------- Add Item ---------------------------
    @FXML
    void UploadItemImg(ActionEvent event){
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image", "*.png*"));
        File file = fc.showOpenDialog(null);
        //->Set Image Path
        txt_ImgPathItem.setText(String.valueOf(file.getAbsoluteFile()));
    }


    @FXML
    void add_Item(ActionEvent event) throws JSONException, IOException, URISyntaxException {
        String ItemName      = txt_ItemName.getText();
        String ItemDetail    = txt_ItemDetail.getText();
        Path ItemImagePath = Path.of(txt_ImgPathItem.getText());

        if(ItemName.isEmpty() || ItemDetail.isEmpty()) {displayMessageBox("Please Complete all fields ..!!","WARNING");}
        else if(!Files.exists(ItemImagePath)){displayMessageBox("File not exists ..!!","WARNING");}
        else{
            String msg = ApiItem.ApiAddItem(ItemName,ItemDetail,ItemImagePath);
            displayMessageBox(msg,"WARNING");
            if(msg.equals("Added Successfully ..!!")){
                txt_ItemName.setText("");
                txt_ItemDetail.setText("");
                txt_ImgPathItem.setText("");
            }
        }
    }
}
