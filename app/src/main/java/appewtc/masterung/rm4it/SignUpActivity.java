package appewtc.masterung.rm4it;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class SignUpActivity extends AppCompatActivity {

    //Explicit
    private EditText nameEditText, idCardEditText, positionEditText,
            workYearEditText, emailEditText;
    private Spinner provinceSpinner;
    private String nameString, idCardString, positionString, workYearString,
            emailString, provinceString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Bind Widget
        bindWidget();

    }   // Main Method

    public void clickSaveData(View view) {

        nameString = nameEditText.getText().toString().trim();
        idCardString = idCardEditText.getText().toString().trim();
        positionString = positionEditText.getText().toString().trim();
        workYearString = workYearEditText.getText().toString().trim();
        emailString = emailEditText.getText().toString().trim();

        //Check Space
        if (checkSpace()) {
            //Have Space

        } else {
            //No Space

        }   // if

    }   // clickSaveData

    private boolean checkSpace() {

        boolean bolResult = true;

        bolResult = nameString.equals("") || idCardString.equals("") ||
                positionString.equals("") || workYearString.equals("") ||
                emailString.equals("");

        return bolResult;
    }


    private void bindWidget() {

        nameEditText = (EditText) findViewById(R.id.editText3);
        idCardEditText = (EditText) findViewById(R.id.editText4);
        positionEditText = (EditText) findViewById(R.id.editText6);
        workYearEditText = (EditText) findViewById(R.id.editText7);
        emailEditText = (EditText) findViewById(R.id.editText8);
        provinceSpinner = (Spinner) findViewById(R.id.spinner);

    }   // bindWidget

}   // Main Class
