package hinojalrobledo.myapplication.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hinojalrobledo.myapplication.R;
import hinojalrobledo.myapplication.databases.StructureBBDD;
import hinojalrobledo.myapplication.databases.StructureBBDDHelper;
import hinojalrobledo.myapplication.entities.Product;

public class ListProducts extends AppCompatActivity {

    ListView listView_products;
    StructureBBDDHelper helper;
    ArrayList<String> informationList;
    ArrayList<Product> productList;
    private Typeface typeFace1,typeFace2;

    String nameProduct, descriptionProduct, priceProduct, emailSellerProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_products);
        //getSupportActionBar().hide();
        getSupportActionBar().setTitle("Lista de productos a la venta");

        listView_products = (ListView) findViewById(R.id.lv_listProducts);

        typeFace1 = Typeface.createFromAsset(getAssets(),"fonts/GeosansLight.ttf");
        typeFace2 = Typeface.createFromAsset(getAssets(),"fonts/Headache.ttf");

        helper = new StructureBBDDHelper(this);

        consultProductList();

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,informationList);
        listView_products.setAdapter(adapter);

        listView_products.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SQLiteDatabase db = helper.getReadableDatabase();

                // Define a projection that specifies which columns from the database
                // you will actually use after this query.
                String[] projection = {
                        StructureBBDD.COLUMN2_PRODUCTS,
                        StructureBBDD.COLUMN3_PRODUCTS,
                        StructureBBDD.COLUMN4_PRODUCTS,
                        StructureBBDD.COLUMN5_PRODUCTS,
                        StructureBBDD.COLUMN6_PRODUCTS
                };

                // Filter results WHERE "title" = 'My Title'
                String selection = StructureBBDD.COLUMN1_PRODUCTS + " = ?";
                String pos = Integer.toString(position+1);
                String[] selectionArgs = {pos};

                Cursor cursor = db.query(
                        StructureBBDD.TABLE_PRODUCTS,                     // The table to query
                        projection,                               // The columns to return
                        selection,                                // The columns for the WHERE clause
                        selectionArgs,                            // The values for the WHERE clause
                        null,                                     // don't group the rows
                        null,                                     // don't filter by row groups
                        null                                // The sort order
                );

                /*List itemIds = new ArrayList<>();
                while (cursor.moveToNext()) {
                    long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(StructureBBDD.COLUMN1_USERS));
                    itemIds.add(itemId);
                }
                cursor.close();*/

                cursor.moveToFirst();
                nameProduct = cursor.getString(1);
                descriptionProduct = cursor.getString(2);
                priceProduct = cursor.getString(3);
                emailSellerProduct = cursor.getString(4);

                //Para que un activity llame a otro activity -> Intent
                Intent intentViewProduct = new Intent(ListProducts.this, ShowProduct.class);

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
        //select * from table_products
        Cursor cursor=db.rawQuery("SELECT * FROM "+ StructureBBDD.TABLE_PRODUCTS,null);

        while (cursor.moveToNext()){
            p = new Product();
            p.setId(cursor.getInt(0));
            p.setPhoto(cursor.getString(1));
            p.setName(cursor.getString(2));
            p.setDescription(cursor.getString(3));
            p.setPrice(cursor.getString(4));
            p.setEmailSeller(cursor.getString(5));
            productList.add(p);
        }
        giveList();
    }

    private void giveList() {
        informationList = new ArrayList<String>();
        for(int i=0;i<productList.size();i++){
            informationList.add(productList.get(i).getName() + " --- Precio: " + productList.get(i).getPrice() + "€");
        }
    }


}
