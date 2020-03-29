package com.neil.guessthecelebrity;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

class LoadActorData {

    private Object actorName;

    public void parseActorData() {
        System.out.println("test");
        JSONParser jsonParser = new JSONParser();
        try (FileReader fileReader = new FileReader("./resources/actors.json")) {
            Object actorsObj = jsonParser.parse(fileReader);
            JSONArray actors = (JSONArray) actorsObj;
            actors.forEach(actor -> parseActorsObject((JSONObject) actor));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void parseActorsObject(JSONObject actor) {
        Object name = actor.get("PerformerImage");
        System.out.println("name: " + name);
        setActorName(name);
    }

    public Object getActorName() {
        return actorName;
    }

    private void setActorName(Object actorName) {
        this.actorName = actorName;
    }
}

