package pkg.foms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import pkg.foms.Model.Model_User;

public class SignIn extends AppCompatActivity {

    //--> Call Class
    Model_User modelUser = new Model_User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        TextView email = (TextView) findViewById(R.id.email);
        TextView pswd  = (TextView) findViewById(R.id.pswd);
        Button login   = (Button) findViewById(R.id.signIn);
        Button register= (Button) findViewById(R.id.register);
        TextView msg   = (TextView) findViewById(R.id.msg);

        //--> Login Button
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(email.getText().length() == 0 || pswd.getText().length() == 0){
                    msg.setText("Please complete all fields ..!!");
               }
               else {
                   modelUser.setEmail(String.valueOf(email.getText()));
                   modelUser.setPassword(String.valueOf(pswd.getText()));

                   String result = "false";

                   try {
                       result = modelUser.verifyCredentials();
                       JSONObject  userInfo = new JSONObject(result);

                       modelUser.setId(String.valueOf(userInfo.get("ID")));
                       modelUser.setName(String.valueOf(userInfo.get("Name")));
                       modelUser.setEmail(String.valueOf(userInfo.get("Email")));
                       String  type = String.valueOf(userInfo.get("Type"));
                       if (type.equals("Customer")) { openHome(); }
                       else{ msg.setText("Password Incorrect or User not Exist ..!!"); }

                   } catch (IOException | JSONException e) {
                       e.printStackTrace();
                       msg.setText("UnExpected Error ..!!");
                   }
               }
            }
        });

        //--> Register Button
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegister();
            }
        });
    }

    public void openHome(){
        Intent intent = new Intent(getApplicationContext(),Home.class);
        startActivity(intent);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }

    public void openRegister(){
        Intent intent = new Intent(getApplicationContext(),Register.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }
}