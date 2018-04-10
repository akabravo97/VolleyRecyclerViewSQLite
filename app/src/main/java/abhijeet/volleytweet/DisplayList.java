package abhijeet.volleytweet;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class DisplayList extends AppCompatActivity {
    SQLiteDatabase myDataBase;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    Context context;
    //String DB_PATH=getApplicationContext().getFilesDir().getPath() + context.getPackageName() + "/databases/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_list);
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Connecting");
        progress.setMessage("Please wait while we load data...");
        Handler pdCanceller = new Handler();
        progress.show();
        final ArrayList<Info> infos=new ArrayList<>();
        recyclerView=(RecyclerView)findViewById(R.id.recycleView);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        myDataBase = openOrCreateDatabase("person.db", Context.MODE_PRIVATE, null);
        Intent intent=getIntent();
        String flag=intent.getStringExtra("check");
        Log.d("flag",flag);
        if (flag.equals("yes")){
         //do nothing yet
            BackgroundTask backgroundTask=new BackgroundTask(DisplayList.this);
            backgroundTask.getList(new BackgroundTask.arrayCallBack() {
                @Override
                public void onSuccess(ArrayList<Info> infos1) {
                    adapter=new InfoAdapter(infos1);
                    recyclerView.setAdapter(adapter);
                    for (int i=0;i<infos1.size();i++){
                        try{
                        myDataBase.execSQL("INSERT INTO COINS (coinName,coinHandle,CoinSymbol,tweet) VALUES('" + infos1.get(i).getCoin_name() + "','" + infos1.get(i).getCoin_handle() + "','" + infos1.get(i).getCoin_symbol() + "','" + infos1.get(i).getTweets() + "');");
                    }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFail(String msg) {
                    Toast.makeText(getApplicationContext(),"Error!!",Toast.LENGTH_SHORT).show();
                }
            });
            Runnable progressRunnable = new Runnable() {

                @Override
                public void run() {
                    progress.cancel();
                }
            };
            pdCanceller.postDelayed(progressRunnable, 20000);
        }
        else if (flag.equals("no")){
                    Cursor cursor=myDataBase.rawQuery("SELECT * FROM COINS",null);
                    if (cursor.getCount()>0){
                        if (cursor.moveToFirst()){
                            do{
                                String coin_name=cursor.getString(cursor.getColumnIndex("coinName"));
                                String coin_handle=cursor.getString(cursor.getColumnIndex("coinHandle"));
                                String coin_symbol=cursor.getString(cursor.getColumnIndex("coinSymbol"));
                                String tweet=cursor.getString(cursor.getColumnIndex("tweet"));
                                Info info=new Info(coin_name,coin_symbol,coin_handle,tweet);
                                infos.add(info);
                            }while (cursor.moveToNext());
                        }
                    }
                    adapter=new InfoAdapter(infos);
                    recyclerView.setAdapter(adapter);
            Runnable progressRunnable = new Runnable() {

                @Override
                public void run() {
                    progress.cancel();
                }
            };
            pdCanceller.postDelayed(progressRunnable, 10000);
        }

    }

}
