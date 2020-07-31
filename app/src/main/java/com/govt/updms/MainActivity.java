package com.govt.updms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.govt.updms.util.TempStorage;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                if(TempStorage.getLoginData()!=null){
                    Intent in=new Intent(MainActivity.this,HomeActivity.class);
                    startActivity(in);
                    finish();
                }
                else if(TempStorage.getWorkerLoginData()!=null){
                    Intent in=new Intent(MainActivity.this,WorkerNewHomePageActivity.class);
                    startActivity(in);
                    finish();
                }
                else{
                    Intent in = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(in);
                    finish();
                }
            }
        }, 3000);


    }
}
