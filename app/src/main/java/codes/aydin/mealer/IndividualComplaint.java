package codes.aydin.mealer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

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
            Pattern datePattern = Pattern.compile("([12]\\d{3}/(0[1-9]|1[0-2])/(0[1-9]|[12]\\d|3[01]))");


            });
    }


}
