package id.fando.gdprcheckerv2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import id.fando.GDPRChecker;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button =findViewById(R.id.check);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//            new GDPRChecker(getApplicationContext())
//                    .withContextAndActivty(MainActivity.this)
//                    .withAppId("ca-app-pub-8137876217855928~3007959370")
//                    .withDebug()
//                    .check();

                new GDPRChecker()
                        .withContext(getApplicationContext())
                        .withActivity(MainActivity.this)
                        .withAppId("ca-app-pub-8137876217855928~3007959370")
                        .withDebug()
                        .check();


            }
        });
    }
}