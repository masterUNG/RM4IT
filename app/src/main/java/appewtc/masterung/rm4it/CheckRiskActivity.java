package appewtc.masterung.rm4it;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CheckRiskActivity extends Activity {

    //Explicit
    private MyCustomAdapter dataAdapter = null;
    private String[] userStrings, titleCheckStrings;
    private String riskTABLEString, riskString, dateString;
    private TextView titleTextView, nameTextView,
            provinceTextView, dateTextView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_risk);

        //Bind Widget
        bindWidget();

        //Get Value from Intent
        userStrings = getIntent().getStringArrayExtra("User");
        riskTABLEString = getIntent().getStringExtra("rickTABLE");
        riskString = getIntent().getStringExtra("risk");

        //Show View
        titleTextView.setText(riskString);
        nameTextView.setText("ชื่อของผู้บันทึก = " + userStrings[3]);
        provinceTextView.setText("จังหวัด " + userStrings[5]);

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        dateString = dateFormat.format(date);
        dateTextView.setText(dateString);

        //Read SQLite
        readSQLite();



        // Generate list View from ArrayList
        displayListView();

        checkButtonClick();

    }   // Main Method

    private void readSQLite() {

        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + riskTABLEString, null);
        cursor.moveToFirst();

        titleCheckStrings = new String[cursor.getCount()];
        for (int i=0;i<cursor.getCount();i++) {

            titleCheckStrings[i] = cursor.getString(cursor.getColumnIndex("Name"));
            cursor.moveToNext();

        }   // for
        cursor.close();


    }   // readSQLite

    private void bindWidget() {

        titleTextView = (TextView) findViewById(R.id.txtRiskTitle);
        nameTextView = (TextView) findViewById(R.id.textView28);
        provinceTextView = (TextView) findViewById(R.id.textView29);
        dateTextView = (TextView) findViewById(R.id.textView30);

    }

    private void displayListView() {

        // Array list of countries
        ArrayList<States> stateList = new ArrayList<States>();

        for (int i=0;i<titleCheckStrings.length;i++) {

            States states = new States(titleCheckStrings[i], false);
            stateList.add(states);

        }   // for

        // create an ArrayAdaptar from the String Array
        dataAdapter = new MyCustomAdapter(this, R.layout.state_info, stateList);
        ListView listView = (ListView) findViewById(R.id.listView1);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);

        listView.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                States state = (States) parent.getItemAtPosition(position);

            }
        });
    }

    private class MyCustomAdapter extends ArrayAdapter<States> {

        private ArrayList<States> stateList;

        public MyCustomAdapter(Context context, int textViewResourceId,

                               ArrayList<States> stateList) {
            super(context, textViewResourceId, stateList);
            this.stateList = new ArrayList<States>();
            this.stateList.addAll(stateList);
        }

        private class ViewHolder {
            TextView code;
            CheckBox name;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;

            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {

                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                convertView = vi.inflate(R.layout.state_info, null);

                holder = new ViewHolder();

                holder.name = (CheckBox) convertView
                        .findViewById(R.id.checkBox1);

                convertView.setTag(holder);

                holder.name.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        States _state = (States) cb.getTag();

                        _state.setSelected(cb.isChecked());
                    }
                });

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            States state = stateList.get(position);

//            holder.code.setText(" (" + state.getCode() + ")");
            holder.name.setText(state.getName());
            holder.name.setChecked(state.isSelected());

            holder.name.setTag(state);

            return convertView;
        }

    }

    private void checkButtonClick() {

        Button myButton = (Button) findViewById(R.id.findSelected);

        myButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                StringBuffer responseText = new StringBuffer();
                responseText.append("Selected Countries are...\n");
                int scoreAnInt = 0;

                ArrayList<States> stateList = dataAdapter.stateList;

                for (int i = 0; i < stateList.size(); i++) {
                    States state = stateList.get(i);

                    if (state.isSelected()) {
                        responseText.append("\n" + state.getName());
                        scoreAnInt += 1;
                    }   // if
                }   // for

                Toast.makeText(getApplicationContext(), "Score = " + Integer.toString(scoreAnInt),
                        Toast.LENGTH_SHORT).show();

                editSQLite(scoreAnInt);

            }
        });
    }

    private void editSQLite(int scoreAnInt) {

        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        ContentValues contentValues = new ContentValues();
        contentValues.put(riskTABLEString, Integer.toString(scoreAnInt));

        sqLiteDatabase.update("checkTABLE", contentValues, "_id=" + 1, null);

        finish();
    }   // editSQLite

}