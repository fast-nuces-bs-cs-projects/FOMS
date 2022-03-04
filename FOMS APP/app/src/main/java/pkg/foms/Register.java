package pkg.foms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

import pkg.foms.Model.Model_Register;

public class Register extends AppCompatActivity {

    //--> Variables
    public static final int PICK_IMAGE = 1;


    //--> Call Class
    Model_Register modelRegister = new Model_Register();

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


                modelRegister.setName(String.valueOf(Name.getText()));
                modelRegister.setEmail(String.valueOf(Email.getText()));
                modelRegister.setPassword(String.valueOf(Pswd.getText()));
                modelRegister.setAddress(String.valueOf(Add.getText()));
                modelRegister.setPhNo(String.valueOf(phonenum.getText()));

                String result = null;
                try {
                    result = modelRegister.register_customer();
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