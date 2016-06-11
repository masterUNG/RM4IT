package appewtc.masterung.rm4it;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.jibble.simpleftp.SimpleFTP;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CheckRiskActivity extends Activity {

    //Explicit
    private MyCustomAdapter dataAdapter = null;
    private String[] userStrings, titleCheckStrings;
    private String riskTABLEString, riskString, dateString, imageString, nameImage = null,
            lunchString = null, lunchname = null, dinnername = null, dinnerString = null;
    private TextView titleTextView, nameTextView,
            provinceTextView, dateTextView;
    public static final int PICK_IMAGE = 1;
    private int timesAnInt = 0, positionClick;
    private ArrayList<String> stringArrayList = new ArrayList<String>();
    private ArrayList<String> nameImageStringArrayList = new ArrayList<String>();
    private ArrayList<String> rickStringArrayList = new ArrayList<String>();


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

    private String findPath(Uri uri) {
        String imagePath;

        String[] columns = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, columns, null, null, null);

        if (cursor != null) { // case gallery
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            imagePath = cursor.getString(columnIndex);
        } else { // case another app
            imagePath = uri.getPath();

        }
        return imagePath;
    }   // findPath

    public void onActivityResult(int requestCode, int resultCode
            , Intent returndata) {
        String imagepath1, imagepath2, imagepath3;
        Uri uri1, uri2, uri3;


        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {

            Uri imageUri = returndata.getData();
            String msg = "URI: " + imageUri + "\n";

            String imagePath = findPath(imageUri);
            msg += "Path: " + imagePath;
            imageString = imagePath;
            nameImage = imageString.substring(imageString.lastIndexOf("/") + 1);
            uri1 = imageUri;
            //Intent imgp1 = new Intent(imagePath1);
            //imgp1.putExtra("breakfast",imagePath1);

            Log.d("11JuneV1", "imageString ==> " + imageString);
            Log.d("11JuneV1", "nameImage ==> " + nameImage);

            stringArrayList.add(imageString);
            nameImageStringArrayList.add(nameImage);
            rickStringArrayList.add(Integer.toString(positionClick + 1));

            Log.d("11JuneV1", "stringArrayList Lenght ==> " + stringArrayList.size());


            //text.setText(msg);  //แสดง path
            try {
                Bitmap imagedata1 = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));//Media.getBitmap(this.getContentResolver(), imageUri);
                // imageView1.setImageBitmap(imagedata1);

            } catch (FileNotFoundException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();
            }   // try

        }   // if

    }   // Active Result


    @Override
    public void onBackPressed() {
        // super.onBackPressed();
    }

    private void readSQLite() {

        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + riskTABLEString, null);
        cursor.moveToFirst();

        titleCheckStrings = new String[cursor.getCount()];
        for (int i = 0; i < cursor.getCount(); i++) {

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

        for (int i = 0; i < titleCheckStrings.length; i++) {

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
        public View getView(final int position, View convertView, ViewGroup parent) {

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

                        Log.d("11JuneV2", "position Click ==> " + position);
                        positionClick = position;

                        chooseImage();

                    }   // onClick
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

    private void chooseImage() {

        Log.d("11JuneV1", "Click ChooseImage");

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent
                , "Select Picture"), PICK_IMAGE);

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


                String[] myImageStrings = stringArrayList.toArray(new String[stringArrayList.size()]);
                String[] myNameImageStrings = nameImageStringArrayList.toArray(new String[nameImageStringArrayList.size()]);
                String[] myRickStrings = rickStringArrayList.toArray(new String[rickStringArrayList.size()]);

                Log.d("11JuneV1", "myImageString lenght ==> " + myImageStrings.length);
                for (int i = 0; i < myImageStrings.length; i++) {

                    uploadpic(myImageStrings[i]);

                    updateToMySQL("http://swiftcodingthai.com/rm4it/image/" + myNameImageStrings[i],
                            myRickStrings[i]);

                }   // for


            }   // onClick
        });

    }   // checkButton

    private void updateToMySQL(String urlImage, String rickChoose) {

        Log.d("11JuneV2", "Category ==> " + riskTABLEString);
        Log.d("11JuneV2", "IdListName ==> " + rickChoose);
        Log.d("11JuneV2", "Image ==> " + urlImage);
        Log.d("11JuneV2", "idUser ==> " + userStrings[0]);

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormEncodingBuilder()
                .add("isAdd", "true")
                .add("Category", riskTABLEString)
                .add("IdListName", rickChoose)
                .add("Image", urlImage)
                .add("IdUser", userStrings[0])
                .build();
        Request.Builder builder = new Request.Builder();
        Request request = builder.url("http://swiftcodingthai.com/rm4it/add_image.php")
                .post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {

            }
        });


    }   // updateToMySQL

    private void uploadpic(String myImageString) {

        try {

            SimpleFTP ftp = new SimpleFTP();

            // Connect to an FTP server on port 21.
            ftp.connect("ftp.swiftcodingthai.com", 21, "rm4it@swiftcodingthai.com", "Abc12345");

            // Set binary mode.
            ftp.bin();

            // Change to a new working directory on the FTP server.
            //ftp.cwd("web");
            ftp.cwd("image");

            // Upload some files.
            /*ftp.stor(new File("webcam.jpg"));
            ftp.stor(new File("comicbot-latest.png"));*/

            // You can also upload from an InputStream, e.g.

            Log.d("11JuneV1", "imageString ==> " + myImageString);
            ftp.stor(new File(myImageString));


            // Quit from the FTP server.
            ftp.disconnect();

            Log.d("11JuneV1", "upload Finish " + myImageString);

        } catch (Exception e) {
            Log.d("11JuneV1", "error e ==> " + e.toString());
            Log.d("11JuneV1", "imageString ate error e==> " + myImageString);
        }


    }   // uploadpic

    private void editSQLite(int scoreAnInt) {

        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        ContentValues contentValues = new ContentValues();
        contentValues.put(riskTABLEString, Integer.toString(scoreAnInt));

        sqLiteDatabase.update("checkTABLE", contentValues, "_id=" + 1, null);

        finish();
    }   // editSQLite

}