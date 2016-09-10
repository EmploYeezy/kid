package com.example.employeezy.kidanalyzer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

public class QuestionDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);

        TextView textView = (TextView) findViewById(R.id.textViewDetail);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingDetail);
        EditText editText = (EditText) findViewById(R.id.editTextDetail);



    }
}
