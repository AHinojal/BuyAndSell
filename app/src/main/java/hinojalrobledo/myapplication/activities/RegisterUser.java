package hinojalrobledo.myapplication.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
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
    private Typeface typeFace;
    private EditText textEmail, textName, textSurname, textPhoneNumber, textPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        //Cambia titulo a la barra de acciones
        getSupportActionBar().setTitle("Registro de Usuario");

        buttonRegister = (Button) findViewById(R.id.btRegister);
        textEmail = (EditText) findViewById(R.id.etEmailUser);
        textName = (EditText) findViewById(R.id.etNameUser);
        textSurname = (EditText) findViewById(R.id.etSurnameUser);
        textPassword = (EditText) findViewById(R.id.etPasswordUser);
        textPhoneNumber = (EditText) findViewById(R.id.etPhoneNumberUser);

        //Crea un nuevo estilo de tipografia a partir de la fuente y lo modifica
        typeFace = Typeface.createFromAsset(getAssets(),"fonts/Headache.ttf");
        buttonRegister.setTypeface(typeFace);

        final StructureBBDDHelper helper = new StructureBBDDHelper (this);
        //Lo que ocurre al pulsar el boton de aceptar
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Comprueba que todos los campos esten rellenos
                if(!textEmail.getText().toString().isEmpty() && !textName.getText().toString().isEmpty() &&
                        !textSurname.getText().toString().isEmpty() && !textPassword.getText().toString().isEmpty() && !textPhoneNumber.getText().toString().isEmpty()){
                    // Le dice a la BBDD que esta en modo escritura
                    SQLiteDatabase db = helper.getWritableDatabase();

                    // Crea un contenedor de valores, donde cada columna sera rellenada
                    ContentValues values = new ContentValues();
                    values.put(StructureBBDD.COLUMN1_USERS, textEmail.getText().toString());
                    values.put(StructureBBDD.COLUMN2_USERS, textPhoneNumber.getText().toString());
                    values.put(StructureBBDD.COLUMN3_USERS, textName.getText().toString());
                    values.put(StructureBBDD.COLUMN4_USERS, textSurname.getText().toString());
                    values.put(StructureBBDD.COLUMN5_USERS, textPassword.getText().toString());
                    //Inserta en la BBDD y nos devuelve el id
                    long newRowId = db.insert(StructureBBDD.TABLE_USERS, null, values);

                    //Si es > 1 nos habra registrado, si no, se habra repetido la primary key (numero de telefono)
                    if(newRowId > -1){
                        Toast.makeText(getApplicationContext(), "Se ha registrado correctamente", Toast.LENGTH_LONG).show();
                        finish();
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
