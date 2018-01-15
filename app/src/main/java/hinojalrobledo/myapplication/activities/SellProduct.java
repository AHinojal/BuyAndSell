package hinojalrobledo.myapplication.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import hinojalrobledo.myapplication.R;
import hinojalrobledo.myapplication.databases.StructureBBDD;
import hinojalrobledo.myapplication.databases.StructureBBDDHelper;

public class SellProduct extends AppCompatActivity {

    ImageButton imageProduct;
    EditText nameProduct,descriptionProduct,priceProduct, emailSeller;
    Button buttonSend;
    private Typeface typeFace1,typeFace2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_product);
        //getSupportActionBar().hide();
        getSupportActionBar().setTitle("Poner anuncio");

        imageProduct = (ImageButton) findViewById(R.id.imageProduct);
        nameProduct = (EditText) findViewById(R.id.et_productName);
        descriptionProduct = (EditText) findViewById(R.id.et_descriptionProduct);
        priceProduct = (EditText) findViewById(R.id.et_priceProduct);
        buttonSend = (Button) findViewById(R.id.buttonSend);
        emailSeller = (EditText) findViewById(R.id.et_emailSeller);

        typeFace1 = Typeface.createFromAsset(getAssets(),"fonts/GeosansLight.ttf");
        typeFace2 = Typeface.createFromAsset(getAssets(),"fonts/Headache.ttf");

        buttonSend.setTypeface(typeFace2);

        final StructureBBDDHelper helper;

        Bundle extra = getIntent().getExtras();
        String emailUser = null;
        if (extra != null){
            emailUser = extra.getString("email");
            emailSeller.setText(emailUser);
        }

        imageProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImage();
            }
        });

        helper = new StructureBBDDHelper(getApplicationContext());

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!nameProduct.getText().toString().isEmpty() && !descriptionProduct.getText().toString().isEmpty() &&
                        !priceProduct.getText().toString().isEmpty() && !emailSeller.getText().toString().isEmpty()){
                    // Gets the data repository in write mode
                    SQLiteDatabase db = helper.getWritableDatabase();

                    // Create a new map of values, where column names are the keys
                    ContentValues values = new ContentValues();
                    values.put(StructureBBDD.COLUMN2_PRODUCTS, nameProduct.getText().toString());
                    values.put(StructureBBDD.COLUMN3_PRODUCTS, nameProduct.getText().toString());
                    values.put(StructureBBDD.COLUMN4_PRODUCTS, descriptionProduct.getText().toString());
                    values.put(StructureBBDD.COLUMN5_PRODUCTS, priceProduct.getText().toString());
                    values.put(StructureBBDD.COLUMN6_PRODUCTS, emailSeller.getText().toString());

                    // Insert the new row, returning the primary key value of the new row
                    long newRowId = db.insert(StructureBBDD.TABLE_PRODUCTS, StructureBBDD.COLUMN1_PRODUCTS, values);

                    if(newRowId > -1){
                        Toast.makeText(getApplicationContext(), "El anuncio se ha incluido perfectamente", Toast.LENGTH_LONG).show();
                        //Intent intentBackLogin = new Intent(SellProduct.this, ViewUser.class);
                        //intentBackLogin.putExtra("email",emailSeller.getText().toString());
                        //SellProduct.this.startActivity(intentBackLogin);
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(), "No se ha podido guardar el anuncio.\n¡Intentelo de nuevo! ", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "No se ha podido crear el anuncio.\nFalta algún campo del formulario", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void loadImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Selecciona imagen"),10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            Uri path = data.getData();
            imageProduct.setImageURI(path);
        }
    }
}
