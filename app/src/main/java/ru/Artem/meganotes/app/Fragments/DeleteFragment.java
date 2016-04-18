package ru.Artem.meganotes.app.Fragments;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import ru.Artem.meganotes.app.Activity.MainActivity;
import ru.Artem.meganotes.app.Adapters.DeleteFragmentAdapter;
import ru.Artem.meganotes.app.Adapters.MainAdapter;
import ru.Artem.meganotes.app.DataBaseHelper.DataBaseHelper;
import ru.Artem.meganotes.app.Models.ModelNote;
import ru.Artem.meganotes.app.POJO.HelpClass;
import ru.Artem.meganotes.app.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Артем on 16.04.2016.
 */
public class DeleteFragment extends Fragment implements MainActivity.InteractionWithFragment {

    private Button buttonAll;
    private Toolbar toolbar;
    private SQLiteDatabase sqLiteDatabase;
    private DeleteFragmentAdapter adapter;
    private List<ModelNote> modelNotes;
    private RecyclerView recyclerView;
    private DataBaseHelper dataBaseHelper;
    private HelpClass helpClass = new HelpClass();

    private final String query = "select " + DataBaseHelper.TITLE_NOTES_COLUMN + ", "
            + DataBaseHelper.CONTENT_COLUMN + ", " + DataBaseHelper.LAST_UPDATE_DATE_COLUMN + ", "
            + DataBaseHelper.IMG_PATH_COLUMN + " from "
            + DataBaseHelper.DATABASE_TABLE;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
        dataBaseHelper = new DataBaseHelper(getActivity().getApplicationContext());
        modelNotes = helpClass.initializeData(dataBaseHelper,
                dataBaseHelper.getReadableDatabase(), query, null);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_delete, menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View myView = inflater.inflate(R.layout.fragment_main, container, false);
        adapter = new DeleteFragmentAdapter(modelNotes, getActivity().getApplicationContext());
        recyclerView = (RecyclerView) myView.findViewById(R.id.recyclerView);
        recyclerView = helpClass.initRecyclerView(new LinearLayoutManager(getActivity().getApplicationContext()), recyclerView, adapter);

        return myView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void removeAll() {

        sqLiteDatabase = dataBaseHelper.getWritableDatabase();
        sqLiteDatabase.delete(DataBaseHelper.DATABASE_TABLE, null, null);
        if(!modelNotes.isEmpty())
            adapter.notifyItemRangeRemoved(0, modelNotes.size());
        while(modelNotes.isEmpty()) {
            modelNotes.remove(modelNotes.size() - 1);
        }
    }
}
