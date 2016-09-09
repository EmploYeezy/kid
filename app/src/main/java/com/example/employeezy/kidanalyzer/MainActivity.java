package com.example.employeezy.kidanalyzer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ListView schoolList = (ListView) findViewById(R.id.listOfSchools);
        cursor = (Cursor) DatabaseHelper.getInstance(MainActivity.this).getSchooList();
        setSupportActionBar(toolbar);

        final CursorAdapter cursorAdapter = new CursorAdapter(this, cursor, 0) {

            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                View view = layoutInflater.inflate(R.layout.school_names, parent,false);
                return view;
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                TextView rosterPlayerName = (TextView) view.findViewById(R.id.schoolInputTV);
                String playerNamesGetter = cursor.getString(cursor.getColumnIndex(DatabaseHelper.NAMEOFSCHOOL));
                rosterPlayerName.setText(playerNamesGetter);

                RatingBar schoolRatingBar = (RatingBar) view.findViewById(R.id.schoolRatingBar);
                float rookieYearGetter = cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.RATING));
                schoolRatingBar.setRating(5);
            }
        };

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.add_list, null);
                builder.setView(dialogView);
                final EditText userInput = (EditText) dialogView.findViewById(R.id.user_input);
                builder.setTitle("Fuck you");
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String schoolName = userInput.getText().toString();
                        if (!userInput.getText().toString().equals("")) {
                            cursor = DatabaseHelper.getInstance(MainActivity.this).getSchooList();
                            boolean foundDuplicate = false;
                            while (cursor.moveToNext()) {
                                String current = cursor.getString(cursor.getColumnIndex(DatabaseHelper.NAMEOFSCHOOL));
                                if (current.equals(schoolName)) {
                                    foundDuplicate = true;
                                }
                            }
                            if (!foundDuplicate) {
                                DatabaseHelper.getInstance(MainActivity.this).insertSchool(schoolName);
                                cursor = DatabaseHelper.getInstance(MainActivity.this).getSchooList();
                                cursorAdapter.changeCursor(cursor);
                            }
                            else {
                                Toast.makeText(MainActivity.this, "Duplicate", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "No Name", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // does nothing
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }


        });

        schoolList.setAdapter(cursorAdapter);
        schoolList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
                String schoolID = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_ID));
                String schoolName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.NAMEOFSCHOOL));
                intent.putExtra("School ID", schoolID);
                intent.putExtra("Name of School", schoolName);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
