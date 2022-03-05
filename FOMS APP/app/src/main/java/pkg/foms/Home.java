package pkg.foms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import pkg.foms.Model.Model_Item;
import pkg.foms.Model.Model_User;

public class Home extends AppCompatActivity {

    //--> Call Class
    Model_Item modelItem = new Model_Item();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FloatingActionButton cart  = (FloatingActionButton) findViewById(R.id.cart);
        ListView listView = (ListView) findViewById(R.id.listView);





        try {
            String obj =  modelItem.getItem();
            JSONArray array = new JSONArray(obj);

            for(int i=0;i<array.length();i++){
                System.out.println(array.get(i));
            }




        } catch (IOException | JSONException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        //--> Cart Button
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openCart();
            }
        });


    }

    public void openCart(){
        Intent intent = new Intent(getApplicationContext(),Cart.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }
}