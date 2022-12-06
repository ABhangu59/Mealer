package codes.aydin.mealer;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchResults extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        String[] intentInfo = getIntent().getExtras().getStringArray("searchInfo");
        String mealTypes = intentInfo[0], cuisineTypes = intentInfo[1], input = intentInfo[2];
//        String mealTypes = " ", cuisineTypes = " ", input = " ";

        findViewById(R.id.btnBackSearchResults).setOnClickListener(view -> finish());

        DBHelper DBHelper = new DBHelper(getApplicationContext());

        SQLiteDatabase db = DBHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + codes.aydin.mealer.DBHelper.MEAL_TABLE_NAME + " WHERE currently_offered = 1 AND meal_type IN(" + mealTypes + ") AND cuisine_type IN(" + cuisineTypes + ") AND LOWER(meal_name) LIKE '%" + input + "%'", new String[]{});
        List<String[]> rows = new ArrayList<>();
        while (cursor.moveToNext()) {
            String[] row = {cursor.getString(cursor.getColumnIndexOrThrow("meal_name")),
                    cursor.getString(cursor.getColumnIndexOrThrow("cook_email")),
                    cursor.getString(cursor.getColumnIndexOrThrow("description")),
                    cursor.getString(cursor.getColumnIndexOrThrow("allergens")),
                    cursor.getString(cursor.getColumnIndexOrThrow("ingredients")),
                    cursor.getString(cursor.getColumnIndexOrThrow("price")),
                    cursor.getString(cursor.getColumnIndexOrThrow("meal_type")),
                    cursor.getString(cursor.getColumnIndexOrThrow("cuisine_type")),
                    cursor.getString(cursor.getColumnIndexOrThrow("meal_num_ratings")),
                    cursor.getString(cursor.getColumnIndexOrThrow("meal_sum_ratings")),
                    cursor.getString(cursor.getColumnIndexOrThrow("meal_id"))};
            rows.add(row);
        }
        cursor.close();

        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        String today = date.toString().replaceAll("-", "/");

        cursor = db.rawQuery("SELECT email FROM " + codes.aydin.mealer.DBHelper.SUSPENSION_TABLE_NAME + " WHERE unsuspension_date > ?", new String[]{today});
        List<String> suspendedCooks = new ArrayList<>();
        while (cursor.moveToNext())
            suspendedCooks.add(cursor.getString(cursor.getColumnIndexOrThrow("email")));
        cursor.close();

        LinearLayout mealScroll = findViewById(R.id.lytMealChoices);

        if (rows.size() != 0) {
            findViewById(R.id.txtNoResultsFound).setVisibility(View.GONE);
        }

        for (String[] row : rows) {

            if (suspendedCooks.contains(row[1])) continue;

            LinearLayout meal = new LinearLayout(this);

            meal.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) meal.getLayoutParams();
            params.setMargins(0, 15, 0, 15);
            meal.setLayoutParams(params);
            meal.setPadding(30, 30, 30, 30);
            meal.setBackgroundColor(getResources().getColor(R.color.light_grey));
            meal.setId(View.generateViewId());
            meal.setOrientation(LinearLayout.VERTICAL);


            TextView mealName = new TextView(this);
            mealName.setText(row[0]);
            mealName.setTypeface(null, Typeface.BOLD);
            mealName.setTextSize(16);

            meal.addView(mealName);

            String[] cuisines = getResources().getStringArray(R.array.CuisineTypes);
            String[] meals = getResources().getStringArray(R.array.MealTypes);


            TextView mealCuisine = new TextView(this);
            mealCuisine.setText(cuisines[Integer.parseInt(row[7])] + " " + meals[Integer.parseInt(row[6])] + " |  $" + row[7]);
            meal.addView(mealCuisine);


            TextView description = new TextView(this);
            description.setText(row[2]);
            meal.addView(description);

            TextView rating = new TextView(this);
            rating.setText((Integer.parseInt(row[8]) == 0) ? "No ratings" : ((Double.parseDouble(row[9]) / Double.parseDouble(row[8])) + "/5 stars"));
            meal.addView(rating);

            TextView empty = new TextView(this);
            empty.setText("");
            meal.addView(empty);

            TextView ingredients = new TextView(this);
            ingredients.setText("Ingredients: " + row[4]);
            meal.addView(ingredients);

            TextView allergens = new TextView(this);
            allergens.setText("Allergens: " + row[3]);
            meal.addView(allergens);

//            meal.setOnClickListener(view -> finish());

            mealScroll.addView(meal);
        }
    }
}