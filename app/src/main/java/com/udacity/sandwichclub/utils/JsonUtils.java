package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        try {
            // Create a new JSONObject
            JSONObject baseJSON = new JSONObject(json);

            // Extract the JSONObject associated with the key called "name"
            JSONObject names = baseJSON.getJSONObject("name");

            // Extract the value for the key called "mainName"
            String mainName = names.optString("mainName");

            // Extract the JSONArray associated with the key called "alsoKnownAs"
            JSONArray alsoKnownAsJsonArray = names.getJSONArray("alsoKnownAs");
            ArrayList<String> alsoKnownAs = new ArrayList<>();
            for (int i = 0; i < alsoKnownAsJsonArray.length(); i++) {
                alsoKnownAs.add(alsoKnownAsJsonArray.getString(i));
            }

            // Extract the value for the key called "placeOfOrigin"
            String placeOfOrigin = baseJSON.optString("placeOfOrigin");

            // Extract the value for the key called "description"
            String description = baseJSON.optString("description");

            // Extract the value for the key called "image"
            String image = baseJSON.optString("image");

            // Extract the JSONArray associated with the key called "ingredients"
            JSONArray ingredientsJsonArray = baseJSON.getJSONArray("ingredients");
            ArrayList<String> ingredients = new ArrayList<>();
            for (int i = 0; i < ingredientsJsonArray.length(); i++) {
                ingredients.add(ingredientsJsonArray.getString(i));
            }

            // Return the data
            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}