package codes.aydin.mealer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class IndividualMeal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_meal);

        DBHelper DBHelper = new DBHelper(getApplicationContext());
        SQLiteDatabase db = DBHelper.getWritableDatabase();

        String[] mealInfo = getIntent().getExtras().getStringArray("mealInfo");
        boolean addMeal = mealInfo.length == 1;

        TextView title = findViewById(R.id.txtAddEdit);
        title.setText(((addMeal) ? "Add" : "Edit") + "Meal");


        EditText mealName = findViewById(R.id.txtMealName);
        mealName.setText((addMeal) ? "" : mealInfo[1]);

        Spinner mealType = findViewById(R.id.spnMealType);
        mealType.setSelection((addMeal) ? 0 : Integer.parseInt(mealInfo[2]));

        Spinner cuisineType = findViewById(R.id.spnCuisineType);
        cuisineType.setSelection((addMeal) ? 0 : Integer.parseInt(mealInfo[3]));

        EditText mealDescription = findViewById(R.id.txtMealDescription);
        mealDescription.setText((addMeal) ? "" : mealInfo[4]);

        EditText mealIngredients = findViewById(R.id.txtMealIngredients);
        mealIngredients.setText((addMeal) ? "" : mealInfo[5]);

        EditText mealAllergens = findViewById(R.id.txtMealAllergens);
        mealAllergens.setText((addMeal) ? "" : mealInfo[6]);

        EditText mealPrice = findViewById(R.id.txtMealPrice);
        mealPrice.setText((addMeal) ? "" : mealInfo[7]);

        Switch currentlyOffered = findViewById(R.id.swtOffered);
        currentlyOffered.setChecked(!addMeal && ((mealInfo[8]).equals("1")));

        Button submit = findViewById(R.id.btnAddSave);
        submit.setText((addMeal) ? "Create Meal" : "Save Changes");
        submit.setOnClickListener(v -> {
            if(mealName.getText().toString().length() == 0 || mealDescription.getText().toString().length() == 0 ||
                    mealIngredients.getText().toString().length() == 0 ||
                    mealPrice.getText().toString().length() == 0)
            {
                Toast.makeText(getApplicationContext(),
                        "Please fill in all required fields.", Toast.LENGTH_LONG).show();
            }
            else
            {
                ContentValues cv = new ContentValues();
                cv.put("cook_email", mealInfo[0]);
                cv.put("meal_name", mealName.getText().toString());
                cv.put("description", mealDescription.getText().toString());
                cv.put("allergens", mealAllergens.getText().toString());
                cv.put("ingredients", mealIngredients.getText().toString());
                cv.put("price", mealPrice.getText().toString());
                cv.put("currently_offered", (currentlyOffered.isChecked()) ? "1" : "0");
                cv.put("meal_type", mealType.getSelectedItemPosition());
                cv.put("cuisine_type", cuisineType.getSelectedItemPosition());

                if (addMeal) db.insert(codes.aydin.mealer.DBHelper.MEAL_TABLE_NAME, null, cv);
                else {db.update(codes.aydin.mealer.DBHelper.MEAL_TABLE_NAME, cv, "meal_id = ?", new String[]{mealInfo[9]});}

                Intent launchActivity = new Intent(getApplicationContext(), CookPage.class).putExtra("cook_email", mealInfo[0]);
                startActivity(launchActivity);
                finish();
            }
        });

        Button cancel = findViewById(R.id.btnCancelMeal);
        cancel.setOnClickListener(v -> {
            Intent launchActivity = new Intent(getApplicationContext(), CookPage.class).putExtra("cook_email", mealInfo[0]);

            startActivity(launchActivity);
            finish();
        });

        Button delete = findViewById(R.id.btnDeleteMeal);

        if (addMeal) delete.setVisibility(View.GONE);
        delete.setOnClickListener(v ->
        {
            if (mealInfo[8].equals("1"))
            {
                Toast.makeText(getApplicationContext(),
                        "Cannot remove a meal that is currently offered.", Toast.LENGTH_LONG).show();
            }

            db.delete(codes.aydin.mealer.DBHelper.MEAL_TABLE_NAME, "meal_id = ?", new String[]{mealInfo[9]});

            Intent launchActivity = new Intent(getApplicationContext(), CookPage.class).putExtra("cook_email", mealInfo[0]);

            startActivity(launchActivity);
            finish();
        });

    }

}
