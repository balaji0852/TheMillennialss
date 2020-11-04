package com.tmv01.themillennial;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class setting extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        final TextView preference = findViewById(R.id.preference);
        TextView saved = findViewById(R.id.saved);

        ImageButton Back = findViewById(R.id.backButton);

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
