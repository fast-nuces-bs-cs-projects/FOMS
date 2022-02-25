package pkg.foms.Modal;

public class Mod_Login {



    //-> Variables
    private static String Id;
    private static String UserName ;
    private static String Email;
    private static String Image;

    //-> Setter
    public void setId(String id) {Id = id;}
    public void setUserName(String userName) {UserName = userName;}
    public void setEmail(String email) {Email = email;}
    public void setImage(String image) {
        Image = image. replaceAll(" ", "%20");
    }

    //-> Getter
    public String getId() {return Id;}
    public String getUserName() {return UserName;}
    public String getEmail() {return Email;}
    public String getImage() {return Image;}


}
