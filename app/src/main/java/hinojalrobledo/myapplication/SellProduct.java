package hinojalrobledo.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class SellProduct extends AppCompatActivity {

    ImageButton imageProduct;
    EditText nameProduct,descriptionProduct,priceProduct;
    EditText emailSeller;
    Button buttonSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_product);

        imageProduct = (ImageButton) findViewById(R.id.imageProduct);
        nameProduct = (EditText) findViewById(R.id.et_productName);
        descriptionProduct = (EditText) findViewById(R.id.et_descriptionProduct);
        priceProduct = (EditText) findViewById(R.id.et_priceProduct);
        buttonSend = (Button) findViewById(R.id.buttonSend);
        emailSeller = (EditText) findViewById(R.id.et_emailSeller);

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

        final StructureBBDDHelper helper = new StructureBBDDHelper (this);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gets the data repository in write mode
                SQLiteDatabase db = helper.getWritableDatabase();

                // Create a new map of values, where column names are the keys
                ContentValues values = new ContentValues();
                values.put(StructureBBDD.COLUMN2_PRODUCTS, nameProduct.getText().toString());
                values.put(StructureBBDD.COLUMN3_PRODUCTS, nameProduct.getText().toString());
                values.put(StructureBBDD.COLUMN4_PRODUCTS, descriptionProduct.getText().toString());
                values.put(StructureBBDD.COLUMN5_PRODUCTS, priceProduct.getText().toString());
                values.put(StructureBBDD.COLUMN6_PRODUCTS, emailSeller.getText().toString());

                long newRowId = db.insert(StructureBBDD.TABLE_PRODUCTS, null, values);
                Toast.makeText(getApplicationContext(), "No se ha podido guardar. ¡Intentelo de nuevo! " + newRowId, Toast.LENGTH_LONG).show();

                /*if(newRowId > -1){
                    Toast.makeText(getApplicationContext(), "asfsdgs", Toast.LENGTH_LONG).show();

                    //Para que un activity llame a otro activity -> Intent
                    Intent intentBackLogin = new Intent(SellProduct.this, LoginUser.class);
                    SellProduct.this.startActivity(intentBackLogin);
                }else{
                    Toast.makeText(getApplicationContext(), "No se ha podido guardar. ¡Intentelo de nuevo! ", Toast.LENGTH_LONG).show();
                }*/
            }
        });

        /*final StructureBBDDHelper helper = new StructureBBDDHelper (this);
        final Integer id = 1;

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gets the data repository in write mode
                SQLiteDatabase db = helper.getWritableDatabase();

                // Create a new map of values, where column names are the keys
                ContentValues values = new ContentValues();
                values.put(StructureBBDD.COLUMN1_PRODUCTS, Integer.toString(id));
                values.put(StructureBBDD.COLUMN2_PRODUCTS, nameProduct.getText().toString());
                values.put(StructureBBDD.COLUMN3_PRODUCTS, nameProduct.getText().toString());
                values.put(StructureBBDD.COLUMN4_PRODUCTS, descriptionProduct.getText().toString());
                values.put(StructureBBDD.COLUMN5_PRODUCTS, priceProduct.getText().toString());
                values.put(StructureBBDD.COLUMN6_PRODUCTS, emailSeller.getText().toString());

                long newRowId = db.insert(StructureBBDD.TABLE_PRODUCTS,null, values);

                if(newRowId > -1){
                    Toast.makeText(getApplicationContext(), "El anuncio se ha incluido perfectamente", Toast.LENGTH_LONG).show();
                    Intent intentBackLogin = new Intent(SellProduct.this, ViewUser.class);
                    intentBackLogin.putExtra("email",emailSeller.getText().toString());
                    SellProduct.this.startActivity(intentBackLogin);
                }else{
                    Toast.makeText(getApplicationContext(), "No se ha podido guardar. ¡Intentelo de nuevo! ", Toast.LENGTH_LONG).show();
                }
            }
        });*/
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
