package com.example.fithub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ShowZoomedPicture extends AppCompatActivity {

    ImageView zoomedpic;
    String piclink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showpicturezoomed);

        piclink = getIntent().getStringExtra("piclink");

        zoomedpic = findViewById(R.id.zoomedpic);

        Picasso.with(this)
                .load(piclink)
                .into(zoomedpic);



    }
}
