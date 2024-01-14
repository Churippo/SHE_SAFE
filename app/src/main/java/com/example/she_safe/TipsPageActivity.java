package com.example.she_safe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class TipsPageActivity extends AppCompatActivity {
    private TextView randomtips;
    private Button btn_random;
    private Button btn_back;

    private String items[] = {"Display confidence. Walk with purpose, scan the area around you and make casual eye contact with others to display confidence.","Stay in well-lit areas. Avoid dark parking lots, dark alleys, dark lanes and dark trails. A well-lit path  is your safest route to any destination.","Keep your phone charged and let someone know your plans and whereabouts. Regularly check in with friends or family, especially if you're out alone.","When using e-hailing services, ensure that the driver and vehicle match the details provided by the app. Share your ride details with someone.","Be cautious about sharing personal information, especially on social media. Avoid disclosing your home address or detailed plans online.","When entering unfamiliar places, identify exit routes. Knowing how to leave a location quickly can be valuable in emergency situations."};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips_page);
        randomtips = findViewById(R.id.randomtips);
        btn_random = findViewById(R.id.btn_random);
        btn_back = findViewById(R.id.btn_back);


        btn_random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random rndm = new Random();
                int i = rndm.nextInt(items.length);

                randomtips.setText(items[i]);
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TipsPageActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
