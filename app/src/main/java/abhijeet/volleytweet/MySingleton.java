package abhijeet.volleytweet;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by MAHE on 10-Apr-18.
 */

public class MySingleton {
    private static  MySingleton mySingleton;
    private RequestQueue requestQueue;
    private static Context mcontext;


    private MySingleton(Context context){
        mcontext=context;
        requestQueue=getRequestQueue();
    }
    public RequestQueue getRequestQueue(){
        if (requestQueue==null){
            requestQueue= Volley.newRequestQueue(mcontext.getApplicationContext());
        }
        return requestQueue;
    }
    public static synchronized MySingleton getInstance(Context context){
        if (mySingleton==null){
            mySingleton=new MySingleton(context);
        }
        return mySingleton;
    }
    public<T> void addToRequestQueue(Request<T> request){
        requestQueue.add(request);
    }
}
