package hinojalrobledo.myapplication.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StructureBBDDHelper extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "buyandsell.db";

    public StructureBBDDHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    //Crea las BBDD
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(StructureBBDD.CREATE_TABLE_USERS);
        db.execSQL(StructureBBDD.CREATE_TABLE_PRODUCTS);
    }
    //Actualiza las BBDD
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + StructureBBDD.TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + StructureBBDD.TABLE_PRODUCTS);

        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
