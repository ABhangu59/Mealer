package codes.aydin.mealer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CookPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_page);
        findViewById(R.id.btnCookLogout).setOnClickListener(view -> finish());

        String[] userinfo = getIntent().getExtras().getStringArray("userinfo");

        String cookEmail = userinfo[0];
        String firstName = userinfo[1];

        TextView welcomeMsg = findViewById(R.id.txtCookWelcome);
        welcomeMsg.setText("Welcome, " + firstName);

        findViewById(R.id.btnYourMenu).setOnClickListener(view -> {
            Intent launchActivity = new Intent(getApplicationContext(), MenuPage.class).putExtra("cook_email", cookEmail);
            startActivity(launchActivity);
        });

        findViewById(R.id.btnPendingOrders).setOnClickListener(view -> {
            Intent launchActivity = new Intent(getApplicationContext(), PendingOrders.class).putExtra("cook_email", cookEmail);
            startActivity(launchActivity);
        });

    }
}