package com.example.assignment8moviefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class FeedbackEmailActivity extends AppCompatActivity {

    // define objects for edit text and button
    Button send_button;
    EditText subject, body;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_email);

        // Getting instance of edittext and button
        subject = findViewById(R.id.subjectTextView);
        body = findViewById(R.id.descriptionTextView);
        send_button = findViewById(R.id.sendBtn);
        // attach setOnClickListener to button with Intent object define in it
        send_button.setOnClickListener(view -> {
            String emailsubject = subject.getText().toString();
            String emailbody = body.getText().toString();

            // define Intent object with action attribute as ACTION_SEND
            Intent intent = new Intent(Intent.ACTION_SEND);

            // add three fields to intent using putExtra function
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"brilord1000@gmail.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT, emailsubject);
            intent.putExtra(Intent.EXTRA_TEXT, emailbody);
            // set type of intent
            intent.setType("message/rfc822");
            // startActivity with intent with chooser as Email client using createChooser function
            startActivity(Intent.createChooser(intent, "Choose an Email client :"));
        });
    }
}