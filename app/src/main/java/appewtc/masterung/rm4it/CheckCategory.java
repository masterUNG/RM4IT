package appewtc.masterung.rm4it;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class CheckCategory extends AppCompatActivity {

    //Explicit
    private String categoryString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_category);

        categoryString = getIntent().getStringExtra("Category");
        Log.d("Chok", "Catetory ==> " + categoryString);

        //Create ListView
        createListView();

    }   // Main Method

    private void createListView() {

        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + categoryString, null);
        cursor.moveToFirst();
        String[] titleStrings = new String[cursor.getCount()];
        for (int i=0;i<cursor.getCount();i++) {
            titleStrings[i] = cursor.getString(1);
            cursor.moveToNext();
        }   // for
        cursor.close();

        CheckAdapter checkAdapter = new CheckAdapter(this, titleStrings);
        ListView listView = (ListView) findViewById(R.id.listView2);
        listView.setAdapter(checkAdapter);


    }   // createListView

}   // Main Class
