package codes.aydin.mealer;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Locale;
import java.util.regex.Pattern;

public class SignUp extends Activity {

    Button SelectImage;

    ImageView PreviewImage;

    int SELECT_PICTURE =  200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DBHelper DBHelper = new DBHelper(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        final TextView signIn = findViewById(R.id.txtSignIn);
        signIn.setOnClickListener(v -> finish());

        final ImageView logo = findViewById(R.id.imgSignUpLogo);
        logo.setImageResource(R.drawable.mealer_logo);

        final RadioGroup accountType = findViewById(R.id.accountSelection);
        accountType.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton choice = findViewById(checkedId);

            if (choice.equals(findViewById(R.id.radioClient))) {
                ViewGroup cookInfo = findViewById(R.id.cookInfo);
                cookInfo.setVisibility(View.GONE);

                ViewGroup clientInfo = findViewById(R.id.clientInfo);
                clientInfo.setVisibility(View.VISIBLE);
            }
            if (choice.equals(findViewById(R.id.radioCook))) {
                ViewGroup clientInfo = findViewById(R.id.clientInfo);
                clientInfo.setVisibility(View.GONE);

                ViewGroup cookInfo = findViewById(R.id.cookInfo);
                cookInfo.setVisibility(View.VISIBLE);
            }
        });

        SelectImage = findViewById(R.id.selectImage);
        PreviewImage = findViewById(R.id.previewImage);

        SelectImage.setOnClickListener(v -> imageChooser());


        final Button signUp = findViewById(R.id.btnSignUp);
        signUp.setOnClickListener(v -> {

            Pattern emailPattern = Pattern.compile("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
            Pattern postalPattern = Pattern.compile("^([A-Za-z]\\d[A-Za-z][-]?\\d[A-Za-z]\\d)");

            boolean valid = true;

            EditText firstNameText = findViewById(R.id.txtFirstName);
            String firstName = firstNameText.getText().toString().trim();
            if (firstName.isEmpty()) valid = false;

            EditText lastNameText = findViewById(R.id.txtLastName);
            String lastName = lastNameText.getText().toString().trim();
            if (lastName.isEmpty()) valid = false;

            EditText emailText = findViewById(R.id.txtEmail);
            String email = emailText.getText().toString().trim().toLowerCase(Locale.ROOT);
            if (email.isEmpty() || !emailPattern.matcher(email).matches()) valid = false;

            EditText passwordText = findViewById(R.id.txtPassword);
            String password = passwordText.getText().toString().trim();
            if (password.isEmpty()) valid = false;

            EditText address1dText = findViewById(R.id.txtAddress1);
            String address1 = address1dText.getText().toString().trim();
            if (address1.isEmpty()) valid = false;

            EditText address2dText = findViewById(R.id.txtAddress2);
            String address2 = address2dText.getText().toString().trim();

            EditText cityText = findViewById(R.id.txtCity);
            String city = cityText.getText().toString().trim();
            if (city.isEmpty()) valid = false;

            Spinner provinceSpinner = findViewById(R.id.spnProvince);
            String province = provinceSpinner.getSelectedItem().toString().trim();

            EditText postalCodeText = findViewById(R.id.txtPostalCode);
            String postalCode = postalCodeText.getText().toString().trim().toUpperCase(Locale.ROOT);
            if (postalCode.isEmpty() || !postalPattern.matcher(postalCode).matches()) valid = false;

            RadioGroup accountType1 = findViewById(R.id.accountSelection);

            int choice = accountType1.getCheckedRadioButtonId();
            String userType = "";

            String creditCardNumber = null;
            String creditCardMonth = null;
            String creditCardYear = null;
            String creditCardCVV = null;

            if (choice == R.id.radioClient) {
                userType = "client";
                EditText creditCardNumberText = findViewById(R.id.txtCCNumber);
                creditCardNumber = creditCardNumberText.getText().toString().trim();
                if (creditCardNumber.length() != 16) valid = false;

                Spinner monthSpinner = findViewById(R.id.spnMonth);
                creditCardMonth = monthSpinner.getSelectedItem().toString().trim();

                Spinner yearSpinner = findViewById(R.id.spnYear);
                creditCardYear = yearSpinner.getSelectedItem().toString().trim();

                EditText creditCardCVVText = findViewById(R.id.txtCVV);
                creditCardCVV = creditCardCVVText.getText().toString().trim();
                if (creditCardCVV.length() != 3) valid = false;

            }

            String bio = null;

            if (choice == R.id.radioCook) {
                userType = "cook";
                EditText bioText = findViewById(R.id.txtCookBio);
                bio = bioText.getText().toString().trim();
                if (bio.isEmpty()) valid = false;

                ImageView preview = findViewById(R.id.previewImage);
                Drawable img = preview.getDrawable();

                if (img == null) valid = false;
                else {
                    BitmapDrawable bitDw = ((BitmapDrawable) img);
                    Bitmap bitmap = bitDw.getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] imageInByte = stream.toByteArray();
                }
            }

            //TODO check if user already exists once database is set up

            if (!valid)
                Toast.makeText(getApplicationContext(),
                        "Please fill out all of the fields", Toast.LENGTH_LONG).show();
            else {

                User user = null;
                Address address = new Address(address1, address2, city, province, postalCode);

                if (userType.equals("client")) {
                    user = new Client(firstName, lastName, email, password, address,
                            new CreditCard(firstName + " " + lastName, creditCardNumber, creditCardMonth, creditCardYear, creditCardCVV));
                } else if (userType.equals("cook")) {
                    user = new Cook(firstName, lastName, email, password, address, bio, "");
                }

                SQLiteDatabase db = DBHelper.getWritableDatabase();

                ContentValues user_values = new ContentValues();
                user_values.put("type", userType);
                user_values.put("first_name", firstName);
                user_values.put("email", email);
                user_values.put("last_name", lastName);
                user_values.put("password", password);
                user_values.put("bio", bio);
                user_values.put("void_cheque", "");
                db.insert(codes.aydin.mealer.DBHelper.USER_TABLE_NAME, null, user_values);

                ContentValues address_values = new ContentValues();
                address_values.put("email", email);
                address_values.put("address_1", address1);
                address_values.put("address_2", address2);
                address_values.put("city", city);
                address_values.put("province", province);
                address_values.put("postal_code", postalCode);
                db.insert(codes.aydin.mealer.DBHelper.ADDRESS_TABLE_NAME, null, address_values);

                ContentValues credit_values = new ContentValues();
                credit_values.put("email", email);
                credit_values.put("full_name", firstName + " " + lastName);
                credit_values.put("number", creditCardNumber);
                credit_values.put("expiration_month", creditCardMonth);
                credit_values.put("expiration_year", creditCardYear);
                credit_values.put("cvv", creditCardCVV);
                db.insert(codes.aydin.mealer.DBHelper.CREDIT_TABLE_NAME, null, credit_values);

                Intent submitInfo = null;
                if (userType.equals("client"))
                    submitInfo = new Intent(getApplicationContext(),UserScreen.class).putExtra("userinfo", new String[]{email, firstName});
                else if (userType.equals("cook"))
                    submitInfo = new Intent(getApplicationContext(), MenuPage.class).putExtra("cook_email", email);
                startActivity(submitInfo);
                finish();
            }
        });

    }

    void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    PreviewImage.setImageURI(selectedImageUri);
                }
            }
        }
    }

}