package pkg.foms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;



public class MainActivity extends AppCompatActivity {

    private Handler handler;
    private Runnable callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler();
        callback = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), SignIn.class));
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        };
        handler.postDelayed(callback, 500);


    }

}