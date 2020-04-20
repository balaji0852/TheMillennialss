package com.tmv01.themillennial;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);
          final CalendarView cal = findViewById(R.id.calendars);
          final Button gotonewspaper = findViewById(R.id.newspaper);
          Button preferences = findViewById(R.id.preferences);
          final Intent intent = new Intent(MainActivity.this,firstpage.class);

                          cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                              @Override
                              public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                                  //Toast.makeText(MainActivity.this, i+":"+i1+":"+i2, Toast.LENGTH_SHORT).show();
//                                  intent.putExtra("yyyy",String.valueOf(i));
//                                  intent.putExtra("MM",String.valueOf(i1));
//                                  intent.putExtra("dd",String.valueOf(i2));
                                  int zero=0;
                                  String y=String.valueOf(i);
                                  String m=String.valueOf(i1+1);
                                  String d=String.valueOf(i2);
                                  if(i1<10 && i2<10){
                                      intent.putExtra("date",y+"-"+zero+m+"-"+zero+d);
                                      Toast.makeText(MainActivity.this, y+":"+zero+m+"-"+zero+d, Toast.LENGTH_SHORT).show();
                                  }
                                  else if(i1<10 && i2>=10){
                                      intent.putExtra("date",y+"-"+zero+m+"-"+d);
                                      Toast.makeText(MainActivity.this, y+":"+zero+m+"-"+d, Toast.LENGTH_SHORT).show();
                                  }
                                  else if (i2<10 && i1>=10){
                                      intent.putExtra("date",y+"-"+m+"-"+zero+d);
                                      Toast.makeText(MainActivity.this, y+"-"+m+"-"+zero+d, Toast.LENGTH_SHORT).show();
                                  }
                                  else
                                  {
                                      intent.putExtra("date",y+"-"+m+"-"+d);
                                      Toast.makeText(MainActivity.this, y+"-"+m+"-"+d, Toast.LENGTH_SHORT).show();

                                  }
                              }
                          });

                        gotonewspaper.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                startActivity(intent);
                            }
                        });

                        preferences.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(MainActivity.this,preference.class));
                            }
                        });
    }
}
