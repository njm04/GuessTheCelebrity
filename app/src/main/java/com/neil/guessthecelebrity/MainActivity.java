package com.neil.guessthecelebrity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ImageView actorImage;

    private Button firstBTN;
    private Button secondBTN;
    private Button thirdBTN;
    private Button fourthBTN;
    private TextView correctAnswerPointsDisplay;
    private TextView wrongAnswerPointsDisplay;

    private int index = 0;
    private Object actorName = null;
    private RetrieveActorNames retrieved;
    private int pointsCounter = 0;
    private int wrongAnswerCounter = 0;

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
        correctAnswerPointsDisplay = findViewById(R.id.correct_answer_points);
        wrongAnswerPointsDisplay = findViewById(R.id.wrong_answer_points);

        retrieved = new RetrieveActorNames(actorsList());

        correctAnswerPointsDisplay.setText(getString(R.string.correct,
                Integer.toString(pointsCounter)));

        wrongAnswerPointsDisplay.setText(getString(R.string.mistakes,
                Integer.toString(wrongAnswerCounter)));

        getActorImage(index);
        choicesButton();
    }

    public void checkIfCorrectBtnOne(View view) {
        if (firstBTN.getText().equals(getCorrectName())) {
            Toast.makeText(this, "Your guessed it right!", Toast.LENGTH_LONG).show();
            index++;
            pointsCounter++;
            checkIfAllItemsAreAnswered();
        } else {
            index++;
            wrongAnswerCounter++;
            checkIfAllItemsAreAnswered();
            Toast.makeText(this,
                    "You made a mistake! the actor name is " + getCorrectName().toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }


    public void checkIfCorrectBtnTwo(View view) {
        if (secondBTN.getText().equals(getCorrectName())) {
            Toast.makeText(this, "Your guessed it right!", Toast.LENGTH_LONG).show();
            index++;
            pointsCounter++;
            checkIfAllItemsAreAnswered();
        } else {
            index++;
            wrongAnswerCounter++;
            checkIfAllItemsAreAnswered();
            Toast.makeText(this,
                    "You made a mistake! the actor's name is " + getCorrectName().toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void checkIfCorrectBtnThree(View view) {
        if (thirdBTN.getText().equals(getCorrectName())) {
            Toast.makeText(this, "Your guessed it right!", Toast.LENGTH_LONG).show();
            index++;
            pointsCounter++;
            checkIfAllItemsAreAnswered();
        } else {
            index++;
            wrongAnswerCounter++;
            checkIfAllItemsAreAnswered();
            Toast.makeText(this,
                    "You made a mistake! the actor's name is " + getCorrectName().toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void checkIfCorrectBtnFour(View view) {
        if (fourthBTN.getText().equals(getCorrectName())) {
            Toast.makeText(this, "Your guessed it right!", Toast.LENGTH_LONG).show();
            index++;
            pointsCounter++;
            checkIfAllItemsAreAnswered();
        } else {
            index++;
            wrongAnswerCounter++;
            checkIfAllItemsAreAnswered();
            Toast.makeText(this,
                    "You made a mistake! the actor's name is " + getCorrectName().toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void choicesButton() {
        Button[] buttons = new Button[]{firstBTN, secondBTN, thirdBTN, fourthBTN};
        ArrayList actorNamesChoices = retrieved.namesChoices(getCorrectName());

        List<Button> buttonList = Arrays.asList(buttons);
        Collections.shuffle(buttonList);

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setText(actorNamesChoices.get(i).toString());
        }
    }

    private void getActorImage(int index) {
        LoadImage loadImage = new LoadImage(actorImage);
        try {
            loadImage.execute(actorsList().getJSONObject(index).get("PerformerImage").toString());

            actorName = actorsList().getJSONObject(index).get("PerformerName");
        } catch (JSONException e) {
            e.printStackTrace();
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

    private void checkIfAllItemsAreAnswered() {
        Handler handler = new Handler();
        int sumOfAllPoints = pointsCounter + wrongAnswerCounter;
        if (index > 49 && sumOfAllPoints > 49) {
            handler.postDelayed(this::resetGame, 1100);
        } else {
            handler.postDelayed(this::continueGame, 1100);
        }
    }

    private void resetGame() {
        index = 0;
        pointsCounter = 0;
        wrongAnswerCounter = 0;

        getActorImage(index);

        correctAnswerPointsDisplay.setText(getString(R.string.correct,
                Integer.toString(pointsCounter)));

        wrongAnswerPointsDisplay.setText(getString(R.string.mistakes,
                Integer.toString(wrongAnswerCounter)));

        choicesButton();
    }

    private void continueGame() {
        getActorImage(index);
        displayScore();
        displayMistakesScore();
        choicesButton();
    }

    private void displayScore() {
        String displayPoints = getString(R.string.correct, Integer.toString(pointsCounter));
        correctAnswerPointsDisplay.setText(displayPoints);
    }

    private void displayMistakesScore() {
        String displayMistakesPoints = getString(R.string.mistakes,
                Integer.toString(wrongAnswerCounter));
        wrongAnswerPointsDisplay.setText(displayMistakesPoints);
    }

    public Object getCorrectName() {
        return actorName;
    }
}
