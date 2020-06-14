package com.tmv01.themillennial;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.google.firebase.firestore.FirebaseFirestore;
import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);
          final Button gotonewspaper = findViewById(R.id.newspaper);
          TextView headline = findViewById(R.id.headline);

          gotonewspaper.setOnClickListener(new View.OnClickListener()
          {
              @Override
              public void onClick(View view)
              {
                  startActivity(new Intent(MainActivity.this,firstpage.class));
              }
          });


    }
}
