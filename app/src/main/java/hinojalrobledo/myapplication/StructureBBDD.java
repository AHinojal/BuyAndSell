package hinojalrobledo.myapplication;

public class StructureBBDD {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    public StructureBBDD() {}

        //Constantes Tabla Usuarios
        public static final String TABLE_USERS = "datosUsers";
        public static final String COLUMN1_USERS = "Email";
        public static final String COLUMN2_USERS = "Telefono";
        public static final String COLUMN3_USERS = "Nombre";
        public static final String COLUMN4_USERS = "Apellidos";
        public static final String COLUMN5_USERS = "Password";

        //Constantes Tabla Productos
        public static final String TABLE_PRODUCTS = "datosProductos";
        public static final String COLUMN1_PRODUCTS = "idProducto";
        public static final String COLUMN2_PRODUCTS = "UrlImagen";
        public static final String COLUMN3_PRODUCTS = "Producto";
        public static final String COLUMN4_PRODUCTS = "Descripcion";
        public static final String COLUMN5_PRODUCTS = "Precio";
        public static final String COLUMN6_PRODUCTS = "EmailVendedor";



    public static final String CREATE_TABLE_USERS =
            "CREATE TABLE " + StructureBBDD.TABLE_USERS + " (" +
                    StructureBBDD.COLUMN1_USERS + " TEXT," +
                    StructureBBDD.COLUMN2_USERS + " INTEGER PRIMARY KEY," +
                    StructureBBDD.COLUMN3_USERS + " TEXT," +
                    StructureBBDD.COLUMN4_USERS + " TEXT," +
                    StructureBBDD.COLUMN5_USERS + " TEXT)";

    public static final String CREATE_TABLE_PRODUCTS =
            "CREATE TABLE " + StructureBBDD.TABLE_PRODUCTS + " (" +
                    //StructureBBDD.COLUMN1_PRODUCTS + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    StructureBBDD.COLUMN2_PRODUCTS + " TEXT," +
                    StructureBBDD.COLUMN3_PRODUCTS + " TEXT," +
                    StructureBBDD.COLUMN4_PRODUCTS + " TEXT," +
                    StructureBBDD.COLUMN5_PRODUCTS + " INTEGER PRIMARY KEY," +
                    StructureBBDD.COLUMN6_PRODUCTS + " TEXT)";

}
