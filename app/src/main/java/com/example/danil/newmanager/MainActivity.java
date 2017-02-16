package com.example.danil.newmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends Main {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Main.setLayoutResID(MainActivity.class);
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }


}
