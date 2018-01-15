package hinojalrobledo.myapplication.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import hinojalrobledo.myapplication.R;
import hinojalrobledo.myapplication.databases.StructureBBDD;
import hinojalrobledo.myapplication.databases.StructureBBDDHelper;

public class RegisterUser extends AppCompatActivity {

    private Button buttonRegister;

    private EditText textEmail, textName, textSurname, textPhoneNumber, textPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        getSupportActionBar().hide();

        buttonRegister = (Button) findViewById(R.id.btRegister);
        textEmail = (EditText) findViewById(R.id.etEmailUser);
        textName = (EditText) findViewById(R.id.etNameUser);
        textSurname = (EditText) findViewById(R.id.etSurnameUser);
        textPassword = (EditText) findViewById(R.id.etPasswordUser);
        textPhoneNumber = (EditText) findViewById(R.id.etPhoneNumberUser);

        final StructureBBDDHelper helper = new StructureBBDDHelper (this);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    Toast.makeText(getApplicationContext(), "No se ha podido registrar. ¡Intentelo de nuevo con otro teléfono! ", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
