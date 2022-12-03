package codes.aydin.mealer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class UserScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_screen);

        String[] userinfo = getIntent().getExtras().getStringArray("userinfo");
        String userEmail = userinfo[0];
        String userName = userinfo[1];

        TextView welcomeMsg = findViewById(R.id.txtUserWelcome);
        welcomeMsg.setText("Welcome, " + userName);

        findViewById(R.id.btnUserLogout).setOnClickListener(view -> {
            finish();
        });

        findViewById(R.id.btnYourOrders).setOnClickListener(view -> {
            Intent launchActivity = new Intent(getApplicationContext(), UserOrders.class)
                    .putExtra("email", userEmail);
            startActivity(launchActivity);
        });

        findViewById(R.id.btnNewOrder).setOnClickListener(view -> {
            Intent launchActivity = new Intent(getApplicationContext(), NewOrderSearch.class)
                    .putExtra("email", userEmail);
            startActivity(launchActivity);
        });
    }
}