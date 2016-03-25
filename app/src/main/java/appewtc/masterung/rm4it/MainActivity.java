package appewtc.masterung.rm4it;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    //Explicit
    private MyManage myManage;
    private EditText userEditText, passwordEditText;
    private String userString, passwordString;
    private String[] resultStrings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind Widget
        bindWidget();

        //Request database
        myManage = new MyManage(this);

        //Tester Add Value
        //testerAdd();

        //Delete All SQLite
        deleteAllSQLite();

        //Syn JSON to SQLite
        synJSONtoSQLite();

    }   // Main Method

    private void bindWidget() {
        userEditText = (EditText) findViewById(R.id.editText);
        passwordEditText = (EditText) findViewById(R.id.editText2);
    }

    public void clickSignInMain(View view) {

        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        //Check Space
        if (userString.equals("") || passwordString.equals("")) {
            //Have Space
            MyAlertDialog myAlertDialog = new MyAlertDialog();
            myAlertDialog.MyDialog(MainActivity.this,
                    "มีช่องว่าง", "กรุณากรอกให้ครบทุกช่อง คะ");
        } else {
            //No Space
            checkUser();
        }


    }   // clickSignInMain

    private void checkUser() {

        try {

            SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                    MODE_PRIVATE, null);
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM userTABLE WHERE User = " + "'" + userString + "'", null);
            cursor.moveToFirst();
            resultStrings = new String[cursor.getColumnCount()];
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                resultStrings[i] = cursor.getString(i);
            }   //for

            //Check Password
            if (passwordString.equals(resultStrings[2])) {
                //Password True
                Toast.makeText(MainActivity.this, "ยินดีต้อนรับ " + resultStrings[3],
                        Toast.LENGTH_SHORT).show();
            } else {
                //Password False
                MyAlertDialog myAlertDialog = new MyAlertDialog();
                myAlertDialog.MyDialog(MainActivity.this, "Password ผิด",
                        "กรุณาพิมพ์ ใหม่ Password ผิด");
            }


        } catch (Exception e) {
            MyAlertDialog myAlertDialog = new MyAlertDialog();
            myAlertDialog.MyDialog(MainActivity.this, "ไม่มี User",
                    "ไม่มี " + userString + " ใน ฐานข้อมูลของเรา");
        }

    }   // checkUser


    @Override
    protected void onRestart() {
        super.onRestart();

        deleteAllSQLite();
        synJSONtoSQLite();

    }

    private void synJSONtoSQLite() {

        //Connected Http
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy
                .Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        //1 Create Input Stream
        InputStream inputStream = null;
        String urlJSON = "http://swiftcodingthai.com/rm4it/php_get_user_master.php";
        String tag = "Rm4it";
        try {

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(urlJSON);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

        } catch (Exception e) {
            Log.d(tag, "Input ==> " + e.toString());
        }

        //2 Create JSON String
        String strJSON = null;
        try {

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();
            String strLine = null;

            while ((strLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(strLine);
            }   // while
            inputStream.close();
            strJSON = stringBuilder.toString();

        } catch (Exception e) {
            Log.d(tag, "strJSON ==> " + e.toString());
        }


        //3 Update SQLite
        try {

            JSONArray jsonArray = new JSONArray(strJSON);
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String strUser = jsonObject.getString(MyManage.column_User);
                String strPassword = jsonObject.getString(MyManage.column_Password);
                String strName = jsonObject.getString(MyManage.column_Name);
                String strIDcard = jsonObject.getString(MyManage.column_ID_card);
                String strProvince = jsonObject.getString(MyManage.column_Province);
                String strPosition = jsonObject.getString(MyManage.column_Position);
                String strWorkYear = jsonObject.getString(MyManage.column_Work_Year);
                String strEmail = jsonObject.getString(MyManage.column_Email);

                myManage.addUser(strUser, strPassword, strName, strIDcard, strProvince,
                        strPosition, strWorkYear, strEmail);

            }   //for

        } catch (Exception e) {
            Log.d(tag, "Update ==> " + e.toString());
        }


    }   // synJSONtoSQLite

    private void deleteAllSQLite() {

        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        sqLiteDatabase.delete(MyManage.table_user, null, null);

    }   // deleteAllSQLite

    private void testerAdd() {

        myManage.addUser("user", "pass", "name", "123", "BKK", "position", "3", "abc@gmail.com");

    }   // testerAdd

    public void clickSignUp(View view) {
        startActivity(new Intent(MainActivity.this, SignUpActivity.class));
    }


}   // Main Class
