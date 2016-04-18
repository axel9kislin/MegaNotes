package ru.Artem.meganotes.app.Models;

import android.graphics.drawable.Drawable;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;

/**
 * Created by Артем on 09.04.2016.
 */
public class ModelTypeApp {
    private String nameApp;
    private GoogleMaterial.Icon ico;

    public String getNameApp() {
        return nameApp;
    }

    public void setNameApp(String nameApp) {
        this.nameApp = nameApp;
    }

    public GoogleMaterial.Icon getIco() {
        return ico;
    }

    public void setIco(GoogleMaterial.Icon ico) {
        this.ico = ico;
    }

    public ModelTypeApp(String nameApp, GoogleMaterial.Icon ico) {

        this.nameApp = nameApp;
        this.ico = ico;
    }
}
