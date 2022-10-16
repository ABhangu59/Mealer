package codes.aydin.mealer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
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

        final Button signin = (Button) findViewById(R.id.btnSignUp);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText emailText = (EditText) findViewById(R.id.txtEmail);
                EditText passwordText = (EditText) findViewById(R.id.txtPassword);
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

                Cursor cursor = db.query("Users", projection, selection, selectionArgs, null, null, null);

                List rows = new ArrayList<>();
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

                String[] row = (String[]) rows.get(0);

                if (row[0].equals(password)) {
                    Intent launchActivity = new Intent(getApplicationContext(), WelcomePage.class).putExtra("type", row[1]);
                    startActivity(launchActivity);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "You have entered an invalid email or password", Toast.LENGTH_LONG).show();
                    return;
                }

            }
        });

        final TextView signup = (TextView)findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent launchActivity = new Intent(getApplicationContext(), SignUp.class);
               startActivity(launchActivity);
           }
        });
    }
}