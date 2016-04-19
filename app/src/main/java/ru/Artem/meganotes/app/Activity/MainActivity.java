package ru.Artem.meganotes.app.Activity;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import ru.Artem.meganotes.app.Fragments.*;
import ru.Artem.meganotes.app.POJO.ListDrawer;
import ru.Artem.meganotes.app.R;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements
        MyDialogFragment.OnFragmentInteractionListener, MyDialogFragment.ShowDialog {


    private Fragment fragment = null;
    private Drawer drawer;
    private String uriPath;
    private Toolbar toolbar;
    private Spinner spinner;
    private InteractionWithFragment interactionWithFragment;
    private final String myTag = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        spinner = (Spinner) findViewById(R.id.spinner);

        List<String> spinnerList = new ArrayList<String>();

        spinnerList.add(getString(R.string.drawer_item_work));
        spinnerList.add(getString(R.string.drawer_item_home));
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, spinnerList);
        spinner.setAdapter(spinnerAdapter);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        createDrawer();
       if (savedInstanceState != null) {
            //Restore the fragment's instance
            fragment = getSupportFragmentManager().getFragment(savedInstanceState, "saveFragment");
            setFragment(fragment);
        } else {
           MainFragment mainFragment = new MainFragment();
           setFragment(mainFragment);
           //drawer.setSelection(5);
       }
    }

/*
    private int getScreenOrientation(){
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            return 0;
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            return 1;
        else
            return -1;
    }

    private int getWidthDrawer () {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        if (getScreenOrientation() == 0)
            return (int) dpWidth - getStatusBarHeight();
        else if (getScreenOrientation() == 1)
            return (int) dpHeight - getStatusBarHeight();
        return 0;
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
*/
    private void createDrawer() {
        drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withHeader(R.layout.drawer_header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_all).withIcon(GoogleMaterial.Icon.gmd_note),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_home).withIcon(GoogleMaterial.Icon.gmd_home),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_work).withIcon(GoogleMaterial.Icon.gmd_work),
                        new SectionDrawerItem().withName(getString(R.string.drawer_menu_manager)),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_create).withIcon(GoogleMaterial.Icon.gmd_create),
                        new PrimaryDrawerItem().withName(getString(R.string.drawer_item_delete)).withIcon(GoogleMaterial.Icon.gmd_delete),
                        new SectionDrawerItem().withName(R.string.drawer_menu_info),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_info).withIcon(GoogleMaterial.Icon.gmd_info))
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int i, IDrawerItem iDrawerItem) {

                        Log.d(myTag, "I: " + i);
                        switch (i) {
                            case ListDrawer.allPos:
                                fragment = new MainFragment();
                                break;
                            case ListDrawer.homePos:
                                fragment = new HomeFragment();
                                break;
                            case ListDrawer.workPos:
                                fragment = new WorkFragment();
                                break;
                            case ListDrawer.createPos:
                                fragment = new CreateNoteFragment();
                                break;
                            case ListDrawer.deletePos:
                                fragment = new DeleteFragment();
                                break;
                            case ListDrawer.aboutPos:
                                break;
                        }
                        setFragment(fragment);
                        drawer.closeDrawer();
                        return false;
                    }
                })
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.uploadImg) {
            MyDialogFragment myDialogFragment = new MyDialogFragment();
            Bundle args = new Bundle();
            args.putString("dialogKey", "camera/gallery");
            myDialogFragment.setArguments(args);
            myDialogFragment.show(getSupportFragmentManager(), "dialog");
        }
        else if (id == R.id.doneCreate) {
            //проверка на корректность введенных данных, запись в базу данных, открытие MainFragment
            CreateNoteFragment fragment = (CreateNoteFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);

            String titleNote = ((EditText) fragment.getView().findViewById(R.id.editTitleNote)).getText().toString();
            String contentNote = ((EditText) fragment.getView().findViewById(R.id.editContentNote)).getText().toString();
            if (!titleNote.isEmpty() && !contentNote.isEmpty()) {
                if (uriPath == null)
                    uriPath = "null";
                String date = CreateNoteFragment.getDate();
                if (drawer.getCurrentSelectedPosition() == ListDrawer.createPos)
                    CreateNoteFragment.addData(titleNote, contentNote, uriPath,
                            date, date, spinner.getSelectedItemPosition(),
                            getResources(), this);
                //Log.d(myTag, "uri: " + uriPath);
                MainFragment mainFragment = new MainFragment();
                setFragment(mainFragment);
                drawer.setSelectionAtPosition(ListDrawer.allPos);
            }

            return true;
        }
        else if (id == R.id.doneDelete) {
            interactionWithFragment = (InteractionWithFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
            interactionWithFragment.removeAll();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        /*addToBackPack = true;
        drawer.setSelectionAtPosition(tmpSelectedDrawerItem);*/
        if(drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //ft.addToBackStack(getString(R.string.back_stack));
        ft.replace(R.id.content_frame, fragment).commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getSupportFragmentManager().putFragment(outState, "saveFragment", getSupportFragmentManager().findFragmentById(R.id.content_frame));
        //Log.d(myTag, " " + getSupportFragmentManager().findFragmentById(R.id.content_frame));
        super.onSaveInstanceState(outState);
    }

    @Override
    public void getUriPath(String uri) {
        CreateNoteFragment createNoteFragment = (CreateNoteFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        createNoteFragment.setImg(uri);
        uriPath = uri;
    }

    @Override
    public void showDialog(MyDialogFragment myDialogFragment) {
        Bundle args = new Bundle();
        args.putString("dialogKey", "del");
        myDialogFragment.setArguments(args);
        myDialogFragment.show(getSupportFragmentManager(), "dialog");
    }

    public interface InteractionWithFragment {
        void removeAll();
    }
}
