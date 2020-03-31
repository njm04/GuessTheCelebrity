package com.neil.guessthecelebrity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

class RetrieveActorNames {
    private JSONArray actors;

    RetrieveActorNames(JSONArray actors) {
        this.actors = actors;
    }

    // Get random actor names
    private ArrayList<Object> getActorName() {
        ArrayList<Object> names = new ArrayList<>();
        ArrayList<Integer> uniqueRandomIndex = new ArrayList<>();
        int randomIndex;
        try {

            for (int i = 0; i < 4; i++) {
                randomIndex = getRandomIndex(uniqueRandomIndex);
                names.add(actors.getJSONObject(randomIndex).get("PerformerName"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return names;
    }

    // To get unique random index
    private int getRandomIndex(ArrayList<Integer> uniqueRandomIndex) {
        int randomIndex;
        randomIndex = new Random().nextInt(actors.length());

        if (!uniqueRandomIndex.contains(randomIndex)) uniqueRandomIndex.add(randomIndex);
        else if (uniqueRandomIndex.contains(randomIndex)) {
            randomIndex = new Random().nextInt(actors.length());
            uniqueRandomIndex.add(randomIndex);
        }
        return randomIndex;
    }

    // check and replace a any name with the correct name
    // this allows the choices to always include the correct answer
    ArrayList namesChoices(Object correctName) {
        ArrayList<Object> names = getActorName();
        if(!names.contains(correctName)) {
            names.set(0, correctName);
            Collections.shuffle(names);
            return names;
        } else {
            Collections.shuffle(names);
            return names;
        }
    }

}
