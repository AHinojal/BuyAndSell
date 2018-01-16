package hinojalrobledo.myapplication.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hinojalrobledo.myapplication.R;
import hinojalrobledo.myapplication.databases.StructureBBDD;
import hinojalrobledo.myapplication.databases.StructureBBDDHelper;

public class LoginUser extends AppCompatActivity {
    Button logIn,register;

    EditText emailUser;
    EditText passwordUser;

    Typeface typeFace1,typeFace2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        emailUser = (EditText) findViewById (R.id.et_EmailUser);
        passwordUser = (EditText) findViewById (R.id.et_PasswordUser);
        logIn = (Button) findViewById (R.id.startSession);
        register = (Button) findViewById (R.id.buttonRegister);

        //Crea un nuevo estilo de tipografia a partir de la fuente
        typeFace1 = Typeface.createFromAsset(getAssets(),"fonts/GeosansLight.ttf");
        typeFace2 = Typeface.createFromAsset(getAssets(),"fonts/Headache.ttf");

        //Cambia la topografia de letra
        logIn.setTypeface(typeFace2);
        register.setTypeface(typeFace2);

        //Al pulsar al boton de registro, le envia a esa activity
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Para que un activity llame a otro activity -> Intent
                Intent intentRegister = new Intent(LoginUser.this, RegisterUser.class);
                LoginUser.this.startActivity(intentRegister);
            }
        });

        final StructureBBDDHelper helper = new StructureBBDDHelper (this);

        //Al pulsar el boton, comprobara si el correo y la pass son iguales a alguno de la BBDD
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Le dice a la BBDD que esta en modo lectura
                SQLiteDatabase db = helper.getReadableDatabase();

                // Aqui ponemos los datos de la columna que queremos que nos den
                String[] projection = {
                        StructureBBDD.COLUMN1_USERS,
                };

                // Como filtramos la busqueda --> por email y pass
                String selection = StructureBBDD.COLUMN1_USERS + " = ? and " + StructureBBDD.COLUMN5_USERS + " = ?" ;
                String[] selectionArgs = {emailUser.getText().toString(), passwordUser.getText().toString()};

                Cursor cursor = db.query(
                        StructureBBDD.TABLE_USERS,                     // The table to query
                        projection,                               // The columns to return
                        selection,                                // The columns for the WHERE clause
                        selectionArgs,                            // The values for the WHERE clause
                        null,                                     // don't group the rows
                        null,                                     // don't filter by row groups
                        null                                // The sort order
                );

                //Mete en un array el resultado
                List itemIds = new ArrayList<>();
                while(cursor.moveToNext()) {
                    long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(StructureBBDD.COLUMN1_USERS));
                    itemIds.add(itemId);
                }
                cursor.close();

                //Si coincide email y contraseña, es decir, esta dentro del array itemIds --> Entra en la app
                if (!itemIds.isEmpty()){
                    //Para que un activity llame a otro activity -> Intent
                    Intent intentStartSession = new Intent(LoginUser.this, ViewUser.class);
                    intentStartSession.putExtra("email",emailUser.getText().toString());
                    LoginUser.this.startActivity(intentStartSession);
                }else{
                    passwordUser.setText(null);
                    Toast.makeText(getApplicationContext(), "Los datos introducidos son erróneos.\n¡Intentelo de nuevo!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
