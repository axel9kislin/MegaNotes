package ru.Artem.meganotes.app.Models;

import android.graphics.Bitmap;

/**
 * Created by Артем on 07.04.2016.
 */
public class ModelNote {
    private String nameNote;
    private String lastUpdateNote;
    private String pathImg;
    private String titleContent;
    private Bitmap bitmap;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    private boolean select;

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getNameNote() {
        return nameNote;
    }

    public void setNameNote(String nameNote) {
        this.nameNote = nameNote;
    }

    public String getLastUpdateNote() {
        return lastUpdateNote;
    }

    public void setLastUpdateNote(String lastUpdateNote) {
        this.lastUpdateNote = lastUpdateNote;
    }

    public String getPathImg() {
        return pathImg;
    }

    public void setPathImg(String pathImg) {
        this.pathImg = pathImg;
    }

    public ModelNote(String nameNote, String titleContent, String lastUpdateNote, String pathImg) {
        this.nameNote = nameNote;
        this.titleContent = titleContent;
        this.lastUpdateNote = lastUpdateNote;
        this.pathImg = pathImg;
    }
}
