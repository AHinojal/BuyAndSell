package hinojalrobledo.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewUser extends AppCompatActivity {

    TextView emailUserLog;
    Button buttonBuy, buttonSell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);

        buttonBuy = (Button) findViewById(R.id.buttonBuy);
        buttonSell = (Button) findViewById(R.id.buttonSell);


        emailUserLog = (TextView) findViewById(R.id.tvEmailUser);

        Bundle extra = getIntent().getExtras();
        String emailUser = null;
        if (extra != null){
            emailUser = extra.getString("email");
            emailUserLog.setText(emailUser);
        }

        buttonBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBuyProduct = new Intent(ViewUser.this, BuyProduct.class);
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
}
