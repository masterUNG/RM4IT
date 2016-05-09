package appewtc.masterung.rm4it;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    //Explicit
    private ImageView hub1ImageView, hub2ImageView, hub3ImageView,
            hub4ImageView, hub5ImageView;
    private String[] resultStrings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //Bind Widget
        bindWidget();

        //Receive from Intent
        resultStrings = getIntent().getStringArrayExtra("Result");

        //Image Controller
        imageController();

    }   // Main Method

    private void imageController() {
        hub1ImageView.setOnClickListener(this);
        hub2ImageView.setOnClickListener(this);
        hub3ImageView.setOnClickListener(this);
        hub4ImageView.setOnClickListener(this);
        hub5ImageView.setOnClickListener(this);

    }

    private void bindWidget() {
        hub1ImageView = (ImageView) findViewById(R.id.imageButton2);
        hub2ImageView = (ImageView) findViewById(R.id.imageButton6);
        hub3ImageView = (ImageView) findViewById(R.id.imageButton7);
        hub4ImageView = (ImageView) findViewById(R.id.imageButton8);
        hub5ImageView = (ImageView) findViewById(R.id.imageButton9);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.imageButton2:

                Intent intent = new Intent(SignInActivity.this, InformationActivity.class);
                intent.putExtra("Result", resultStrings);
                startActivity(intent);

                break;
            case R.id.imageButton6:

                Intent intent1 = new Intent(SignInActivity.this, ChooseRisk.class);
                intent1.putExtra("User", resultStrings);
                startActivity(intent1);

                break;
            case R.id.imageButton7:
                break;
            case R.id.imageButton8:
                break;
            case R.id.imageButton9:
                finish();
                break;

        } // switch

    }   // onClick

}   // Main Class
