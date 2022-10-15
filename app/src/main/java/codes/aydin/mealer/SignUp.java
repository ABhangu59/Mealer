package codes.aydin.mealer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

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


        final Button signUp = (Button) findViewById(R.id.btnSignUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Pattern emailPattern = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
                Pattern postalPattern = Pattern.compile("^([A-Za-z]\\d[A-Za-z][-]?\\d[A-Za-z]\\d)");

                boolean valid = true;

                EditText firstNameText = (EditText) findViewById(R.id.txtFirstName);
                String firstName = firstNameText.getText().toString().trim();
                if (firstName.isEmpty()) valid = false;

                EditText lastNameText = (EditText) findViewById(R.id.txtLastName);
                String lastName = lastNameText.getText().toString().trim();
                if (lastName.isEmpty()) valid = false;

                EditText emailText = (EditText) findViewById(R.id.txtEmail);
                String email = emailText.getText().toString().trim();
                if (email.isEmpty() || !emailPattern.matcher(email).matches()) valid = false;

                EditText passwordText = (EditText) findViewById(R.id.txtPassword);
                String password = passwordText.getText().toString().trim();
                if (password.isEmpty()) valid = false;

                EditText address1dText = (EditText) findViewById(R.id.txtAddress1);
                String address1 = address1dText.getText().toString().trim();
                if (address1.isEmpty()) valid = false;

                EditText address2dText = (EditText) findViewById(R.id.txtAddress2);
                String address2 = address2dText.getText().toString().trim();

                EditText cityText = (EditText) findViewById(R.id.txtCity);
                String city = cityText.getText().toString().trim();
                if (city.isEmpty()) valid = false;

                Spinner provinceSpinner = (Spinner) findViewById(R.id.spnProvince);
                String province = provinceSpinner.getSelectedItem().toString().trim();

                EditText postalCodeText = (EditText) findViewById(R.id.txtPostalCode);
                String postalCode = postalCodeText.getText().toString().trim();
                if (postalCode.isEmpty() || !postalPattern.matcher(postalCode).matches()) valid = false;

                RadioGroup accountType = (RadioGroup) findViewById(R.id.accountSelection);

                int choice = accountType.getCheckedRadioButtonId();

                String creditCardNumber;
                String creditCardMonth;
                String creditCardYear;
                String creditCardCVV;

                if (choice == R.id.radioClient) {
                    EditText creditCardNumberText = (EditText) findViewById(R.id.txtCCNumber);
                    creditCardNumber = creditCardNumberText.getText().toString().trim();
                    if (creditCardNumber.length() != 16) valid = false;

                    Spinner monthSpinner = (Spinner) findViewById(R.id.spnMonth);
                    creditCardMonth = monthSpinner.getSelectedItem().toString().trim();

                    Spinner yearSpinner = (Spinner) findViewById(R.id.spnYear);
                    creditCardYear = yearSpinner.getSelectedItem().toString().trim();

                    EditText creditCardCVVText = (EditText) findViewById(R.id.txtCVV);
                    creditCardCVV = creditCardCVVText.getText().toString().trim();
                    if (creditCardCVV.length() != 3) valid = false;

                }

                String bio;

                if (choice == R.id.radioCook) {
                    EditText bioText = (EditText) findViewById(R.id.txtCookBio);
                    bio = bioText.getText().toString().trim();
                    if (bio.isEmpty()) valid = false;
                }

                if (!valid)
                    Toast.makeText(getApplicationContext(),
                        "Please fill out all of the fields", Toast.LENGTH_LONG).show();

            }
        });
    }

}
