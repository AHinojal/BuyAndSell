package hinojalrobledo.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class LoginUser extends AppCompatActivity {
    TextView tvRegister;
    TextView tvStartSession;

    EditText emailUser;
    EditText passwordUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        emailUser = (EditText) findViewById (R.id.et_EmailUser);
        passwordUser = (EditText) findViewById (R.id.et_PasswordUser);

        tvRegister = (TextView) findViewById(R.id.buttonRegister);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Para que un activity llame a otro activity -> Intent
                Intent intentRegister = new Intent(LoginUser.this, RegisterUser.class);
                LoginUser.this.startActivity(intentRegister);
            }
        });

        final StructureBBDDHelper helper = new StructureBBDDHelper (this);
        tvStartSession = (TextView) findViewById(R.id.startSession);
        tvStartSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = helper.getReadableDatabase();

                // Define a projection that specifies which columns from the database
                // you will actually use after this query.
                String[] projection = {
                        StructureBBDD.COLUMN1_USERS,
                };

                // Filter results WHERE "title" = 'My Title'
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

                List itemIds = new ArrayList<>();
                while(cursor.moveToNext()) {
                    long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(StructureBBDD.COLUMN1_USERS));
                    itemIds.add(itemId);
                }
                cursor.close();

                //Si coincide email y contraseña --> Entra en la app
                if (!itemIds.isEmpty()){
                    //Para que un activity llame a otro activity -> Intent
                    Intent intentStartSession = new Intent(LoginUser.this, ViewUser.class);
                    intentStartSession.putExtra("email",emailUser.getText().toString());
                    LoginUser.this.startActivity(intentStartSession);
                }else{
                    passwordUser.setText(null);
                    Toast.makeText(getApplicationContext(), "Los datos introducidos son erróneos. ¡Intentelo de nuevo!", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
