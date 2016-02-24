package appewtc.masterung.rm4it;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    //Explicit
    private MyManage myManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Request database
        myManage = new MyManage(this);

        //Tester Add Value
        //testerAdd();

        //Delete All SQLite
        deleteAllSQLite();

    }   // Main Method

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
