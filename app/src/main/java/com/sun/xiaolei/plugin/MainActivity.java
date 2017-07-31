package com.sun.xiaolei.plugin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.didi.virtualapk.PluginManager;
import com.didi.virtualapk.internal.LoadedPlugin;

import java.io.File;

import static android.os.Environment.getExternalStorageDirectory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = (TextView) findViewById(R.id.tv_test);
        tv.setText("Host");

        // 加载plugin.apk插件包
        PluginManager pluginManager = PluginManager.getInstance(MainActivity.this);
        File apk = new File(getExternalStorageDirectory(), "plugin_mod1.apk");
        if (apk.exists()) {
            try {
                pluginManager.loadPlugin(apk);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(MainActivity.this, "no found plugin!!!", Toast.LENGTH_SHORT).show();
        }

        LoadedPlugin plugin = pluginManager.getLoadedPlugin("");
        plugin.getPluginManager();
        findViewById(R.id.btn_open_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName("com.sun.xiaolei.plugintest1",
                        "com.sun.xiaolei.plugintest1.MainActivity");
                startActivity(intent);
            }
        });
    }
}
