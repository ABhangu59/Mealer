package codes.aydin.mealer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class SignUp extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        final TextView signIn =(TextView)findViewById(R.id.txtSignIn);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final RadioGroup accountType = (RadioGroup) findViewById(R.id.accountSelection);
        accountType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton choice = (RadioButton) findViewById(checkedId);

                if (choice.equals(findViewById(R.id.radioClient))) {
                    ViewGroup cookInfo = (ViewGroup) findViewById(R.id.cookInfo);
                    cookInfo.setVisibility(View.GONE);

                    ViewGroup clientInfo = (ViewGroup) findViewById(R.id.clientInfo);
                    clientInfo.setVisibility(View.VISIBLE);
                }
                if (choice.equals(findViewById(R.id.radioCook))) {
                    ViewGroup clientInfo = (ViewGroup) findViewById(R.id.clientInfo);
                    clientInfo.setVisibility(View.GONE);

                    ViewGroup cookInfo = (ViewGroup) findViewById(R.id.cookInfo);
                    cookInfo.setVisibility(View.VISIBLE);
                }
            }
        });

    }

}
