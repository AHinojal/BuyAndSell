package hinojalrobledo.myapplication.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import java.util.ArrayList;
import hinojalrobledo.myapplication.R;
import hinojalrobledo.myapplication.databases.StructureBBDD;
import hinojalrobledo.myapplication.databases.StructureBBDDHelper;
import hinojalrobledo.myapplication.entities.Product;

public class ListProducts extends AppCompatActivity {

    ListView listView_products;
    StructureBBDDHelper helper;
    ArrayList<String> informationList;
    ArrayList<Product> productList;

    String nameProduct, descriptionProduct, priceProduct, emailSellerProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_products);
        //Cambia titulo a la barra de acciones
        getSupportActionBar().setTitle("Lista de productos a la venta");

        listView_products = (ListView) findViewById(R.id.lv_listProducts);

        helper = new StructureBBDDHelper(this);

        //Consulta la lista de pedidos
        consultProductList();

        //Devuelve a una vista cada objeto de un array de objetos que le pasemos
        //Es decir, nos devuelve toda la informacion que queremos que muestre por pantalla
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,informationList);
        listView_products.setAdapter(adapter);

        listView_products.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Le dice a la BBDD que esta en modo lectura
                SQLiteDatabase db = helper.getReadableDatabase();

                // Aqui ponemos los datos de la columna que queremos que nos den
                String[] projection = {
                        StructureBBDD.COLUMN2_PRODUCTS,
                        StructureBBDD.COLUMN3_PRODUCTS,
                        StructureBBDD.COLUMN4_PRODUCTS,
                        StructureBBDD.COLUMN5_PRODUCTS,
                        StructureBBDD.COLUMN6_PRODUCTS
                };

                // Como filtramos la busqueda --> por la id
                String selection = StructureBBDD.COLUMN1_PRODUCTS + " = ?";
                //Posicion del elemento que pinchamos en la ListView
                String pos = Integer.toString(position+1);
                String[] selectionArgs = {pos};

                //Busqueda
                Cursor cursor = db.query(
                StructureBBDD.TABLE_PRODUCTS,                     // The table to query
                        projection,                               // The columns to return
                        selection,                                // The columns for the WHERE clause
                        selectionArgs,                            // The values for the WHERE clause
                        null,                              // don't group the rows
                        null,                                // don't filter by row groups
                        null                                // The sort order
                );

                //Ponemos el cursor en primer elemento que nos den
                cursor.moveToFirst();

                //Devolvemos en una variable los elementos que obtenemos en la busqueda
                byte[] imageProduct = cursor.getBlob(0);
                nameProduct = cursor.getString(1);
                descriptionProduct = cursor.getString(2);
                priceProduct = cursor.getString(3);
                emailSellerProduct = cursor.getString(4);

                //Para que un activity llame a otro activity -> Intent
                Intent intentViewProduct = new Intent(ListProducts.this, ShowProduct.class);
                //Pasamos los elementos obtenidos a la siguiente vista
                intentViewProduct.putExtra("imageProduct", imageProduct);
                intentViewProduct.putExtra("nameProduct",nameProduct);
                intentViewProduct.putExtra("descriptionProduct",descriptionProduct);
                intentViewProduct.putExtra("priceProduct",priceProduct);
                intentViewProduct.putExtra("emailSellerProduct",emailSellerProduct);

                ListProducts.this.startActivity(intentViewProduct);
            }
        });
    }

    private void consultProductList(){
        SQLiteDatabase db = helper.getReadableDatabase();

        Product p = null;
        productList = new ArrayList<Product>();
        //Hacemos la busqueda --> Select * from table_products
        Cursor cursor = db.rawQuery("SELECT * FROM "+ StructureBBDD.TABLE_PRODUCTS,null);

        //Recorremos toda la tabla
        while (cursor.moveToNext()){
            p = new Product();
            p.setId(cursor.getInt(0));
            p.setPhoto(cursor.getBlob(1));
            p.setName(cursor.getString(2));
            p.setDescription(cursor.getString(3));
            p.setPrice(cursor.getString(4));
            p.setEmailSeller(cursor.getString(5));
            productList.add(p);
        }
        giveList();
    }

    //Recorremos la lista de productos y rellenamos una lista con la info a mostrar
    private void giveList() {
        informationList = new ArrayList<String>();
        for(int i=0;i<productList.size();i++){
            informationList.add(productList.get(i).getName() + " --- Precio: " + productList.get(i).getPrice() + "€");
        }
    }
}
