package com.sun.xiaolei.plugintest1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by sunxl8 on 2017/7/31.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p1_activity_main);
        TextView tv = (TextView) findViewById(R.id.tv_test);
        tv.setText("Plugin 1 update");
    }
}
