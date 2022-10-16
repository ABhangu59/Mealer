package codes.aydin.mealer;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {

    public static final String USER_TABLE_NAME = "Users";
    public static final String ADDRESS_TABLE_NAME = "Addresses";
    public static final String CREDIT_TABLE_NAME = "Credit";

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Mealer.db";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + USER_TABLE_NAME + " (" + "type VARCHAR NOT NULL," +
                "email VARCHAR NOT NULL," +
                "first_name VARCHAR NOT NULL," +
                "last_name VARCHAR NOT NULL," +
                "password VARCHAR NOT NULL," +
                "bio VARCHAR," +
                "void_cheque VARCHAR," +
                "PRIMARY KEY (email))");

        ContentValues values = new ContentValues();
        values.put("type", "admin");
        values.put("first_name", "Admin");
        values.put("email", "mealeradmin");
        values.put("last_name", "User");
        values.put("password", "admin123!");
        values.put("bio", "");
        values.put("void_cheque", "");
        db.insert("Users", null, values);

        db.execSQL("CREATE TABLE " + ADDRESS_TABLE_NAME + " (" + "email VARCHAR NOT NULL," +
                "address_1 VARCHAR NOT NULL," +
                "address_2 VARCHAR NOT NULL," +
                "city VARCHAR NOT NULL," +
                "province VARCHAR NOT NULL," +
                "postal_code VARCHAR NOT NULL," +
                "PRIMARY KEY (email))");

        db.execSQL("CREATE TABLE " + CREDIT_TABLE_NAME + " (" + "email VARCHAR NOT NULL," +
                "full_name VARCHAR NOT NULL," +
                "number VARCHAR NOT NULL," +
                "expiration_month VARCHAR NOT NULL," +
                "expiration_year VARCHAR NOT NULL," +
                "cvv VARCHAR NOT NULL," +
                "PRIMARY KEY (email))");


    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ADDRESS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CREDIT_TABLE_NAME);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
