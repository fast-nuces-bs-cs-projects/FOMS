package pkg.foms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import pkg.foms.Model.Model_User;

public class Register extends AppCompatActivity {

    //--> Variables
    public static final int PICK_IMAGE = 1;


    //--> Call Class
    Model_User modelUser = new Model_User();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView Name  = (TextView) findViewById(R.id.Name);
        TextView Email = (TextView) findViewById(R.id.reg_Email);
        TextView Pswd  = (TextView) findViewById(R.id.reg_Pswd);
        TextView Add   = (TextView) findViewById(R.id.reg_Add);
        TextView phonenum = (TextView) findViewById(R.id.reg_phonenumber);
        Button registercustomer = (Button) findViewById(R.id.registercustomer);
        TextView msg   = (TextView) findViewById(R.id.msg_2);

        //--> Register Button
        registercustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                modelUser.setName(String.valueOf(Name.getText()));
                modelUser.setEmail(String.valueOf(Email.getText()));
                modelUser.setPassword(String.valueOf(Pswd.getText()));
                modelUser.setAddress(String.valueOf(Add.getText()));
                modelUser.setPhNo(String.valueOf(phonenum.getText()));

                String result = null;
                try {
                    result = modelUser.register_customer();
                    if(result.equals("True")){msg.setText("Registered Successfully ..!!"); }
                    else{ msg.setText("User Already Exists ..!!"); }
                }
                catch (IOException e) {
                    System.out.println(e.getMessage());
                  msg.setText(e.getMessage());
                }




            }
        });
    }


}