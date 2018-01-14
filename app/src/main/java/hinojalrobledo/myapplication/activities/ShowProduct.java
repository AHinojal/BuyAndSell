package hinojalrobledo.myapplication.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import hinojalrobledo.myapplication.R;

public class ShowProduct extends AppCompatActivity {

    Button buttonContact;
    TextView nameProductBuy,descriptionProductBuy,priceProductBuy,emailSellerProductBuy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product);

        buttonContact = (Button) findViewById (R.id.buttonContact);
        nameProductBuy =  (TextView) findViewById (R.id.tv_nameProduct);
        descriptionProductBuy = (TextView) findViewById(R.id.tv_descriptionProduct);
        priceProductBuy = (TextView) findViewById(R.id.tv_priceProduct);
        emailSellerProductBuy = (TextView) findViewById(R.id.tv_emailSellerProduct);


        Bundle extra = getIntent().getExtras();
        String nameProduct = null;
        String descriptionProduct = null;
        String priceProduct = null;
        String emailSellerProduct = null;

        if (extra != null){
            nameProduct = extra.getString("nameProduct");
            nameProductBuy.setText(nameProduct);
            descriptionProduct = extra.getString("descriptionProduct");
            descriptionProductBuy.setText(descriptionProduct);
            priceProduct = extra.getString("priceProduct");
            priceProductBuy.setText(priceProduct);
            emailSellerProduct = extra.getString("emailSellerProduct");
            emailSellerProductBuy.setText(emailSellerProduct);
        }

        buttonContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });

    };

    public void sendEmail() {
        String[] TO = {emailSellerProductBuy.getText().toString()}; //aquí pon tu correo
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        // Esto podrás modificarlo si quieres, el asunto y el cuerpo del mensaje
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "BuyAndSell: " + nameProductBuy.getText().toString() + " - " + priceProductBuy.getText().toString());
        emailIntent.putExtra(Intent.EXTRA_TEXT, descriptionProductBuy.getText().toString());

        try {
            startActivity(Intent.createChooser(emailIntent, "Enviar email..."));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ShowProduct.this,"No tienes clientes de email instalados.", Toast.LENGTH_SHORT).show();
        }
    }

}
