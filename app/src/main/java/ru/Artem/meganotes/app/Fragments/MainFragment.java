package ru.Artem.meganotes.app.Fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.*;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import ru.Artem.meganotes.app.Activity.DetailedActivity;
import ru.Artem.meganotes.app.Activity.MainActivity;
import ru.Artem.meganotes.app.Adapters.MainAdapter;
import ru.Artem.meganotes.app.Models.DataFromDB;
import ru.Artem.meganotes.app.Models.ModelNote;
import ru.Artem.meganotes.app.DataBaseHelper.DataBaseHelper;
import ru.Artem.meganotes.app.POJO.HelpClass;
import ru.Artem.meganotes.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */

public class MainFragment extends Fragment implements MyDialogFragment.CallBack {

    protected List<ModelNote> modelNotes;
    private MainAdapter adapter;
    protected RecyclerView recyclerView;
    protected DataBaseHelper dataBaseHelper;
    protected SQLiteDatabase sqLiteDatabase;
    protected View myView;
    private Toolbar toolbar;
    private final String query = "select " + DataBaseHelper.TITLE_NOTES_COLUMN + ", "
            + DataBaseHelper.CONTENT_COLUMN + ", " + DataBaseHelper.LAST_UPDATE_DATE_COLUMN + ", "
            + DataBaseHelper.IMG_PATH_COLUMN + " from "
            + DataBaseHelper.DATABASE_TABLE;
    MyDialogFragment.ShowDialog activity_showDialog;
    //InteractionWithActivity interactionWithActivity;

    private HelpClass helpClass = new HelpClass();

    public MainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        dataBaseHelper = new DataBaseHelper(getActivity().getApplicationContext());
        modelNotes = helpClass.initializeData(dataBaseHelper,
                dataBaseHelper.getReadableDatabase(), query, null);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_main, container, false);
        adapter = new MainAdapter(modelNotes, getActivity().getApplicationContext());
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        //toolbar.inflateMenu(R.menu.menu_main);
        //toolbar.setTitle(getString(R.string.drawer_item_all));

        recyclerView = (RecyclerView) myView.findViewById(R.id.recyclerView);
        recyclerView = helpClass.initRecyclerView(new LinearLayoutManager(getActivity().getApplicationContext()), recyclerView, adapter);

        adapter.SetOnItemClickListener(new MainAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                Intent intent = new Intent(getActivity().getApplicationContext(), DetailedActivity.class);
                TextView nameNote = (TextView) view.findViewById(R.id.nameNote);
                Log.d("mylogs", nameNote.getText().toString());
                intent.putExtra("nameNote", nameNote.getText());
                startActivity(intent);
            }
        });

        adapter.SetOnItemLongClickListener(new MainAdapter.OnLongItemClickListener() {
            @Override
            public void onLongItemClick(View view, int position) {
                TextView nameNote = (TextView) view.findViewById(R.id.nameNote);
                HelpClass.nameNote = nameNote.getText().toString();
                HelpClass.position = position;
                activity_showDialog.showDialog(new MyDialogFragment());
            }
        });
        return myView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try
        {
            activity_showDialog = (MyDialogFragment.ShowDialog)activity;
        }
        catch(ClassCastException e)
        {
            Log.e(this.getClass().getSimpleName(), "ShowDialog interface needs to be     implemented by Activity.", e);
            throw e;
        }

    }

/*
    public interface InteractionWithActivity {
        void callFragmentNoteEdit(EditNoteFragment editNoteFragment);
    }
*/
    @Override
    public void del() {
        //Toast.makeText(getActivity().getApplicationContext(), "123", Toast.LENGTH_LONG).show();
        sqLiteDatabase = dataBaseHelper.getWritableDatabase();
        sqLiteDatabase.delete(DataBaseHelper.DATABASE_TABLE,
                DataBaseHelper.TITLE_NOTES_COLUMN  + " = ?", new String[]{HelpClass.nameNote});
        modelNotes.remove(helpClass.position);
        adapter.notifyItemRemoved(helpClass.position);
    }

}
