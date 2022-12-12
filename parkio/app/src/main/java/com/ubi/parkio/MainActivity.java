package com.ubi.parkio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btt = (Button) findViewById(R.id.button);
        btt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent act = new Intent(view.getContext(), map_page.class);
                startActivity(act);

            }
        });
    }
}