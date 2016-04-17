package appewtc.masterung.rm4it;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class CheckRiskActivity extends AppCompatActivity {

    //Explicit
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_risk);

        //Bind Widget
        listView = (ListView) findViewById(R.id.listView);

        //Create ListView
        createListView();


    }   // Main Method

    private void createListView() {

        String[] titleStrings = new String[9];
        titleStrings[0] = "Group1";
        titleStrings[1] = "Group2";
        titleStrings[2] = "Group3";
        titleStrings[3] = "Group4";
        titleStrings[4] = "Group5";
        titleStrings[5] = "Group6";
        titleStrings[6] = "Group7";
        titleStrings[7] = "Group8";
        titleStrings[8] = "Group9";

        final String[] categoryStrings = getResources().getStringArray(R.array.category);

        MyAdapter myAdapter = new MyAdapter(this, titleStrings);
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Log.d("17April", "Click ListView");

                Intent intent = new Intent(CheckRiskActivity.this, CheckCategory.class);
                intent.putExtra("Category", categoryStrings[i]);
                startActivity(intent);


            }   // onItemClick
        });


    }   // createListview


}   // Main Class
