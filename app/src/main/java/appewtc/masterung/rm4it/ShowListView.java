package appewtc.masterung.rm4it;

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
    private String[] userStrings, iconStrings, titleStrings, detailStrings;
    private static final String urlPHP = "http://swiftcodingthai.com/rm4it/get_addList_where_IdUser.php";
    private String JSONString;


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

            for (int i=0;i<jsonArray.length();i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                iconStrings[i] = jsonObject.getString("Image");
                titleStrings[i] = jsonObject.getString("Category");
                detailStrings[i] = jsonObject.getString("IdListName");

                Log.d("23AugV3", "Title ==> " + i + " = " + titleStrings[i]);
                Log.d("23AugV3", "Image ==> " + i + " = " + iconStrings[i]);
                Log.d("23AugV3", "Detail ==> " + i + " = " + detailStrings[i]);

            }   //for

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    ShowAdapter showAdapter = new ShowAdapter(ShowListView.this, iconStrings, titleStrings, detailStrings);
                    listView.setAdapter(showAdapter);

                }
            });

        } catch (Exception e) {
            Log.d("23AugV4", "e ==> " + e.toString());
        }

    }   // createListView

}   // Main Class
