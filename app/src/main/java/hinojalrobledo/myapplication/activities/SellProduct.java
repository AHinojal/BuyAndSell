package hinojalrobledo.myapplication.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import hinojalrobledo.myapplication.R;
import hinojalrobledo.myapplication.databases.StructureBBDD;
import hinojalrobledo.myapplication.databases.StructureBBDDHelper;

public class SellProduct extends AppCompatActivity {

    ImageView imageProduct;
    EditText nameProduct,descriptionProduct,priceProduct, emailSeller;
    Button buttonSend,buttonAddImage;
    private Typeface typeFace1,typeFace2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_product);
        //Cambia titulo a la barra de acciones
        getSupportActionBar().setTitle("Poner anuncio");

        imageProduct = (ImageView) findViewById(R.id.imageProduct);
        nameProduct = (EditText) findViewById(R.id.et_productName);
        descriptionProduct = (EditText) findViewById(R.id.et_descriptionProduct);
        priceProduct = (EditText) findViewById(R.id.et_priceProduct);
        buttonSend = (Button) findViewById(R.id.buttonSend);
        buttonAddImage = (Button) findViewById(R.id.btn_addImage);
        emailSeller = (EditText) findViewById(R.id.et_emailSeller);

        typeFace1 = Typeface.createFromAsset(getAssets(),"fonts/GeosansLight.ttf");
        typeFace2 = Typeface.createFromAsset(getAssets(),"fonts/Headache.ttf");
        //Cambia la topografia de letra
        buttonSend.setTypeface(typeFace2);

        final StructureBBDDHelper helper;

        //Esto, con lo que nos han pasado en el intent, podemos rellenar el campo del email
        Bundle extra = getIntent().getExtras();
        String emailUser = null;
        if (extra != null){
            emailUser = extra.getString("email");
            emailSeller.setText(emailUser);
        }

        helper = new StructureBBDDHelper(getApplicationContext());
        //Al pulsar el boton, nos dara la opcion de insertar foto a traves de la galeria
        buttonAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/");
                startActivityForResult(intent.createChooser(intent,"Selecciona una fotografía"),10);
            }
        });

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Comprueba que todos los campos esten rellenos, menos la foto
                if(!nameProduct.getText().toString().isEmpty() && !descriptionProduct.getText().toString().isEmpty() &&
                        !priceProduct.getText().toString().isEmpty() && !emailSeller.getText().toString().isEmpty()){
                    // Le dice a la BBDD que esta en modo escritura
                    SQLiteDatabase db = helper.getWritableDatabase();
                    long newRowId = -2; //Inicializacion basura

                    //Save Image
                    try{
                        byte[] entryImage = imageViewToByte(imageProduct);

                        // Crea un contenedor de valores, donde cada columna sera rellenadas
                        ContentValues values = new ContentValues();

                        values.put(StructureBBDD.COLUMN2_PRODUCTS, entryImage);
                        values.put(StructureBBDD.COLUMN3_PRODUCTS, nameProduct.getText().toString());
                        values.put(StructureBBDD.COLUMN4_PRODUCTS, descriptionProduct.getText().toString());
                        values.put(StructureBBDD.COLUMN5_PRODUCTS, priceProduct.getText().toString());
                        values.put(StructureBBDD.COLUMN6_PRODUCTS, emailSeller.getText().toString());

                        //Inserta en la BBDD y nos devuelve el id
                        newRowId = db.insert(StructureBBDD.TABLE_PRODUCTS, StructureBBDD.COLUMN1_PRODUCTS, values);
                    }catch (Exception e){

                    }
                    if(newRowId > -1){
                        Toast.makeText(getApplicationContext(), "El anuncio se ha incluido perfectamente", Toast.LENGTH_LONG).show();
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(), "No se ha podido guardar el anuncio.\n¡Intentelo de nuevo! ", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "No se ha podido crear el anuncio.\nFalta algún campo del formulario", Toast.LENGTH_LONG).show();
                }

            }

            //Convierte la imagen en bytes para poder meterlo en la tabla
            private byte[] imageViewToByte(ImageView image) {
                Bitmap bmp = ((BitmapDrawable) image.getDrawable()).getBitmap();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG,10,bos);
                byte[] byteArray = bos.toByteArray();
                return byteArray;
            }
        });
    }

    //Para que meta la imagen elegida a traves de la ruta en el imageView
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            //Coge la ruta
            Uri path = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(path);
                //Transforma la imagen en un bitmap para luego poder transformarlo en bytes y meter la imagen en la BBDD
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                //Cambia la imagen predefinida por la elegida por le usuario
                imageProduct.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
