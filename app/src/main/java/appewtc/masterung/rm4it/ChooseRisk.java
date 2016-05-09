package appewtc.masterung.rm4it;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

public class ChooseRisk extends AppCompatActivity {

    //Explicit
    private String[] nameTableStrings;
    private Button risk1Button, risk2Button,
            risk3Button, risk4Button, risk5Button,
            risk6Button, risk7Button, risk8Button, risk9Button;
    private ImageView risk1ImageView, risk2ImageView, risk3ImageView,
            risk4ImageView, risk5ImageView, risk6ImageView, risk7ImageView,
            risk8ImageView, risk9ImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_risk);

        //Bind Widget
        bindWidget();

        setupNameTable();

    }   // Main Method

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


}   // Main Class
