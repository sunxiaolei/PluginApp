package com.sun.xiaolei.plugintest1.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.sun.xiaolei.plugintest1.R;
import com.sun.xiaolei.plugintest1.TestAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunxl8 on 2017/7/31.
 */

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvTest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p1_activity_main);
        String str = getIntent().getStringExtra("data");
        TextView tv = (TextView) findViewById(R.id.tv_test);
        tv.setText(str == null ? "Plugin 1 update" : str);

        ImageView iv = (ImageView) findViewById(R.id.iv_test);

        rvTest = (RecyclerView) findViewById(R.id.rv_test);
        rvTest.setLayoutManager(new LinearLayoutManager(this));
        List<String> test = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            test.add("==" + i + "==");
        }
        TestAdapter mAdapter = new TestAdapter(this, test);
        rvTest.setAdapter(mAdapter);

    }
}
