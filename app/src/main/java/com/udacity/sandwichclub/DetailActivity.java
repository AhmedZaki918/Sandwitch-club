package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;


public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Find view by id
        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        // Display the main name
        displayNames(R.id.origin_tv, sandwich.getMainName());

        // Display also known as
        List<String> alsoKnownAsList = sandwich.getAlsoKnownAs();

        // Create object from string builder
        StringBuilder sbAlsoKnown = new StringBuilder();
        for (int i = 0; i < alsoKnownAsList.size(); i++) {
            sbAlsoKnown.append(alsoKnownAsList.get(i));
            if (i != (alsoKnownAsList.size() - 1)) {
                sbAlsoKnown.append(", ");
            }
        }
        // Display also Known as
        displayNames(R.id.also_known_tv, sbAlsoKnown.toString());

        // Display place of origin
        displayNames(R.id.place_origin_tv, sandwich.getPlaceOfOrigin());

        List<String> ingredientsList = sandwich.getIngredients();
        // Create object from string builder
        StringBuilder sbIngredients = new StringBuilder();
        for (int i = 0; i < ingredientsList.size(); i++) {
            sbIngredients.append(ingredientsList.get(i));
            if (i != (ingredientsList.size())) {
                sbIngredients.append("\n");
            }
        }
        // Display the ingredients
        displayNames(R.id.ingredients_tv, sbIngredients.toString());

        // Display the description
        displayNames(R.id.description_tv, sandwich.getDescription());
    }

    // Display the views
    private void displayNames(int id, String givenText) {
        // Create TextView variable to store the id of the view
        TextView tx = findViewById(id);
        // Display the text on that view
        tx.setText(givenText);
    }
}