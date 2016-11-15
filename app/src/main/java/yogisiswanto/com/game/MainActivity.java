package yogisiswanto.com.game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //konstanta, supaya bisa membedakan antar message
    //public final static String EXTRA_MESSAGE = "yogisiswanto.com.game";

    static final int ACT2_REQUEST = 99;  // request code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void button(View v) {
        //ambil komponen text dan label
        EditText mail = (EditText) findViewById(R.id.mail);
        EditText pass = (EditText) findViewById(R.id.pass);

        //cek login
        //when login is correct, will be directly to MapsActivity
        if(mail.getText().toString().equals("example@mail.com") && pass.getText().toString().equals("nilaimobprogA")){

            //show toast
            Toast toast = Toast.makeText(getApplicationContext(), "Login Berhasil", Toast.LENGTH_LONG);
            toast.show();

            //clear the textfield
            mail.setText("");
            pass.setText("");

            Intent intent2 = new Intent(this, MapsActivity.class);
            startActivityForResult(intent2,ACT2_REQUEST);

        //when login is failed
        }else{

            //show toast
            Toast toast2 = Toast.makeText(getApplicationContext(), "Login Gagal", Toast.LENGTH_LONG);
            toast2.show();

            //clear the text field
            mail.setText("");
            pass.setText("");
        }
    }
}
