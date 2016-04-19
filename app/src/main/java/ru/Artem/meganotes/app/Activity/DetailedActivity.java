package ru.Artem.meganotes.app.Activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.*;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import ru.Artem.meganotes.app.DataBaseHelper.DataBaseHelper;
import ru.Artem.meganotes.app.Fragments.CreateNoteFragment;
import ru.Artem.meganotes.app.Fragments.MyDialogFragment;
import ru.Artem.meganotes.app.Models.DataFromDB;
import ru.Artem.meganotes.app.Models.ModelNote;
import ru.Artem.meganotes.app.POJO.HelpClass;
import ru.Artem.meganotes.app.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Артем on 13.04.2016.
 */
public class DetailedActivity extends AppCompatActivity implements EditText.OnEditorActionListener,
        MyDialogFragment.OnFragmentInteractionListener{

    private HelpClass helpClass = new HelpClass();
    private List<ModelNote> modelNotes;
    private String tmpEditTitle = null;
    private InputMethodManager imm;
    private String tmpText = "";
    private EditText editTitle;
    private EditText editContent;
    private TextView textView;
    private String[] where;
    private ImageView imageView;
    private final String query = "select * from " + DataBaseHelper.DATABASE_TABLE
            + " where " + DataBaseHelper.TITLE_NOTES_COLUMN + " = ?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        where = new String[]{getIntent().getStringExtra("nameNote")};
        setContentView(R.layout.activity_detailed);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detailed);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textView = (TextView) findViewById(R.id.textView);
        editTitle = (EditText) findViewById(R.id.editTitle);
        editContent = (EditText) findViewById(R.id.editContent);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
        SQLiteDatabase sqLiteDatabase = dataBaseHelper.getWritableDatabase();
        helpClass.openCursor(sqLiteDatabase.rawQuery(query, where));

        textView.setText(DataFromDB.lastUpdateData);
        editContent.setText(DataFromDB.contentNote);
        editTitle.setText(DataFromDB.titleNote);

        editTitle.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                imm.hideSoftInputFromWindow(editTitle.getWindowToken(), 0);
                editData(DataBaseHelper.TITLE_NOTES_COLUMN, v.getText().toString(), CreateNoteFragment.getDate());
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

        imageView = (ImageView) findViewById(R.id.imageNote);
        setImg(Uri.parse(DataFromDB.imgPath));

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDialogFragment myDialogFragment = new MyDialogFragment();
                Bundle args = new Bundle();
                args.putString("dialogKey", "camera/gallery");
                myDialogFragment.setArguments(args);
                myDialogFragment.show(getSupportFragmentManager(), "dialog");
            }
        });
    }

    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            final Bitmap image = (Bitmap) msg.obj;
            imageView.setImageBitmap(image);
        }
    };

    private void setImg(final Uri pathImg) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream inputStream;

                try {
                    inputStream = getContentResolver()
                            .openInputStream(pathImg);
                    final Message message = handler.obtainMessage(1,
                            BitmapFactory.decodeStream(inputStream, null, null));
                    handler.sendMessage(message);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();
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

    @Override
    public void getUriPath(String uri) {
        setImg(Uri.parse(uri));
        editData(DataBaseHelper.IMG_PATH_COLUMN, uri, CreateNoteFragment.getDate());
    }
}
