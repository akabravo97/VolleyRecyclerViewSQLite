package abhijeet.volleytweet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button login,signup;
    EditText username,password;
    SQLiteDatabase myDataBase;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        login=(Button)findViewById(R.id.loginButton);
        signup=(Button)findViewById(R.id.signUpButton);
        username=(EditText) findViewById(R.id.userName);
        password=(EditText) findViewById(R.id.userPass);
        myDataBase = openOrCreateDatabase("person.db", Context.MODE_PRIVATE, null);
        myDataBase.execSQL("CREATE TABLE IF NOT EXISTS INFO (userName TEXT PRIMARY KEY,password TEXT)");
        if(sharedpreferences.contains("username"))
        {
            Intent intent=new Intent(MainActivity.this,HomeActivity.class);
            startActivity(intent);
        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userVal=String.valueOf(username.getText());
                String passVal=String.valueOf(password.getText());
                if(userVal.isEmpty() || passVal.isEmpty()){
                    Toast.makeText(getApplicationContext(), "All Fields are Mandatory", Toast.LENGTH_SHORT).show();
                }
                Cursor cursor=myDataBase.rawQuery("SELECT * FROM INFO WHERE userName = '"+userVal+"';",null);
                if (cursor!=null){
                    if(cursor.getCount()>0){
                        if(cursor.moveToFirst()){
                            String dpass=cursor.getString(cursor.getColumnIndex("password"));
                            if(dpass.equals(passVal)){
                                Toast.makeText(getApplicationContext(), "Login Successful.", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(MainActivity.this,HomeActivity.class);
                                startActivity(intent);
                                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString("username",userVal);
                                editor.commit();
                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Incorrect Password!!",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"user not found!!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(MainActivity.this,SignupActivity.class);
                startActivity(in);
            }
        });
    }
}
