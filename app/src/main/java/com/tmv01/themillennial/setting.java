package com.tmv01.themillennial;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class setting extends AppCompatActivity {

    public void logout(){
        FeedReaderDbHelper database = new FeedReaderDbHelper(this);
        database.logout(this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        final TextView preference = findViewById(R.id.preference);
        TextView saved = findViewById(R.id.saved);

        ImageButton Back = findViewById(R.id.backButton);
        Button logout = findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
                Toast.makeText(setting.this,"logging out",Toast.LENGTH_SHORT);
                startActivity(new Intent(setting.this,MainActivity.class));
                finish();
            }
        });



        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(setting.this,tib.class);
                back.putExtra("date",getIntent().getStringExtra("date"));
                startActivity(back);
                finish();
            }
        });

        saved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent savedNews = new Intent(setting.this,saved.class);
                savedNews.putExtra("date",getIntent().getStringExtra("date"));
                startActivity(savedNews);
                finish();
            }
        });
        preference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent preference = new Intent(setting.this,preference.class);
                preference.putExtra("date",getIntent().getStringExtra("date"));
                startActivity(preference);
                finish();
            }
        });
    }
}
