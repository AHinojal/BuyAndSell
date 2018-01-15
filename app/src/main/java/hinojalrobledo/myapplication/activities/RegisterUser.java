package hinojalrobledo.myapplication.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import hinojalrobledo.myapplication.R;
import hinojalrobledo.myapplication.databases.StructureBBDD;
import hinojalrobledo.myapplication.databases.StructureBBDDHelper;

public class RegisterUser extends AppCompatActivity {

    private Button buttonRegister;
    private Typeface typeFace2,typeFace1;
    private TextView textRegister;

    private EditText textEmail, textName, textSurname, textPhoneNumber, textPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        //getSupportActionBar().hide();
        getSupportActionBar().setTitle("Registro de Usuario");

        buttonRegister = (Button) findViewById(R.id.btRegister);
        textEmail = (EditText) findViewById(R.id.etEmailUser);
        textName = (EditText) findViewById(R.id.etNameUser);
        textSurname = (EditText) findViewById(R.id.etSurnameUser);
        textPassword = (EditText) findViewById(R.id.etPasswordUser);
        textPhoneNumber = (EditText) findViewById(R.id.etPhoneNumberUser);
        textRegister = (TextView) findViewById(R.id.textRegister);

        typeFace1 = Typeface.createFromAsset(getAssets(),"fonts/GeosansLight.ttf");
        typeFace2 = Typeface.createFromAsset(getAssets(),"fonts/Headache.ttf");

        textRegister.setTypeface(typeFace2);
        buttonRegister.setTypeface(typeFace2);

        final StructureBBDDHelper helper = new StructureBBDDHelper (this);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!textEmail.getText().toString().isEmpty() && !textName.getText().toString().isEmpty() &&
                        !textSurname.getText().toString().isEmpty() && !textPassword.getText().toString().isEmpty() && !textPhoneNumber.getText().toString().isEmpty()){
                    // Gets the data repository in write mode
                    SQLiteDatabase db = helper.getWritableDatabase();

                    // Create a new map of values, where column names are the keys
                    ContentValues values = new ContentValues();
                    values.put(StructureBBDD.COLUMN1_USERS, textEmail.getText().toString());
                    values.put(StructureBBDD.COLUMN2_USERS, textPhoneNumber.getText().toString());
                    values.put(StructureBBDD.COLUMN3_USERS, textName.getText().toString());
                    values.put(StructureBBDD.COLUMN4_USERS, textSurname.getText().toString());
                    values.put(StructureBBDD.COLUMN5_USERS, textPassword.getText().toString());

                    long newRowId = db.insert(StructureBBDD.TABLE_USERS, null, values);

                    if(newRowId > -1){
                        Toast.makeText(getApplicationContext(), "Se ha registrado correctamente", Toast.LENGTH_LONG).show();

                        //Para que un activity llame a otro activity -> Intent
                        Intent intentBackLogin = new Intent(RegisterUser.this, LoginUser.class);
                        RegisterUser.this.startActivity(intentBackLogin);
                    }else{
                        Toast.makeText(getApplicationContext(), "No se ha podido registrar.\n¡Intentelo de nuevo!", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "No se ha podido registrar.\nFalta algún campo en el formulario. ", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
