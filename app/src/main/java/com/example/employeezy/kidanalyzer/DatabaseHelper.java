package com.example.employeezy.kidanalyzer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by EmployYeezy on 9/9/16.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "Data.db";

    public static final String SCHOOLS_TABLE = "schools_table";

    public static final String COL_ID = "_id";
    public static final String NAMEOFSCHOOL = "name";
    public static final String RATING = "rating";

    public static final String[] COL_NAMES = {COL_ID, NAMEOFSCHOOL, RATING};

    private static final String CREATE_TABLE =
            "CREATE TABLE " + SCHOOLS_TABLE +
                    "(" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NAMEOFSCHOOL + " TEXT," +
                    RATING + " REAL)";

    private static DatabaseHelper instance;

    public static DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_QUESTION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SCHOOLS_TABLE);
        this.onCreate(db);
    }

    public void insertSchool(String schoolName) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAMEOFSCHOOL, schoolName);
        long g = db.insert(SCHOOLS_TABLE, null, values);


        int i = (int) g;
        values = new ContentValues();
        values.put(QUESTION_TITLE, Questions.questionOne);
        values.put(SCHOOL_ID, i);
        values.put(COMPLETED, 0);
        db.insert(QUESTION_TABLE, null, values);

        values = new ContentValues();
        values.put(QUESTION_TITLE, Questions.questionTwo);
        values.put(SCHOOL_ID, i);
        values.put(COMPLETED, 0);
        db.insert(QUESTION_TABLE, null, values);

        values = new ContentValues();
        values.put(QUESTION_TITLE, Questions.questionThree);
        values.put(SCHOOL_ID, i);
        values.put(COMPLETED, 0);
        db.insert(QUESTION_TABLE, null, values);

        values = new ContentValues();
        values.put(QUESTION_TITLE, Questions.questionFour);
        values.put(SCHOOL_ID, i);
        values.put(COMPLETED, 0);
        db.insert(QUESTION_TABLE, null, values);

        values = new ContentValues();
        values.put(QUESTION_TITLE, Questions.questionFive);
        values.put(SCHOOL_ID, i);
        values.put(COMPLETED, 0);
        db.insert(QUESTION_TABLE, null, values);

        values = new ContentValues();
        values.put(QUESTION_TITLE, Questions.questionSix);
        values.put(SCHOOL_ID, i);
        values.put(COMPLETED, 0);
        db.insert(QUESTION_TABLE, null, values);

        values = new ContentValues();
        values.put(QUESTION_TITLE, Questions.questionSeven);
        values.put(SCHOOL_ID, i);
        values.put(COMPLETED, 0);
        db.insert(QUESTION_TABLE, null, values);

        values = new ContentValues();
        values.put(QUESTION_TITLE, Questions.questionEight);
        values.put(SCHOOL_ID, i);
        values.put(COMPLETED, 0);
        db.insert(QUESTION_TABLE, null, values);
    }

    public Cursor getSchooList() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(SCHOOLS_TABLE, // a. table
                COL_NAMES, // b. column names
                null, // c. selections
                null, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        return cursor;
    }
    //End of Schools Table Beginning of Questions table

    public static final String QUESTION_TABLE = "question_table";

    public static final String QUESTION_TITLE = "question_title";
    public static final String QUESTION_RATING = "question_rating";
    public static final String QUESTION_NOTES = "question_notes";
    public static final String SCHOOL_ID = "school_id";
    public static final String COMPLETED = "completed";



    public static final String[] QUESTION_COL_NAMES = {COL_ID, QUESTION_TITLE, QUESTION_RATING, QUESTION_NOTES, SCHOOL_ID, COMPLETED};

    private static final String CREATE_QUESTION_TABLE =
            "CREATE TABLE " + QUESTION_TABLE +
                    "(" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    QUESTION_TITLE + " TEXT," +
                    QUESTION_RATING + " REAL," +
                    QUESTION_NOTES + " TEXT," +
                    COMPLETED + " INTEGER," +
                    SCHOOL_ID + " INTEGER," +
                    " FOREIGN KEY(" + SCHOOL_ID + ") REFERENCES " + SCHOOLS_TABLE + "(" + COL_ID + "))";

    public Cursor getQuestionListatID(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selections = SCHOOL_ID + " = ?";

        String [] selectionsArgs = new String[] {
                id
        };


        Cursor cursor = db.query(QUESTION_TABLE, // a. table
                QUESTION_COL_NAMES, // b. column names
                selections, // c. selections
                selectionsArgs, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        DatabaseUtils.dumpCursor(cursor);
        return cursor;
    }

    public void updateCompletedAtId(String id, int completed) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(COMPLETED, completed);

        String selection = COL_ID + " = ?";

        String [] selectionArgs = new String[] {
                id
        };

        db.update(QUESTION_TABLE, values, selection, selectionArgs);
    }

}


