package codes.aydin.mealer;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
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

public class PendingOrders extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_orders);

        findViewById(R.id.btnBackPending).setOnClickListener(view -> finish());

        LinearLayout pendingOrders = findViewById(R.id.lytPendingOrders);

        String cookEmail = getIntent().getExtras().getString("cook_email");

        DBHelper DBHelper = new DBHelper(getApplicationContext());

        SQLiteDatabase db = DBHelper.getReadableDatabase();


        String[] projection = {"user_email", "order_date_time", "delivery_date_time", "status", "meal_name", "meal_price", "order_id"};
        String selection = "cook_email = ? AND status = 0";
        String[] selectionArgs = {cookEmail};

        Cursor cursor = db.query(codes.aydin.mealer.DBHelper.ORDER_TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        List<String[]> rows = new ArrayList<>();
        while (cursor.moveToNext()) {
            String[] row = {cursor.getString(cursor.getColumnIndexOrThrow("user_email")),
                    cursor.getString(cursor.getColumnIndexOrThrow("order_date_time")),
                    cursor.getString(cursor.getColumnIndexOrThrow("delivery_date_time")),
                    cursor.getString(cursor.getColumnIndexOrThrow("status")),
                    cursor.getString(cursor.getColumnIndexOrThrow("meal_name")),
                    cursor.getString(cursor.getColumnIndexOrThrow("meal_price")),
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
            mealName.setText(row[4]);
            mealName.setTypeface(null, Typeface.BOLD);
            mealName.setTextSize(16);

            order.addView(mealName);

            TextView cook = new TextView(this);
            cook.setText("Client: " + row[0]);
            order.addView(cook);

            TextView price = new TextView(this);
            price.setText("Price: $" + row[5]);
            order.addView(price);

            TextView orderDate = new TextView(this);
            orderDate.setText("Ordered: " + row[1]);
            order.addView(orderDate);

            int status = Integer.parseInt(row[3]);
            String orderStatusText = (status == 0) ? "Order Pending" : ((status == 1) ? "Order Confirmed" : "Order Rejected");

            TextView orderStatus = new TextView(this);
            orderStatus.setText(orderStatusText);
            order.addView(orderStatus);

            order.setClickable(true);
            order.setOnClickListener(view -> {
                AlertDialog.Builder confirm = new AlertDialog.Builder(this);
                confirm.setMessage("Would you like to accept this order request?");
                confirm.setPositiveButton("Confirm", (dialogInterface, i) -> {
                    ContentValues cv = new ContentValues();

                    cv.put("status", 1);

                    db.update(codes.aydin.mealer.DBHelper.ORDER_TABLE_NAME, cv, "order_id = ?", new String[]{row[6]});
                    Intent launchActivity = new Intent(getApplicationContext(), PendingOrders.class).putExtra("cook_email", cookEmail);
                    startActivity(launchActivity);
                    finish();
                });
                confirm.setNegativeButton("Reject", (dialogInterface, i) -> {
                    ContentValues cv = new ContentValues();

                    cv.put("status", 2);

                    db.update(codes.aydin.mealer.DBHelper.ORDER_TABLE_NAME, cv, "order_id = ?", new String[]{row[6]});
                    Intent launchActivity = new Intent(getApplicationContext(), PendingOrders.class).putExtra("cook_email", cookEmail);
                    startActivity(launchActivity);
                    finish();
                });
                confirm.show();
            });

            pendingOrders.addView(order);
        }
    }
}