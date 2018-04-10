package abhijeet.volleytweet;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by MAHE on 10-Apr-18.
 */

public class BackgroundTask {
    public interface arrayCallBack {
        void onSuccess(ArrayList<Info> infos);

        void onFail(String msg);
    }
    Context context;
    String url="http://165.227.98.190/tweets/";
    ArrayList<Info> arrayList=new ArrayList<>();
    public BackgroundTask(Context context){
        this.context=context;
    }
    public ArrayList<Info> getList(final arrayCallBack onCallBack){
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray=response.getJSONArray("tweets");
                    for(int i =0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        Info info1=new Info(jsonObject.getString("coin_name"),jsonObject.getString("coin_symbol"),jsonObject.getString("coin_handle"),jsonObject.getString("tweet"));
                        arrayList.add(info1);
                    }
                    onCallBack.onSuccess(arrayList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Error!!",Toast.LENGTH_SHORT).show();
                error.printStackTrace();
                onCallBack.onFail(error.toString());

            }
        });
        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
        return arrayList;

    }
}