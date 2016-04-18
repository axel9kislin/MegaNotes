package ru.Artem.meganotes.app.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ru.Artem.meganotes.app.DataBaseHelper.DataBaseHelper;
import ru.Artem.meganotes.app.Models.ModelNote;
import ru.Artem.meganotes.app.POJO.HelpClass;
import ru.Artem.meganotes.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Артем on 14.04.2016.
 */
public class HomeFragment extends MainFragment {

    private HelpClass helpClass = new HelpClass();
    private final String query = "select " + DataBaseHelper.TITLE_NOTES_COLUMN + ", "
            + DataBaseHelper.CONTENT_COLUMN + ", " + DataBaseHelper.LAST_UPDATE_DATE_COLUMN + ", "
            + DataBaseHelper.IMG_PATH_COLUMN + " from "
            + DataBaseHelper.DATABASE_TABLE + " where category_column = ?";

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] where = new String[] {String.valueOf(getString(R.string.drawer_item_home))};
        modelNotes = helpClass.initializeData(dataBaseHelper,
                dataBaseHelper.getReadableDatabase(), query, where);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
