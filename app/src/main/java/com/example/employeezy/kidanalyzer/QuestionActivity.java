package com.example.employeezy.kidanalyzer;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class QuestionActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        TextView schoolName = (TextView) findViewById(R.id.schoolName);
        Intent intent = getIntent();
        String schoolTitle = intent.getStringExtra("Name of School");
        String schoolID = intent.getStringExtra("School ID");
        schoolName.setText(schoolTitle);
        Cursor cursor = DatabaseHelper.getInstance(this).getQuestionListatID(schoolID);
        cursor.moveToFirst();
        ListView questionsList = (ListView) findViewById(R.id.questionsListView);

        final CursorAdapter cursorAdapter = new CursorAdapter(this, cursor, 0) {

            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                View view = layoutInflater.inflate(R.layout.a_question_layout, parent,false);
                return view;
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                CheckBox checkBox = (CheckBox) view.findViewById(R.id.a_question_CB);
                String questionTitle = cursor.getString(cursor.getColumnIndex(DatabaseHelper.QUESTION_TITLE));
                checkBox.setText(questionTitle);
            }
        };
        questionsList.setAdapter(cursorAdapter);

    }
}
