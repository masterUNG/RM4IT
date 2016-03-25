package appewtc.masterung.rm4it;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class InformationActivity extends AppCompatActivity {

    //Explicit
    private TextView nameTextView, positionTextView,
            ageWorksTextView, provinceTextView, emailTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        //Bind Widget
        nameTextView = (TextView) findViewById(R.id.textView12);
        positionTextView = (TextView) findViewById(R.id.textView13);
        ageWorksTextView = (TextView) findViewById(R.id.textView23);
        provinceTextView = (TextView) findViewById(R.id.textView24);
        emailTextView = (TextView) findViewById(R.id.textView25);

        //Receive from Intent
        String[] resultStrings = getIntent().getStringArrayExtra("Result");

        //Show View
        nameTextView.setText(resultStrings[3]);
        positionTextView.setText(resultStrings[6]);
        ageWorksTextView.setText(resultStrings[7] + " ปี");
        provinceTextView.setText(resultStrings[5]);
        emailTextView.setText(resultStrings[8]);

    }      //Main Method

}   // Main Class
