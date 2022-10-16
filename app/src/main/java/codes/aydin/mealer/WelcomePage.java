package codes.aydin.mealer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class WelcomePage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomepage);

        TextView message = (TextView) findViewById(R.id.txtWelcomeMsg);
        message.setText("Welcome! You are logged in as " + getIntent().getExtras().getString("type"));

        final ImageView logo = (ImageView) findViewById(R.id.imgWelcomeLogo);
        logo.setImageResource(R.drawable.mealer_logo);

        final Button logout = (Button) findViewById(R.id.btnLogOut);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(launchActivity);
                finish();
            }
        });
    }
}