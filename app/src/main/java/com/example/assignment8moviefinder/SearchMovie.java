package com.example.assignment8moviefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class SearchMovie extends AppCompatActivity {
    TextView searchInput;
    Button searchButton;
    String searchInputString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);
        searchButton = findViewById(R.id.search_button);
        searchInput = findViewById(R.id.search_input);



        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchInput.getText().toString().isEmpty()) {
                    Toast.makeText(SearchMovie.this, "umm.. enter something..", Toast.LENGTH_SHORT).show();
                } else {
                    Intent i = new Intent(SearchMovie.this, MainActivity.class);
                    searchInputString = searchInput.getText().toString();
                    i.putExtra("search_input",searchInputString);
                    startActivity(i);
                }
            }
        });
    }
}