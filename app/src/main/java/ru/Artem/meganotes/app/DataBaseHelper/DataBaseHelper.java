package ru.Artem.meganotes.app.DataBaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Артем on 07.04.2016.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mydb.db";
    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_TABLE = "notes";
    public static final String ID_COLUMN = "_id";
    public static final String TITLE_NOTES_COLUMN = "note_title";
    public static final String CONTENT_COLUMN = "content";
    public static final String IMG_PATH_COLUMN = "img_path";
    public static final String CREATE_DATE_COLUMN = "create_date";
    public static final String LAST_UPDATE_DATE_COLUMN = "last_update_date";
    public static final String CATEGORY_COLUMN = "category_column";

    private static final String CREATE_TABLE = "CREATE TABLE " + DATABASE_TABLE
            + " (" + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TITLE_NOTES_COLUMN
            + " TEXT NOT NULL, " + CONTENT_COLUMN + " TEXT, " + IMG_PATH_COLUMN
            + " TEXT, " + CREATE_DATE_COLUMN + " TEXT NOT NULL, " + LAST_UPDATE_DATE_COLUMN
            + " TEXT, " + CATEGORY_COLUMN + " TEXT NOT NULL);";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
