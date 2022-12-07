package codes.aydin.mealer;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class FileComplaint extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_complaint);

        findViewById(R.id.btnBackComplaint).setOnClickListener(view -> finish());
        String useremail = getIntent().getExtras().getString("email");

        DBHelper DBHelper = new DBHelper(getApplicationContext());

        SQLiteDatabase db = DBHelper.getReadableDatabase();

        LinearLayout orderScroll = findViewById(R.id.lytComplaintOrders);

        String[] projection = {"cook_email", "cook_first_name", "cook_last_name", "order_date_time", "delivery_date_time", "status", "meal_name", "meal_price", "meal_rating", "order_id"};
        String selection = "user_email = ? AND status = 1";
        String[] selectionArgs = {useremail};

        Cursor cursor = db.query(codes.aydin.mealer.DBHelper.ORDER_TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        List<String[]> rows = new ArrayList<>();
        while (cursor.moveToNext()) {
            String[] row = {cursor.getString(cursor.getColumnIndexOrThrow("cook_email")),
                    cursor.getString(cursor.getColumnIndexOrThrow("cook_first_name")),
                    cursor.getString(cursor.getColumnIndexOrThrow("cook_last_name")),
                    cursor.getString(cursor.getColumnIndexOrThrow("order_date_time")),
                    cursor.getString(cursor.getColumnIndexOrThrow("delivery_date_time")),
                    cursor.getString(cursor.getColumnIndexOrThrow("status")),
                    cursor.getString(cursor.getColumnIndexOrThrow("meal_name")),
                    cursor.getString(cursor.getColumnIndexOrThrow("meal_price")),
                    cursor.getString(cursor.getColumnIndexOrThrow("meal_rating")),
                    cursor.getString(cursor.getColumnIndexOrThrow("order_id"))};
            rows.add(row);
        }
        cursor.close();

        for (String[] row : rows) {
            LinearLayout order = new LinearLayout(this);

            order.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) order.getLayoutParams();
            params.setMargins(0, 15, 0, 15);
            order.setLayoutParams(params);
            order.setPadding(30, 30, 30, 30);
            order.setBackgroundColor(getResources().getColor(R.color.light_grey));
            order.setId(View.generateViewId());
            order.setOrientation(LinearLayout.VERTICAL);


            TextView mealName = new TextView(this);
            mealName.setText(row[6]);
            mealName.setTypeface(null, Typeface.BOLD);
            mealName.setTextSize(16);

            order.addView(mealName);

            TextView cook = new TextView(this);
            cook.setText("Cook: " + row[1] + " " + row[2]);
            order.addView(cook);

            TextView price = new TextView(this);
            price.setText("Price: $" + row[7]);
            order.addView(price);

            TextView orderDate = new TextView(this);
            orderDate.setText("Ordered: " + row[3]);
            order.addView(orderDate);

            TextView deliveryDate = new TextView(this);
            deliveryDate.setText("Pickup Date: " + row[4]);
            order.addView(deliveryDate);
            order.setBackgroundColor(getResources().getColor(R.color.light_green));

            order.setOnClickListener(view -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Describe your complaint");

                final String[] m_Text = {""};
// Set up the input
                final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

// Set up the buttons
                builder.setPositiveButton("Submit", (dialog, which) -> {
                    ContentValues cv = new ContentValues();
                    cv.put("cook_email", row[0]);
                    cv.put("client_email", useremail);
                    cv.put("description", input.getText().toString());
                    db.insert(codes.aydin.mealer.DBHelper.COMPLAINT_TABLE_NAME, null, cv);
                    finish();

                });
                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

                builder.show();
            });


            orderScroll.addView(order);

        }
    }
}
