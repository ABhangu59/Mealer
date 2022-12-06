package codes.aydin.mealer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NewOrderSearch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);

        findViewById(R.id.btnBackSearch).setOnClickListener(view -> finish());

        String useremail = getIntent().getExtras().getString("email");


        findViewById(R.id.btnSubmitSearch).setOnClickListener(view -> {


            TextView textSearch = findViewById(R.id.txtSearchInput);
            String input = textSearch.getText().toString();

            // Check which selections are checked

            String mealTypes = "";
            String cuisineTypes = "";

            boolean entree = ((CheckBox) findViewById(R.id.chkEntree)).isChecked();
            if (entree) mealTypes += "0,";
            boolean salad = ((CheckBox) findViewById(R.id.chkSalad)).isChecked();
            if (salad) mealTypes += "2,";
            boolean side = ((CheckBox) findViewById(R.id.chkSide)).isChecked();
            if (side) mealTypes += "3,";
            boolean soup = ((CheckBox) findViewById(R.id.chkSoup)).isChecked();
            if (soup) mealTypes += "1,";
            boolean dessert = ((CheckBox) findViewById(R.id.chkDessert)).isChecked();
            if (dessert) mealTypes += "4,";

            boolean african = ((CheckBox) findViewById(R.id.chkAfrican)).isChecked();
            if (african) cuisineTypes += "0,";
            boolean american = ((CheckBox) findViewById(R.id.chkAmerican)).isChecked();
            if (american) cuisineTypes += "1,";
            boolean latin = ((CheckBox) findViewById(R.id.chkLatin)).isChecked();
            if (latin) cuisineTypes += "2,";
            boolean chinese = ((CheckBox) findViewById(R.id.chkChinese)).isChecked();
            if (chinese) cuisineTypes += "3,";
            boolean italian = ((CheckBox) findViewById(R.id.chkItalian)).isChecked();
            if (italian) cuisineTypes += "4,";
            boolean mediterranean = ((CheckBox) findViewById(R.id.chkMediterranean)).isChecked();
            if (mediterranean) cuisineTypes += "5,";
            boolean caribbean = ((CheckBox) findViewById(R.id.chkCarribean)).isChecked();
            if (caribbean) cuisineTypes += "6,";
            boolean indian = ((CheckBox) findViewById(R.id.chkIndian)).isChecked();
            if (indian) cuisineTypes += "7,";


            if (cuisineTypes.length() == 0) cuisineTypes = "0,1,2,3,4,5,6,7";
            else cuisineTypes = cuisineTypes.substring(0, cuisineTypes.length() - 1);
            if (mealTypes.length() == 0) mealTypes = "0,1,2,3,4";
            else mealTypes = mealTypes.substring(0, mealTypes.length() - 1);

            System.out.println(mealTypes);
            System.out.println(cuisineTypes);

            Intent launchActivity = new Intent(getApplicationContext(), SearchResults.class).putExtra("searchInfo", new String[]{mealTypes, cuisineTypes, input}).putExtra("user_email", useremail);
            startActivity(launchActivity);
        });


    }
}