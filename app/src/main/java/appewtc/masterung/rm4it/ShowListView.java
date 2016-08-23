package appewtc.masterung.rm4it;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class ShowListView extends AppCompatActivity {

    private ListView listView;
    private String[] userStrings, iconStrings, titleStrings, detailStrings, detailLongStrings;
    private static final String urlPHP = "http://swiftcodingthai.com/rm4it/get_addList_where_IdUser.php";
    private String JSONString, resultString, myResultString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list_view);

        listView = (ListView) findViewById(R.id.listView);

        userStrings = getIntent().getStringArrayExtra("User");

        //Get Array
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormEncodingBuilder()
                .add("isAdd", "true")
                .add("IdUser", userStrings[0])
                .build();
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(urlPHP).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                JSONString = response.body().string();
                Log.d("23AugV3", "JSON ==> " + JSONString);
                createListView(JSONString);
            }
        });



    }   // Main Method

    private void createListView(String jsonString) {

        try {

            JSONArray jsonArray = new JSONArray(jsonString);

            iconStrings = new String[jsonArray.length()];
            titleStrings = new String[jsonArray.length()];
            detailStrings = new String[jsonArray.length()];
            detailLongStrings = new String[jsonArray.length()];

            for (int i=0;i<jsonArray.length();i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                iconStrings[i] = jsonObject.getString("Image");

                String[] strTitle = jsonObject.getString("Category").split("T");

                titleStrings[i] = strTitle[0];
                detailStrings[i] = jsonObject.getString("IdListName");

               //detailLongStrings[i] = findDetail(titleStrings[i], detailStrings[i]);

               detailLongStrings[i] = findDtailBySQLite(titleStrings[i], detailStrings[i]);


                Log.d("23AugV6", "detailShout ==> " + i + " = " + titleStrings[i]);
                Log.d("23AugV6", "detailLong ==> " + detailLongStrings[i]);

                Log.d("23AugV4", "test ==> " + i + " = " + detailLongStrings[i]);

                Log.d("23AugV3", "Title ==> " + i + " = " + titleStrings[i]);
                Log.d("23AugV3", "Image ==> " + i + " = " + iconStrings[i]);
                Log.d("23AugV3", "Detail ==> " + i + " = " + detailStrings[i]);

            }   //for

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    ShowAdapter showAdapter = new ShowAdapter(ShowListView.this, iconStrings, titleStrings, detailLongStrings);
                    listView.setAdapter(showAdapter);

                }
            });

        } catch (Exception e) {
            Log.d("23AugV4", "e ==> " + e.toString());
        }

    }   // createListView

    private String findDtailBySQLite(String titleString, String detailString) {

        String strResult = null;
        String strTable = titleString + "TABLE";
        Log.d("23AugV6", "strTable ==> " + strTable);
        Log.d("23AugV6", "Name ==> " + detailString);

        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM correctTABLE WHERE _id = " + "'" + detailString + "'", null);
        cursor.moveToFirst();
        Log.d("23AugV6", "LengthCursor ==> " + cursor.getCount());
        strResult = cursor.getString(cursor.getColumnIndex("Name"));
        Log.d("23AugV6", "strResult ==> " + strResult);

        return strResult;
    }

    private String findDetail(String titleString, String detailString) {

        String urlPHP2 = "http://swiftcodingthai.com/rm4it/get_detail_where_Id.php";


        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormEncodingBuilder()
                .add("isAdd", "true")
                .add("Table", titleString)
                .add("id", detailString)
                .build();
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(urlPHP2).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.d("23AugV5", "Result e ==> " + e.toString());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resultString = response.body().string();
                Log.d("23AugV5", "Result ==> " + resultString);

                try {

                    JSONArray jsonArray = new JSONArray(resultString);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    myResultString = jsonObject.getString("Name");


                } catch (Exception e) {
                    e.toString();
                }

            }
        });

        return myResultString;
    }

}   // Main Class
