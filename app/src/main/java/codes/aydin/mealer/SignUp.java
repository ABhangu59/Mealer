package codes.aydin.mealer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.net.Uri;

public class SignUp extends Activity {

    Button SelectImage;

    ImageView PreviewImage;

    int SELECT_PICTURE =  200;

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

        SelectImage = findViewById(R.id.selectImage);
        PreviewImage = findViewById(R.id.previewImage);

        SelectImage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               imageChooser();
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
