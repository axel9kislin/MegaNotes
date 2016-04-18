package ru.Artem.meganotes.app.POJO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import ru.Artem.meganotes.app.Adapters.MainAdapter;
import ru.Artem.meganotes.app.DataBaseHelper.DataBaseHelper;
import ru.Artem.meganotes.app.Models.DataFromDB;
import ru.Artem.meganotes.app.Models.ModelNote;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Артем on 14.04.2016.
 */
public class HelpClass {

    public static String nameNote;
    public static int position;

    public List<ModelNote> openCursor(Cursor cursor, List<ModelNote> modelNotes) {
        while (cursor.moveToNext()) {
            DataFromDB.titleNote = cursor.getString(cursor.getColumnIndex(DataBaseHelper.TITLE_NOTES_COLUMN));
            DataFromDB.contentNote = cursor.getString(cursor.getColumnIndex(DataBaseHelper.CONTENT_COLUMN));
            DataFromDB.lastUpdateData = cursor.getString(cursor.getColumnIndex(DataBaseHelper.LAST_UPDATE_DATE_COLUMN));
            DataFromDB.imgPath = cursor.getString(cursor.getColumnIndex(DataBaseHelper.IMG_PATH_COLUMN));
            modelNotes.add(new ModelNote(DataFromDB.titleNote, DataFromDB.contentNote,
                    DataFromDB.lastUpdateData, DataFromDB.imgPath));
        }
        return modelNotes;
    }

    public void openCursor(Cursor cursor) {
        while (cursor.moveToNext()) {
            DataFromDB.id = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.ID_COLUMN));
            DataFromDB.titleNote = cursor.getString(cursor.getColumnIndex(DataBaseHelper.TITLE_NOTES_COLUMN));
            DataFromDB.contentNote = cursor.getString(cursor.getColumnIndex(DataBaseHelper.CONTENT_COLUMN));
            DataFromDB.lastUpdateData = cursor.getString(cursor.getColumnIndex(DataBaseHelper.LAST_UPDATE_DATE_COLUMN));
            DataFromDB.imgPath = cursor.getString(cursor.getColumnIndex(DataBaseHelper.IMG_PATH_COLUMN));
        }
    }

    public List<ModelNote> initializeData(DataBaseHelper dataBaseHelper,
                                          SQLiteDatabase sqLiteDatabase, String query, String[] where) {
        //modelNotes = new ArrayList<ModelNote>();
        //dataBaseHelper = new DataBaseHelper(context);
        sqLiteDatabase = dataBaseHelper.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(query, where);
        return openCursor(cursor, new ArrayList<ModelNote>());
    }

    public RecyclerView initRecyclerView(LinearLayoutManager linearLayoutManager, RecyclerView recyclerView,
                                         RecyclerView.Adapter<?> adapter) {
        //linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        return recyclerView;
    }
}
