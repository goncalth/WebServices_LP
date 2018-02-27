package fr.tgoncalves.webservices;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;


public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void subscribe(View view) {
        Intent sub = new Intent(MainActivity.this, Subscribe.class);
        //sub.putExtra("key", value); //Optional parameters
        MainActivity.this.startActivity(sub);
    }

    public void connect(View view) {
        Intent sub = new Intent(MainActivity.this, login.class);
        //sub.putExtra("key", value); //Optional parameters
        MainActivity.this.startActivity(sub);
    }

    public void showUsers(View view) {
        Intent show = new Intent(MainActivity.this, showUsers.class);
        //sub.putExtra("key", value); //Optional parameters
        MainActivity.this.startActivity(show);
    }


}