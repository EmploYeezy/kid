package com.example.employeezy.kidanalyzer;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class QuestionActivity extends AppCompatActivity {

    private Cursor mainCursor;
    private CursorAdapter cursorAdapter;
    private ListView questionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        TextView schoolName = (TextView) findViewById(R.id.schoolName);
        Intent intent = getIntent();
        String schoolTitle = intent.getStringExtra("Name of School");
        final String schoolID = intent.getStringExtra("School ID");
        schoolName.setText(schoolTitle);
        mainCursor = DatabaseHelper.getInstance(this).getQuestionListatID(schoolID);
        mainCursor.moveToFirst();
        questionsList = (ListView) findViewById(R.id.questionsListView);

        cursorAdapter = new CursorAdapter(this, mainCursor, 0) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                View view = layoutInflater.inflate(R.layout.a_question_layout, parent,false);
                return view;
            }

            @Override
            public void bindView(View view, Context context, final Cursor cursor) {
                final String questionId = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_ID));

                TextView textView = (TextView) view.findViewById(R.id.text);
                String questionTitle = cursor.getString(cursor.getColumnIndex(DatabaseHelper.QUESTION_TITLE));
                textView.setText(questionTitle);

                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(QuestionActivity.this, "Item", Toast.LENGTH_SHORT).show();
                    }
                });

                CheckBox checkBox = (CheckBox) view.findViewById(R.id.a_question_CB);
                int checked = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COMPLETED));
                if (checked == 0) {
                    checkBox.setChecked(false);
                } else {
                    checkBox.setChecked(true);
                }
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            DatabaseHelper.getInstance(QuestionActivity.this).updateCompletedAtId(questionId, 1);
                        }
                        else {
                            DatabaseHelper.getInstance(QuestionActivity.this).updateCompletedAtId(questionId, 0);
                        }
                        mainCursor = DatabaseHelper.getInstance(QuestionActivity.this).getQuestionListatID(schoolID);
                        cursorAdapter.changeCursor(mainCursor);
                    }
                });

            }
        };
        questionsList.setAdapter(cursorAdapter);

//        questionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            }
//        });


    }
}
