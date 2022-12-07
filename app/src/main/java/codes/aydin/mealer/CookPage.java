package codes.aydin.mealer;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class CookPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_page);
        findViewById(R.id.btnCookLogout).setOnClickListener(view -> finish());
        DBHelper DBHelper = new DBHelper(getApplicationContext());

        SQLiteDatabase db = DBHelper.getReadableDatabase();


        String[] userinfo = getIntent().getExtras().getStringArray("userinfo");

        String cookEmail = userinfo[0];
        String firstName = userinfo[1];

        TextView welcomeMsg = findViewById(R.id.txtCookWelcome);
        welcomeMsg.setText("Welcome, " + firstName);

        TextView subtitle = findViewById(R.id.txtReviewSubtitle);

        String[] projection2 = {"rating_num", "rating_sum"};
        String selection2 = "cook_email = ?";
        String[] selectionArgs2 = {cookEmail};

        Cursor cursor2 = db.query(codes.aydin.mealer.DBHelper.RATING_TABLE_NAME, projection2, selection2, selectionArgs2, null, null, null);
        List<String[]> rows2 = new ArrayList<>();
        while (cursor2.moveToNext()) {
            rows2.add(new String[]{cursor2.getString(cursor2.getColumnIndexOrThrow("rating_num")),
                    cursor2.getString(cursor2.getColumnIndexOrThrow("rating_sum"))});
        }
        cursor2.close();

        if (rows2.size() == 0)
            subtitle.setText("You have no reviews.");
        else
            subtitle.setText("You currently have a rating of " + ((Double.parseDouble(rows2.get(0)[1]) / Double.parseDouble(rows2.get(0)[0])) + "/5 stars"));


        findViewById(R.id.btnYourMenu).setOnClickListener(view -> {
            Intent launchActivity = new Intent(getApplicationContext(), MenuPage.class).putExtra("cook_email", cookEmail);
            startActivity(launchActivity);
        });

        findViewById(R.id.btnPendingOrders).setOnClickListener(view -> {
            Intent launchActivity = new Intent(getApplicationContext(), PendingOrders.class).putExtra("cook_email", cookEmail);
            startActivity(launchActivity);
        });

    }
}