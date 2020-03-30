package com.neil.guessthecelebrity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ImageView actorImage;

    private Button firstBTN;
    private Button secondBTN;
    private Button thirdBTN;
    private Button fourthBTN;
    private Button[] buttons;

    private int index = 0;
    HashMap<String, String> actor = new HashMap<>();
    private String actorId = null;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actorImage = findViewById(R.id.imageView);
        firstBTN = findViewById(R.id.first_button);
        secondBTN = findViewById(R.id.second_button);
        thirdBTN = findViewById(R.id.third_button);
        fourthBTN = findViewById(R.id.fourth_button);

        getActorImage(index);
        //checkIfCorrectBtnOne(actorImage);
        //TODO: delete this! this is just a snippet to test
        for (int i = 0; i < actorsList().length(); i++)
        {
            try {
                //System.out.println(actorsList().getJSONObject(i));
                if(actorsList().getJSONObject(i).get("PerformerId").equals("P419915")) {
                    System.out.println(actorsList().getJSONObject(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        buttons = new Button[]{firstBTN, secondBTN, thirdBTN, fourthBTN};

        List<Button> buttonList = Arrays.asList(buttons);

        Collections.shuffle(buttonList);

        //System.out.println("array length: " + buttons.length);
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setText(getActorName().get(i).toString());
        }

    }

    private JSONArray actorsList(){
        JSONArray actorsArray = null;
        try {
            InputStream inputStream = getAssets().open("actors.json");

            int size = inputStream.available();

            byte[] buffer = new byte[size];
            inputStream.read(buffer);

            inputStream.close();

            String jsonString = new String(buffer, StandardCharsets.UTF_8);
            actorsArray = new JSONArray(jsonString);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return actorsArray;
    }

    private void getActorImage(int index) {
        LoadImage loadImage = new LoadImage(actorImage);
        try {
            System.out.println(actorsList().getJSONObject(index).get("PerformerImage").toString());
            loadImage.execute(actorsList().getJSONObject(index).get("PerformerImage").toString());
            actorId = actorsList().getJSONObject(index).get("PerformerId").toString();
            //actor.put(actorsList().getJSONObject(index).get("PerformerName").toString(), actorsList().getJSONObject(index).get("PerformerImage").toString());
            //System.out.println(actorId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getActorId() {
        return actorId;
    }

    public void checkIfCorrectBtnOne(View view) {
        isCorrect();
    }

    public void checkIfCorrectBtnTwo(View view) {
        isCorrect();
    }

    public void checkIfCorrectBtnThree(View view) {
        isCorrect();
    }

    public void checkIfCorrectBtnFour(View view) {
        isCorrect();
    }

    //TODO: fix to handle correct and incorrect answers properly.
    private void isCorrect() {
        LoadImage loadImage = new LoadImage(actorImage);
        JSONArray actors = actorsList();
        index++;
        getActorImage(index);
        //if (getActorId())
        for (int i = 0; i < actorsList().length(); i++) {
            try {
                if(actorsList().getJSONObject(i).get("PerformerId").equals(getActorId())) {
                    System.out.println(i);
                    Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList getActorName() {
        ArrayList<Object> names = new ArrayList<>();
        try {
            for (int i = 0; i < actorsList().length(); i++) {
                names.add(actorsList().getJSONObject(i).get("PerformerName"));
                //actors.add(actorObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return names;
    }

}
