package pkg.foms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import pkg.foms.Model.Mode_SignIn;

public class SignIn extends AppCompatActivity {

    //--> Call Class
    Mode_SignIn model_signIn = new Mode_SignIn();

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
              //  openHome();
               if(email.getText().length() == 0 || pswd.getText().length() == 0){
                    msg.setText("Please complete all fields ..!!");
               }
               else {
                   model_signIn.setEmail(String.valueOf(email.getText()));
                   model_signIn.setPswd(String.valueOf(pswd.getText()));

                   Boolean result = model_signIn.verifyCredentials();

                   if (result.equals(true)) {
                       openHome();
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