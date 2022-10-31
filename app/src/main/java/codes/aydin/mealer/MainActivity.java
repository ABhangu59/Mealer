package codes.aydin.mealer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
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

            String[] projection = {"email", "password", "type"};

            String selection = "email = ?";
            String[] selectionArgs = {email};

            Cursor cursor = db.query(codes.aydin.mealer.DBHelper.USER_TABLE_NAME, projection, selection, selectionArgs, null, null, null);

            List<String[]> rows = new ArrayList<>();
            while(cursor.moveToNext()) {
                String[] row = {cursor.getString(cursor.getColumnIndexOrThrow("password")), cursor.getString(cursor.getColumnIndexOrThrow("type"))};
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

                Intent launchActivity;
                if (row[1].equals("admin")) {
                    launchActivity = new Intent(getApplicationContext(), ComplaintPage.class);
                } else {
                    launchActivity = new Intent(getApplicationContext(), WelcomePage.class).putExtra("type", row[1]);
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