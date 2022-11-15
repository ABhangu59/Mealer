package codes.aydin.mealer;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper
{

    public static final String USER_TABLE_NAME = "users";
    public static final String ADDRESS_TABLE_NAME = "addresses";
    public static final String CREDIT_TABLE_NAME = "credit_cards";
    public static final String COMPLAINT_TABLE_NAME = "complaints";
    public static final String SUSPENSION_TABLE_NAME = "suspensions";
    public static final String MEAL_TABLE_NAME = "meals";

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Mealer.db";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db)
    {

        db.execSQL("CREATE TABLE " + USER_TABLE_NAME + " (" + "type VARCHAR NOT NULL," +
                "email VARCHAR NOT NULL," +
                "first_name VARCHAR NOT NULL," +
                "last_name VARCHAR NOT NULL," +
                "password VARCHAR NOT NULL," +
                "bio VARCHAR," +
                "void_cheque VARCHAR," +
                "PRIMARY KEY (email))");


        String[] adminInfo = {"admin", "Admin", "User", "mealeradmin", "admin123!", "", ""};
        String[] cook1Info = {"cook", "Ali", "Bhangu", "abhang@mealer.app", "cricketlover123", "I love to cook all the Pakistani food", ""};
        String[] client1Info = {"client", "Aydin", "Bingos", "aydin@mealer.app", "kebablover123", "", ""};

        String[][] users = { adminInfo, cook1Info, client1Info };

        for (String[] user: users) {
            ContentValues values = new ContentValues();
            values.put("type", user[0]);
            values.put("first_name", user[1]);
            values.put("last_name", user[2]);
            values.put("email", user[3]);
            values.put("password", user[4]);
            values.put("bio", user[5]);
            values.put("void_cheque", user[6]);
            db.insert(USER_TABLE_NAME, null, values);
        }

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

        db.execSQL("CREATE TABLE " + SUSPENSION_TABLE_NAME + " (" + "email VARCHAR NOT NULL," +
                "reason VARCHAR NOT NULL," +
                "unsuspension_date DATE NOT NULL," + // Either date set by admin, or Dec 31, 9999 if indefinite.
                "PRIMARY KEY (email))");

        db.execSQL("CREATE TABLE " + COMPLAINT_TABLE_NAME + " (" + "cook_email VARCHAR NOT NULL," +
                "client_email VARCHAR NOT NULL," +
                "description VARCHAR NOT NULL," +
                "is_resolved NUMBER(1) NOT NULL DEFAULT 0," +
                "complaint_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "CONSTRAINT ck_testbool_isres CHECK (is_resolved IN (1,0)))");

        String[] complaint1 = {"abhang@mealer.app" , "aydin@mealer.app", "Food arrived cold and soggy."};
        String[] complaint2 = {"tomer@mealer.app" , "aydin@mealer.app", "Mixed dairy with meat products."};
        String[] complaint3 = {"mustafa@mealer.app" , "aydin@mealer.app", "Used produce of subpar quality."};
        String[] complaint4 = {"asif@mealer.app" , "aydin@mealer.app", "Did not make my cake 'soccer' themed."};

        String[][] complaints = { complaint1, complaint2, complaint3, complaint4 };

        for (String[] complaint: complaints) {
            ContentValues values = new ContentValues();
            values.put("cook_email", complaint[0]);
            values.put("client_email", complaint[1]);
            values.put("description", complaint[2]);
            db.insert(COMPLAINT_TABLE_NAME, null, values);
        }

        db.execSQL("CREATE TABLE " + MEAL_TABLE_NAME + " (" + "cook_email VARCHAR NOT NULL," +
                "meal_name VARCHAR NOT NULL," +
                "meal_type VARCHAR NOT NULL," +
                "cuisine_type VARCHAR NOT NULL," +
                "description VARCHAR NOT NULL," +
                "allergens VARCHAR NOT NULL," +
                "ingredients VARCHAR NOT NULL," +
                "price DECIMAL(10, 2) NOT NULL," +
                "currently_offered NUMBER(1) NOT NULL,"+
                "meal_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "CONSTRAINT ck_testbool_isres CHECK (is_resolved IN (1,0)))");


        String[] meal1 = {"abhang@mealer.app", "Butter Chicken", "Dinner", "Indian", "Only the freshest ingredients in this traditional dish", "Dairy", "Butter, Tomatoes, Chicken, Cream", "12.99", "1"};
        String[] meal2 = {"abhang@mealer.app", "Dhal Chawl", "Dinner", "Indian", "Homemade Bliss", "Gluten", "Oil, Lentils, Rice", "11.99", "1"};
        String[] meal3 = {"tomer@mealer.app", "Malabi", "Dessert", "Jewish", "An authentic dessert popular in Israel", "Dairy", "Milk, Cream, Rose Flavouring, Sugar", "9.99", "1"};
        String[] meal4 = {"asif@mealer.app", "Mac & Cheese", "Lunch", "American", "Kraft Dinner, Nothing Special", "Dairy", "Butter, Cheese, Milk, Pasta", "7.99", "1"};

        String[][] meals = {meal1, meal2, meal3, meal4};

        for (String[] meal: meals) {
            ContentValues values = new ContentValues();
            values.put("cook_email", meal[0]);
            values.put("meal_name", meal[1]);
            values.put("meal_type", meal[2]);
            values.put("cuisine_type", meal[3]);
            values.put("description", meal[4]);
            values.put("allergens", meal[5]);
            values.put("ingredients", meal[6]);
            values.put("price", meal[7]);
            db.insert(MEAL_TABLE_NAME, null, values);
        }

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ADDRESS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CREDIT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + COMPLAINT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SUSPENSION_TABLE_NAME);

        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
