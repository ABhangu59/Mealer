package codes.aydin.mealer;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;


public class CookPage extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_page);

        findViewById(R.id.btnCookLogout).setOnClickListener(view -> finish());

        String cookEmail = getIntent().getExtras().getString("cook_email");

        findViewById(R.id.btnAddMeal).setOnClickListener(view -> {
            Intent launchActivity = new Intent(getApplicationContext(), IndividualMeal.class).putExtra("mealInfo", new String[]{cookEmail});
            startActivity(launchActivity);
            finish();
        });



        LinearLayout menuLayout = findViewById(R.id.menuLayout);

        DBHelper DBHelper = new DBHelper(getApplicationContext());

        SQLiteDatabase db = DBHelper.getReadableDatabase();

        String[] projection = {"cook_email","meal_name","meal_type","cuisine_type","description","allergens","ingredients","price","currently_offered","meal_id", "cook_email"};
        String selection = "cook_email = ?";
        String[] selectionArgs = {cookEmail};

        Cursor cursor = db.query(codes.aydin.mealer.DBHelper.MEAL_TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        List<String[]> rows = new ArrayList<>();
        while(cursor.moveToNext()) {
            String[] row = {cursor.getString(cursor.getColumnIndexOrThrow("cook_email")),
                    cursor.getString(cursor.getColumnIndexOrThrow("meal_name")),
                    cursor.getString(cursor.getColumnIndexOrThrow("meal_type")),
                    cursor.getString(cursor.getColumnIndexOrThrow("cuisine_type")),
                    cursor.getString(cursor.getColumnIndexOrThrow("description")),
                    cursor.getString(cursor.getColumnIndexOrThrow("ingredients")),
                    cursor.getString(cursor.getColumnIndexOrThrow("allergens")),
                    cursor.getString(cursor.getColumnIndexOrThrow("price")),
                    cursor.getString(cursor.getColumnIndexOrThrow("currently_offered")),
                    cursor.getString(cursor.getColumnIndexOrThrow("meal_id"))};
            rows.add(row);
        }
        cursor.close();


        for (String[] row : rows) {
            Button complaintBtn = new Button(this);
            complaintBtn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            complaintBtn.setText(row[1]);
            complaintBtn.setId(View.generateViewId());

            complaintBtn.setOnClickListener(l -> {
                Intent launchActivity = new Intent(getApplicationContext(), IndividualMeal.class).putExtra("mealInfo", row);
                startActivity(launchActivity);
                finish();
            });

            menuLayout.addView(complaintBtn);
        }
    }
}
