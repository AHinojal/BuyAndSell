package hinojalrobledo.myapplication.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import hinojalrobledo.myapplication.R;

public class ViewUser extends AppCompatActivity {

    TextView emailUserLog, msgWelcome;
    Button buttonBuy, buttonSell;
    Typeface typeFace1,typeFace2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);
        //getSupportActionBar().hide();
        getSupportActionBar().setTitle("Inicio");

        buttonBuy = (Button) findViewById(R.id.buttonBuy);
        buttonSell = (Button) findViewById(R.id.buttonSell);
        emailUserLog = (TextView) findViewById(R.id.tvEmailUser);
        msgWelcome = (TextView) findViewById(R.id.msg_Welcome);

        typeFace1 = Typeface.createFromAsset(getAssets(),"fonts/GeosansLight.ttf");
        typeFace2 = Typeface.createFromAsset(getAssets(),"fonts/Headache.ttf");
        msgWelcome.setTypeface(typeFace1);
        emailUserLog.setTypeface(typeFace2);

        buttonBuy.setTypeface(typeFace2);
        buttonSell.setTypeface(typeFace2);

        Bundle extra = getIntent().getExtras();
        String emailUser = null;
        if (extra != null){
            emailUser = extra.getString("email");
            emailUserLog.setText(emailUser);
        }

        buttonBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBuyProduct = new Intent(ViewUser.this, ListProducts.class);
                ViewUser.this.startActivity(intentBuyProduct);
            }
        });

        buttonSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBuyProduct = new Intent(ViewUser.this, SellProduct.class);
                intentBuyProduct.putExtra("email",emailUserLog.getText().toString());
                ViewUser.this.startActivity(intentBuyProduct);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
