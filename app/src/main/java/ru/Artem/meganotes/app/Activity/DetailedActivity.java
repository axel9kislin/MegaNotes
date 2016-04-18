package ru.Artem.meganotes.app.Activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import ru.Artem.meganotes.app.DataBaseHelper.DataBaseHelper;
import ru.Artem.meganotes.app.Fragments.CreateNoteFragment;
import ru.Artem.meganotes.app.Models.DataFromDB;
import ru.Artem.meganotes.app.Models.ModelNote;
import ru.Artem.meganotes.app.POJO.HelpClass;
import ru.Artem.meganotes.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Артем on 13.04.2016.
 */
public class DetailedActivity extends AppCompatActivity implements EditText.OnEditorActionListener {

    private HelpClass helpClass = new HelpClass();
    private List<ModelNote> modelNotes;
    private String tmpEditTitle = null;
    private InputMethodManager imm;
    private String tmpText = "";
    private EditText editTitle;
    private EditText editContent;
    private TextView textView;
    private String[] where;
    private final String query = "select * from " + DataBaseHelper.DATABASE_TABLE
            + " where " + DataBaseHelper.TITLE_NOTES_COLUMN + " = ?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        where = new String[]{getIntent().getStringExtra("nameNote")};
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detailed);

        /*setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
        setContentView(R.layout.activity_detailed);
        textView = (TextView) findViewById(R.id.textView);
        editTitle = (EditText) findViewById(R.id.editTitle);
        editContent = (EditText) findViewById(R.id.editContent);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
        SQLiteDatabase sqLiteDatabase = dataBaseHelper.getWritableDatabase();
        helpClass.openCursor(sqLiteDatabase.rawQuery(query, where));

        textView.setText(DataFromDB.lastUpdateData);
        editContent.setText(DataFromDB.contentNote);
        editTitle.setText(DataFromDB.titleNote);
        //Log.d("mylog", "id: "  + DataFromDB.titleNote);
        editTitle.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //if(actionId == EditorInfo.IME_ACTION_DONE) {
                    //Log.d("mylog", CreateNoteFragment.getDate());
                    imm.hideSoftInputFromWindow(editTitle.getWindowToken(), 0);
                    editData(DataBaseHelper.TITLE_NOTES_COLUMN, v.getText().toString(), CreateNoteFragment.getDate());
               // }
                return true;
            }
        });

        editContent.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                imm.hideSoftInputFromWindow(editContent.getWindowToken(), 0);
                editData(DataBaseHelper.CONTENT_COLUMN, v.getText().toString(), CreateNoteFragment.getDate());
                return true;
            }
        });
    }


    private void editData(String column, String value, String lastUpdateDate) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplicationContext());
        SQLiteDatabase sqLiteDatabase = dataBaseHelper.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(column, value);
        values.put(DataBaseHelper.LAST_UPDATE_DATE_COLUMN, lastUpdateDate);

        sqLiteDatabase.update(DataBaseHelper.DATABASE_TABLE, values, DataBaseHelper.TITLE_NOTES_COLUMN + " = ?",
                new String[]{getIntent().getStringExtra("nameNote")});
    }

    /*public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.empty_menu, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        return false;
    }
}
