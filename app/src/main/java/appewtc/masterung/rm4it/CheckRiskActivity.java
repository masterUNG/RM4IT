package appewtc.masterung.rm4it;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class CheckRiskActivity extends AppCompatActivity {

    //Explicit
    private ListView myCheckListView;
    private String[] listCheckStrings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_risk);

        //Bind Widget
        bindWidget();

       //Create ListView
        createListview(1);

    }   // Main Method

    private void createListview(int intIndex) {

        listCheckStrings = readAllData(intIndex);
        Log.d("test3", "list = " + listCheckStrings.length);

        MyAdapter myAdapter = new MyAdapter(CheckRiskActivity.this, listCheckStrings);
        myCheckListView.setAdapter(myAdapter);

    }   //createListview

    private String[] readAllData(int intIndex) {

        String[] resultStrings = null;

        MyData myData = new MyData();

        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
//        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + myData.nameTableStrings[intIndex], null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + "correctTABLE", null);
        cursor.moveToFirst();
        resultStrings = new String[cursor.getCount()];
        for (int i = 0; i < cursor.getCount(); i++) {
            resultStrings[i] = cursor.getString(1);
            cursor.moveToNext();
        }
        cursor.close();
        return resultStrings;
    }   // readAllData


    private void bindWidget() {
        myCheckListView = (ListView) findViewById(R.id.listView);
    }

}   // Main Class
