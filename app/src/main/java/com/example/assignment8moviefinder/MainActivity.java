package com.example.assignment8moviefinder;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 You will be making a movie search app using OMDb API.

 In this app you will be designing your own layout. At the start, the app should display an edit text
 and a button to search for a movie. Use Volley to send a request to OMDb API, and if the API returns
 a valid data, parse the JSON object and display the title, poster image using Picasso, the year of release,
 Rating, running time, Genre, IMDB rating, a link to the IMDB page (which should open the page using
 an implicit Intent), and a share button (which should share the title and the URL using an implicit intent).

 On the toolbar, there should be a button for feedback. Tapping it should compose an email which the
 title: “Feedback” and addressed to the developer (your email address).

 */
public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    //private List<Movie> movieList;
    String value;
    String urlForDescription;
    String urlForPoster;
    String title, imageurl;
    TextView titletxt, yearofreleasetxt,rating, runningtime, genre, website_link;
    ImageView imageView;
    Button share_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titletxt = findViewById(R.id.title);
        yearofreleasetxt = findViewById(R.id.yearofrelease);
        imageView = findViewById(R.id.poster_image);
        rating = findViewById(R.id.rating);
        runningtime = findViewById(R.id.running_time);
        genre = findViewById(R.id.genre);
        website_link = findViewById(R.id.website);
        share_button = findViewById(R.id.share_button);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("search_input");
            //The key argument here must match that used in the other activity
        }
        urlForDescription = "http://www.omdbapi.com/?t=" + value + "&apikey=27fbd61d";

        // creating a new variable for our request queue
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlForDescription, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //for (int i = 0 ; i < response.length() ; i++){
                            try {
                                Log.d("HELLO", "ghtg");
                                title = response.getString("Title");
                                imageurl = response.getString("Poster");
                                titletxt.setText(response.getString("Title"));
                                yearofreleasetxt.setText(response.getString("Year"));
                                runningtime.setText(response.getString("Runtime"));
                                genre.setText(response.getString("Genre"));
                                website_link.setText(response.getString("Website"));
                                Picasso.get().load(response.getString("Poster")).into(imageView);
                                Log.d("HELLO", "helle");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    //}
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("RESPONSE_ERROR", error.getMessage());
            }
        });
        MySingleton.getInstance(MainActivity.this).addToRequestQueue(jsonObjectRequest);

        share_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the text message with a string.
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out this movie "+title
                        + ". This is the url for the movie cove " + imageurl);
                sendIntent.setType("text/plain");
                //sendIntent.setType("text/plain");
                //BitmapDrawable bitmapDrawable = imageView.getDrawable();
                //Bitmap bitmap = bitmapDrawable.getBitmap();
                try {
                    startActivity(sendIntent);
                } catch (ActivityNotFoundException e) {
                    // Define what your app should do if no activity can handle the intent.
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.activity_main_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.feedback:
                startActivity(new Intent(getApplicationContext(), FeedbackEmailActivity.class));
        }
        return true;
    }
}