package codes.aydin.mealer;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DBHelper DBHelper = new DBHelper(getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button signIn = findViewById(R.id.btnSignUp);
        signIn.setOnClickListener(view -> {

            EditText emailText = findViewById(R.id.txtEmail);
            EditText passwordText = findViewById(R.id.txtPassword);
            String email = emailText.getText().toString().trim().toLowerCase(Locale.ROOT);
            String password = passwordText.getText().toString().trim();

            if (email.length() == 0 || password.length() == 0) {
                Toast.makeText(getApplicationContext(),
                        "You have entered an invalid email or password", Toast.LENGTH_LONG).show();
                return;
            }

            SQLiteDatabase db = DBHelper.getReadableDatabase();

            String[] projection = {"email", "password", "type", "first_name"};

            String selection = "email = ?";
            String[] selectionArgs = {email};

            Cursor cursor = db.query(codes.aydin.mealer.DBHelper.USER_TABLE_NAME, projection, selection, selectionArgs, null, null, null);

            List<String[]> rows = new ArrayList<>();
            while(cursor.moveToNext()) {
                String[] row = {cursor.getString(cursor.getColumnIndexOrThrow("password")), cursor.getString(cursor.getColumnIndexOrThrow("type")), cursor.getString(cursor.getColumnIndexOrThrow("first_name"))};
                rows.add(row);
            }
            cursor.close();

            if (rows.size() == 0) {
                Toast.makeText(getApplicationContext(),
                        "You have entered an invalid email or password", Toast.LENGTH_LONG).show();
                return;
            }

            String[] row = rows.get(0);

            if (row[0].equals(password)) {

                Intent launchActivity = null;
                if (row[1].equals("admin")) {
                    launchActivity = new Intent(getApplicationContext(), ComplaintPage.class);
                } else if (row[1].equals("cook")){
                    SQLiteDatabase db2 = DBHelper.getReadableDatabase();

                    String[] projection2 = {"unsuspension_date"};

                    String selection2 = "email = ?";
                    String[] selectionArgs2 = {email};

                    Cursor cursor2 = db2.query(codes.aydin.mealer.DBHelper.SUSPENSION_TABLE_NAME, projection2, selection2, selectionArgs2, null, null, null);

                    List<String[]> rows2 = new ArrayList<>();
                    while(cursor2.moveToNext()) {
                        String[] row2 = {cursor2.getString(cursor2.getColumnIndexOrThrow("unsuspension_date"))};
                        rows2.add(row2);
                    }
                    cursor2.close();

                    if (rows2.size() != 0) {
                        long millis = System.currentTimeMillis();
                        java.sql.Date date = new java.sql.Date(millis);
                        String today = date.toString();

                        try {
                            Date start = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA).parse(today);
                            Date end = new SimpleDateFormat("yyyy/MM/dd", Locale.CANADA).parse(rows2.get(0)[0]);

                            if (start.compareTo(end) > 0) {
                                launchActivity = new Intent(getApplicationContext(), CookPage.class).putExtra("userinfo", new String[]{email, row[2]});

                            } else {

                                String timeframe = (rows2.get(0)[0].equals("9999/12/31")) ? "indefinitely." : ("until " + rows2.get(0)[0]);

                                Toast.makeText(getApplicationContext(),
                                        "You are currently suspended from Mealer " + timeframe, Toast.LENGTH_LONG).show();
                                return;
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                    } launchActivity = new Intent(getApplicationContext(), CookPage.class).putExtra("userinfo", new String[]{email, row[2]});


                } else {
                    launchActivity = new Intent(getApplicationContext(), UserScreen.class).putExtra("userinfo", new String[]{email, row[2]});
                }
                startActivity(launchActivity);
            } else {
                Toast.makeText(getApplicationContext(),
                        "You have entered an invalid email or password", Toast.LENGTH_LONG).show();
            }

        });

        final TextView signup = findViewById(R.id.signup);
        signup.setOnClickListener(v -> {
            Intent launchActivity = new Intent(getApplicationContext(), SignUp.class);
            startActivity(launchActivity);
        });
    }
}
