package abhijeet.volleytweet;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {
    EditText userid;
    EditText password;
    EditText repassword;
    EditText name;
    EditText email;
    SQLiteDatabase myDataBase;
    Button signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        userid = (EditText) findViewById(R.id.userId);
        password = (EditText) findViewById(R.id.userPass);
        repassword = (EditText) findViewById(R.id.userRePass);
        name = (EditText) findViewById(R.id.userName);
        email = (EditText) findViewById(R.id.userEmail);
        signup=(Button)findViewById(R.id.signup);
        myDataBase = openOrCreateDatabase("person.db", Context.MODE_PRIVATE, null);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uid = userid.getText().toString();
                String upass = password.getText().toString();
                String urepass = repassword.getText().toString();
                String uname = name.getText().toString();
                String uemail = email.getText().toString();
                int flag1 = 1,flag2=0;
                if (uid.isEmpty() || upass.isEmpty() || urepass.isEmpty() || uname.isEmpty() || uemail.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "All Fields are mandatory", Toast.LENGTH_SHORT).show();
                    flag1=0;
                }
                if(!upass.equals(urepass)) {
                    Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                    flag1=0;
                }
                if(upass.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password is too short", Toast.LENGTH_SHORT).show();
                    flag1=0;
                }
                if (uname.matches("[0-9]+")) {
                    Toast.makeText(getApplicationContext(), "Name Invalid", Toast.LENGTH_SHORT).show();
                    flag1=0;
                }
                if (!uemail.matches("^[a-zA-z0-9][a-zA-z._0-9]*@[a-zA-z0-9]+\\.[a-zA-Z]+")) {
                    Toast.makeText(getApplicationContext(), "Email is invalid", Toast.LENGTH_SHORT).show();
                    flag1=0;
                }
                if(flag1==1){
                    try {
                        myDataBase.execSQL("CREATE TABLE IF NOT EXISTS INFO (userName TEXT PRIMARY KEY,password TEXT)");
                        myDataBase.execSQL("INSERT INTO INFO (userName,password) VALUES('"+uid+"','"+upass+"');");
                        flag2=1;
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(),"Some error occured!!",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
                if(flag2==1){
                    Toast.makeText(getApplicationContext(),"Sign up done!!", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });
    }
}
