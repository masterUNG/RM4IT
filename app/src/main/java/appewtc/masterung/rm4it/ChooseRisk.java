package appewtc.masterung.rm4it;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChooseRisk extends AppCompatActivity implements View.OnClickListener {

    //Explicit
    private String[] nameTableStrings, userStrings;
    private Button risk1Button, risk2Button,
            risk3Button, risk4Button, risk5Button,
            risk6Button, risk7Button, risk8Button, risk9Button;
    private ImageView risk1ImageView, risk2ImageView, risk3ImageView,
            risk4ImageView, risk5ImageView, risk6ImageView, risk7ImageView,
            risk8ImageView, risk9ImageView;
    private int indexAnInt = 0;
    private String riskString;
    private boolean bolStatus = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_risk);

        //Bind Widget
        bindWidget();

        setupNameTable();

        userStrings = getIntent().getStringArrayExtra("User");

        buttonController();

        //Add SQLite
        if (bolStatus) {

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            String strDate = dateFormat.format(date);

            MyManage myManage = new MyManage(this);
            myManage.addCheckRisk(userStrings[3], userStrings[5], strDate);
        }

    }   // Main Method

    private void buttonController() {
        risk1Button.setOnClickListener(this);
        risk2Button.setOnClickListener(this);
        risk3Button.setOnClickListener(this);
        risk4Button.setOnClickListener(this);
        risk5Button.setOnClickListener(this);
        risk6Button.setOnClickListener(this);
        risk7Button.setOnClickListener(this);
        risk8Button.setOnClickListener(this);
        risk9Button.setOnClickListener(this);
    }

    private void bindWidget() {

        risk1Button = (Button) findViewById(R.id.button8);
        risk2Button = (Button) findViewById(R.id.button9);
        risk3Button = (Button) findViewById(R.id.button10);
        risk4Button = (Button) findViewById(R.id.button11);
        risk5Button = (Button) findViewById(R.id.button12);
        risk6Button = (Button) findViewById(R.id.button13);
        risk7Button = (Button) findViewById(R.id.button14);
        risk8Button = (Button) findViewById(R.id.button15);
        risk9Button = (Button) findViewById(R.id.button16);

        risk1ImageView = (ImageView) findViewById(R.id.imageView3);
        risk2ImageView = (ImageView) findViewById(R.id.imageView4);
        risk3ImageView = (ImageView) findViewById(R.id.imageView5);
        risk4ImageView = (ImageView) findViewById(R.id.imageView6);
        risk5ImageView = (ImageView) findViewById(R.id.imageView7);
        risk6ImageView = (ImageView) findViewById(R.id.imageView8);
        risk7ImageView = (ImageView) findViewById(R.id.imageView9);
        risk8ImageView = (ImageView) findViewById(R.id.imageView10);
        risk9ImageView = (ImageView) findViewById(R.id.imageView11);

    }

    private void setupNameTable() {

        nameTableStrings = new String[9];

        nameTableStrings[0] = "correctTABLE";
        nameTableStrings[1] = "environmentTABLE";
        nameTableStrings[2] = "governanceTABLE";
        nameTableStrings[3] = "internetTABLE";
        nameTableStrings[4] = "moneyTABLE";
        nameTableStrings[5] = "network_intrusionTABLE";
        nameTableStrings[6] = "server_networkTABLE";
        nameTableStrings[7] = "virusTABLE";
        nameTableStrings[8] = "wiless_networkTABLE";
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.button8:
                indexAnInt = 0;
                risk1ImageView.setImageResource(R.drawable.mytrue);
                riskString = getResources().getString(R.string.risk1);
                break;
            case R.id.button9:
                indexAnInt = 1;
                risk2ImageView.setImageResource(R.drawable.mytrue);
                riskString = getResources().getString(R.string.risk2);
                break;
            case R.id.button10:
                indexAnInt = 2;
                risk3ImageView.setImageResource(R.drawable.mytrue);
                riskString = getResources().getString(R.string.risk3);
                break;
            case R.id.button11:
                indexAnInt = 3;
                risk4ImageView.setImageResource(R.drawable.mytrue);
                riskString = getResources().getString(R.string.risk4);
                break;
            case R.id.button12:
                indexAnInt = 4;
                risk5ImageView.setImageResource(R.drawable.mytrue);
                riskString = getResources().getString(R.string.risk5);
                break;
            case R.id.button13:
                indexAnInt = 5;
                risk6ImageView.setImageResource(R.drawable.mytrue);
                riskString = getResources().getString(R.string.risk6);
                break;
            case R.id.button14:
                indexAnInt = 6;
                risk7ImageView.setImageResource(R.drawable.mytrue);
                riskString = getResources().getString(R.string.risk7);
                break;
            case R.id.button15:
                indexAnInt = 7;
                risk8ImageView.setImageResource(R.drawable.mytrue);
                riskString = getResources().getString(R.string.risk8);
                break;
            case R.id.button16:
                indexAnInt = 8;
                risk9ImageView.setImageResource(R.drawable.mytrue);
                riskString = getResources().getString(R.string.risk9);
                break;

        }   // switch

        bolStatus = false;

        Intent intent = new Intent(ChooseRisk.this, CheckRiskActivity.class);
        intent.putExtra("User", userStrings);
        intent.putExtra("rickTABLE", nameTableStrings[indexAnInt]);
        intent.putExtra("risk", riskString);
        startActivity(intent);

    }   // onClick

}   // Main Class
