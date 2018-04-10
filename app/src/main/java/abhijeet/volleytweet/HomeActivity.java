package abhijeet.volleytweet;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity {
    Button logout,getlist;
    String flag="yes";
    SQLiteDatabase myDataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        logout=(Button)findViewById(R.id.logout);
        getlist=(Button)findViewById(R.id.getList);
        myDataBase = openOrCreateDatabase("person.db", Context.MODE_PRIVATE, null);
        myDataBase.execSQL("CREATE TABLE IF NOT EXISTS COINS (coinName TEXT,coinHandle TEXT,coinSymbol TEXT,tweet TEXT)");
        final AlertDialog.Builder builder2=new AlertDialog.Builder(this);
        builder2.setTitle("Select option!!");
        builder2.setMessage("Do you want latest data or last stored data?");
        builder2.setCancelable(true);
        builder2.setPositiveButton("Latest", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                flag="yes";
                Intent intent=new Intent(HomeActivity.this,DisplayList.class);
                intent.putExtra("check",flag);
                startActivity(intent);
            }
        });
        builder2.setNegativeButton("Stored", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                flag="no";
                Intent intent=new Intent(HomeActivity.this,DisplayList.class);
                intent.putExtra("check",flag);
                startActivity(intent);
            }
        });
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("No data found!! Do you want to download data now?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    Intent intent=new Intent(HomeActivity.this,DisplayList.class);
                    intent.putExtra("check",flag);
                    startActivity(intent);
                    }
                });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //  Action for 'NO' Button
                dialog.cancel();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"logged out!!",Toast.LENGTH_SHORT).show();
                SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();
                finish();
            }
        });
        getlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor=myDataBase.rawQuery("SELECT * FROM COINS",null);
                if (cursor.getCount()==0){
                    AlertDialog alert = builder.create();
                    alert.setTitle("Select option!");
                    alert.show();
                }
                else {
                    AlertDialog alertDialog=builder2.create();
                    alertDialog.show();
                }
            }
        });
    }
}
