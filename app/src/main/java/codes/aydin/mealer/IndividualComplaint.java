package codes.aydin.mealer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

public class IndividualComplaint extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_complaint);

        DBHelper DBHelper = new DBHelper(getApplicationContext());
        SQLiteDatabase db = DBHelper.getWritableDatabase();

        findViewById(R.id.btnBack).setOnClickListener(view -> {
            Intent launchActivity = new Intent(getApplicationContext(), ComplaintPage.class);
            startActivity(launchActivity);
            finish();
        });

        String[] complaint = getIntent().getExtras().getStringArray("complaintInfo");

        TextView cookEmail = findViewById(R.id.txtCookEmail);
        cookEmail.setText(complaint[0]);

        TextView clientEmail = findViewById(R.id.txtClientEmail);
        clientEmail.setText(complaint[1]);

        TextView description = findViewById(R.id.txtDescription);
        description.setText(complaint[2]);

        findViewById(R.id.btnDismiss).setOnClickListener(view -> {
            ContentValues cv = new ContentValues();
            cv.put("is_resolved", 1);

            db.update(codes.aydin.mealer.DBHelper.COMPLAINT_TABLE_NAME, cv, "complaint_id = ?", new String[]{complaint[3]});

            Intent launchActivity = new Intent(getApplicationContext(), ComplaintPage.class);
            startActivity(launchActivity);
            finish();
        });

        findViewById(R.id.btnSuspend).setOnClickListener(view -> {
            Pattern datePattern = Pattern.compile("([12]\\d{3}\\/(0[1-9]|1[0-2])\\/(0[1-9]|[12]\\d|3[01]))");

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Suspend Cook?");

            final String[] choice = {"Permanently"};
            final String[] date = {"9999/12/31"};
            EditText et = new EditText(this);
            et.setHint("YYYY/MM/DD");
            builder.setView(et);
            String[] choices = new String[]{"Temporarily", "Permanently"};

            builder.setSingleChoiceItems(choices, 1, (dialogInterface, i) -> {
//                if (i == 1) date[0] = "9999/12/31";
//                else date[0] = et.getText().toString();
//                System.out.println(date[0]);
//
                choice[0] = choices[i];

            });
            builder.setPositiveButton("Suspend", null);
            builder.setNegativeButton("Cancel", null);

            AlertDialog dialog = builder.create();
            dialog.show();
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(view1 -> {


                if (choice[0].equals("Permanently")) {
                    date[0] = "9999/12/31";
                } else {
                    date[0] = et.getText().toString();
                }

                if ((!choice[0].equals("Permanently") && datePattern.matcher(date[0]).matches()) || choice[0].equals("Permanently")) {
                    ContentValues cv = new ContentValues();
                    cv.put("email", complaint[0]);
                    cv.put("reason", "None given");
                    cv.put("unsuspension_date", date[0]);
                    db.insert(codes.aydin.mealer.DBHelper.SUSPENSION_TABLE_NAME, null, cv);

                    ContentValues cv2 = new ContentValues();
                    cv2.put("is_resolved", 1);

                    db.update(codes.aydin.mealer.DBHelper.COMPLAINT_TABLE_NAME, cv2, "complaint_id = ?", new String[]{complaint[3]});
                    dialog.dismiss();
                    Intent launchActivity = new Intent(getApplicationContext(), ComplaintPage.class);
                    startActivity(launchActivity);
                    finish();
                }

            });
        });
    }


}
