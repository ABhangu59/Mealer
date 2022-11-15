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
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_meal);

        String[] mealInfo = getIntent().getExtras().getStringArray("mealInfo");
        boolean addMeal = mealInfo == null;

        EditText mealName = findViewById(R.id.txtMealName);
        mealName.setText((addMeal) ? "" : mealInfo[0]);
        //Add functionality from here and beyond. 106.10

    }
}