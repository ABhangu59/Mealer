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

public class UserOrders extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_orders);

        String useremail = getIntent().getExtras().getString("email");

        findViewById(R.id.btnBackOrders).setOnClickListener(view ->
        {
            finish();
        });

        DBHelper DBHelper = new DBHelper(getApplication());

        SQLiteDatabase db = DBHelper.getReadableDatabase();

        LinearLayout orderScroll = findViewById(R.id.lytOrderScroll);

        String[] projection = {"cook_email", "cook_first_name", "cook_last_name", "order_date_time", "delivery_date_time", "status", "meal_name", "meal_price"};
        String selection = "user_email = ?";
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
                    cursor.getString(cursor.getColumnIndexOrThrow("meal_price"))};
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

            int status = Integer.parseInt(row[5]);
            String orderStatusText = (status == 0) ? "Order Placed" : ((status == 1) ? "Order Confirmed" : "Order Delivered");

            TextView orderStatus = new TextView(this);
            orderStatus.setText(orderStatusText);
            order.addView(orderStatus);

            if (status == 2) {
                TextView deliveryDate = new TextView(this);
                deliveryDate.setText("Delivery Date: " + row[4]);
                order.addView(deliveryDate);
            }

            orderScroll.addView(order);
        }




    }
}