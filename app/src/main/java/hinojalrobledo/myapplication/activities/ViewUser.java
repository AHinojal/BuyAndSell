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
        //Cambia titulo a la barra de acciones
        getSupportActionBar().setTitle("Inicio");

        buttonBuy = (Button) findViewById(R.id.buttonBuy);
        buttonSell = (Button) findViewById(R.id.buttonSell);
        emailUserLog = (TextView) findViewById(R.id.tvEmailUser);
        msgWelcome = (TextView) findViewById(R.id.msg_Welcome);

        //Crea un nuevo estilo de tipografia a partir de la fuente y lo modifica
        typeFace1 = Typeface.createFromAsset(getAssets(),"fonts/GeosansLight.ttf");
        typeFace2 = Typeface.createFromAsset(getAssets(),"fonts/Headache.ttf");
        msgWelcome.setTypeface(typeFace1);
        emailUserLog.setTypeface(typeFace2);
        buttonBuy.setTypeface(typeFace2);
        buttonSell.setTypeface(typeFace2);

        //Esto, con lo que nos han pasado en el intent, podemos rellenar el campo del email
        Bundle extra = getIntent().getExtras();
        String emailUser = null;
        if (extra != null){
            emailUser = extra.getString("email");
            emailUserLog.setText(emailUser);
        }

        //Nos envia a la activity de la lista de productos a vender
        buttonBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBuyProduct = new Intent(ViewUser.this, ListProducts.class);
                ViewUser.this.startActivity(intentBuyProduct);
            }
        });

        //Nos envia a la activity para crear un anuncio
        buttonSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBuyProduct = new Intent(ViewUser.this, SellProduct.class);
                //Enviamos en el intent el email del usuario
                intentBuyProduct.putExtra("email",emailUserLog.getText().toString());
                ViewUser.this.startActivity(intentBuyProduct);
            }
        });
    }

    //Para que cuando le demos a atras en el movil, "muera" la actividad
    @Override
    public void onBackPressed() {
        finish();
    }
}
