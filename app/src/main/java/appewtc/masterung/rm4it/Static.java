package appewtc.masterung.rm4it;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
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

public class Static extends AppCompatActivity {

    private static final String urlPHP = "http://swiftcodingthai.com/rm4it/get_check_where.php";
    private String[] userStrings;
    private String jsonString;
    private double rick1AnInt, rick2AnInt, rick3AnInt, rick4AnInt, rick5AnInt, rick6AnInt,
            rick7AnInt, rick8AnInt, rick9AnInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static);

        userStrings = getIntent().getStringArrayExtra("User");
        Log.d("23AugV1", "NameUser ==> " + userStrings[3]);

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormEncodingBuilder()
                .add("isAdd", "true")
                .add("NameUser", userStrings[3])
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
                jsonString = response.body().string();
                Log.d("23AugV1", "JSON ==> " + jsonString);
                findDataPoint(jsonString);
            }
        });


    }   // Main Method

    private void findDataPoint(String jsonString) {

        try {

            JSONArray jsonArray = new JSONArray(jsonString);
            JSONObject jsonObject = jsonArray.getJSONObject(jsonArray.length()-1);

            rick1AnInt = Double.parseDouble(jsonObject.getString("Risk1"))/26*100;
            rick2AnInt = Double.parseDouble(jsonObject.getString("Risk2"))/16*100;
            rick3AnInt = Double.parseDouble(jsonObject.getString("Risk3"))/8*100;
            rick4AnInt = Double.parseDouble(jsonObject.getString("Risk4"))/7*100;
            rick5AnInt = Double.parseDouble(jsonObject.getString("Risk5"))/2*100;
            rick6AnInt = Double.parseDouble(jsonObject.getString("Risk6"))/7*100;
            rick7AnInt = Double.parseDouble(jsonObject.getString("Risk7"))/7*100;
            rick8AnInt = Double.parseDouble(jsonObject.getString("Risk8"))/2*100;
            rick9AnInt = Double.parseDouble(jsonObject.getString("Risk9"))/16*100;

            Log.d("23AugV2", "rick1 = " + rick1AnInt);
            Log.d("23AugV2", "rick2 = " + rick2AnInt);
            Log.d("23AugV2", "rick3 = " + rick3AnInt);
            Log.d("23AugV2", "rick4 = " + rick4AnInt);
            Log.d("23AugV2", "rick5 = " + rick5AnInt);
            Log.d("23AugV2", "rick6 = " + rick6AnInt);
            Log.d("23AugV2", "rick7 = " + rick7AnInt);
            Log.d("23AugV2", "rick8 = " + rick8AnInt);
            Log.d("23AugV2", "rick9 = " + rick9AnInt);

            showGraph(rick1AnInt, rick2AnInt, rick3AnInt, rick4AnInt,
                    rick5AnInt, rick6AnInt, rick7AnInt, rick8AnInt, rick9AnInt);

        } catch (Exception e) {
            Log.d("23AugV2", "e ==> " + e.toString());
        }


    }   // findDataPoint

    private void showGraph(double rick1AnInt, double rick2AnInt, double rick3AnInt,
                           double rick4AnInt, double rick5AnInt, double rick6AnInt,
                           double rick7AnInt, double rick8AnInt, double rick9AnInt) {



        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(0, 0.5),
                new DataPoint(1, rick1AnInt),
                new DataPoint(2, rick2AnInt),
                new DataPoint(3, rick3AnInt),
                new DataPoint(4, rick4AnInt),
                new DataPoint(5, rick5AnInt),
                new DataPoint(6, rick6AnInt),
                new DataPoint(7, rick7AnInt),
                new DataPoint(8, rick8AnInt),
                new DataPoint(9, rick9AnInt)

        });

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(10);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(100);
        graph.setTitle("Rick");


        graph.addSeries(series);


    }

}   // Main Class
