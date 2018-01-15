package hinojalrobledo.myapplication.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Blob;

import hinojalrobledo.myapplication.R;

public class ShowProduct extends AppCompatActivity {

    ImageView imageProductBuy;
    Button buttonContact;
    TextView nameProductBuy,descriptionProductBuy,priceProductBuy,emailSellerProductBuy;
    private Typeface typeFace1,typeFace2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product);
        //getSupportActionBar().hide();

        buttonContact = (Button) findViewById (R.id.buttonContact);
        imageProductBuy = (ImageView) findViewById(R.id.iv_imageProduct);
        nameProductBuy =  (TextView) findViewById (R.id.tv_nameProduct);
        descriptionProductBuy = (TextView) findViewById(R.id.tv_descriptionProduct);
        priceProductBuy = (TextView) findViewById(R.id.tv_priceProduct);
        emailSellerProductBuy = (TextView) findViewById(R.id.tv_emailSellerProduct);

        typeFace1 = Typeface.createFromAsset(getAssets(),"fonts/GeosansLight.ttf");
        typeFace2 = Typeface.createFromAsset(getAssets(),"fonts/Headache.ttf");

        buttonContact.setTypeface(typeFace2);
        nameProductBuy.setTypeface(typeFace2);
        descriptionProductBuy.setTypeface(typeFace1);
        priceProductBuy.setTypeface(typeFace2);
        emailSellerProductBuy.setTypeface(typeFace1);

        Bundle extra = getIntent().getExtras();
        byte[] imageProduct = null;
        String nameProduct = null;
        String descriptionProduct = null;
        String priceProduct = null;
        String emailSellerProduct = null;

        if (extra != null){
            imageProduct = extra.getByteArray("imageProduct");
            setImageViewWithByteArray(imageProductBuy,imageProduct);
            nameProduct = extra.getString("nameProduct");
            nameProductBuy.setText(nameProduct);
            descriptionProduct = extra.getString("descriptionProduct");
            descriptionProductBuy.setText(descriptionProduct);
            priceProduct = extra.getString("priceProduct");
            priceProductBuy.setText(priceProduct + " €");
            emailSellerProduct = extra.getString("emailSellerProduct");
            emailSellerProductBuy.setText(emailSellerProduct);
        }
        getSupportActionBar().setTitle(nameProductBuy.getText());
        buttonContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });

    }

    public static void setImageViewWithByteArray(ImageView view, byte[] data) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        view.setImageBitmap(bitmap);
    }

    public void sendEmail() {
        String[] TO = {emailSellerProductBuy.getText().toString()}; //Aqui se pone el correo del vendedor
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        //Aqui modificarias el asunto del correo
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Posible comprador B&S: " + nameProductBuy.getText().toString() + " - " + priceProductBuy.getText().toString());
        //Aqui modificarias el cuerpo del correo
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Hola, vendedor de BuyAndSell! \n\n" +
                "Estaría interesado en la oferta que has puesto de: " +  nameProductBuy.getText().toString() + ".\n " +
                "Me gustaría que contactaras conmigo para indicar las condiciones de la compraventa. \n\n" +
                "Un saludo!");

        try {
            startActivity(Intent.createChooser(emailIntent, "Enviar mensaje al vendedor por:"));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ShowProduct.this,"No tienes clientes de email instalados.", Toast.LENGTH_SHORT).show();
        }
    }
}
