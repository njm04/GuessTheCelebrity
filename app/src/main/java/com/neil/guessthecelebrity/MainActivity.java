package com.neil.guessthecelebrity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView actorImage = findViewById(R.id.imageView);
        LoadActorData loadActorData = new LoadActorData();
        loadActorData.parseActorData();
        //System.out.println("actorName: " + loadActorData.getActorName());
        LoadImage loadImage = new LoadImage(actorImage);
        loadImage.execute("https://images.fandango.com/r1.0.856/ImageRenderer/120/180/redesign/static/img/noxportrait.jpg/p302167/cp/cpc/images/masterrepository/performer images/p302167/chrisevans-2019.jpg");

    }

}
