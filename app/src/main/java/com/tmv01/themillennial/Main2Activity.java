package com.tmv01.themillennial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        final CalendarView cal = findViewById(R.id.calendars);
        final Intent intent = new Intent(Main2Activity.this,firstpage.class);
        Button done=findViewById(R.id.done);

        ImageButton Back = findViewById(R.id.backButton);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Back = new Intent(Main2Activity.this,firstpage.class);
                Back.putExtra("date",getIntent().getStringExtra("date"));
                finish();
                startActivity(Back);
            }
        });

        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

                int zero=0;
                String y=String.valueOf(i);
                String m=String.valueOf(i1+1);
                String d=String.valueOf(i2);
                if(i1<10 && i2<10){
                    intent.putExtra("date",y+"-"+zero+m+"-"+zero+d);
                    Toast.makeText(Main2Activity.this, y+"-"+zero+m+"-"+zero+d, Toast.LENGTH_SHORT).show();
                }
                else if(i1<10 && i2>=10){
                    intent.putExtra("date",y+"-"+zero+m+"-"+d);
                    Toast.makeText(Main2Activity.this, y+"-"+zero+m+"-"+d, Toast.LENGTH_SHORT).show();
                }
                else if (i2<10 && i1>=10){
                    intent.putExtra("date",y+"-"+m+"-"+zero+d);
                    Toast.makeText(Main2Activity.this, y+"-"+m+"-"+zero+d, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    intent.putExtra("date",y+"-"+m+"-"+d);
                    Toast.makeText(Main2Activity.this, y+"-"+m+"-"+d, Toast.LENGTH_SHORT).show();

                }
            }
        });

        done.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(intent);
                finish();
            }
        });



    }
}