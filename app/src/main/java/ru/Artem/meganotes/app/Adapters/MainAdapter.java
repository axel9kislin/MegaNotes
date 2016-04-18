package ru.Artem.meganotes.app.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import ru.Artem.meganotes.app.Models.ModelNote;
import ru.Artem.meganotes.app.R;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by Артем on 07.04.2016.
 */


public class MainAdapter extends RecyclerView.Adapter<MainAdapter.NoteViewHolder> {

    OnItemClickListener onItemClickListener;
    OnLongItemClickListener onLongItemClickListener;
    private List<ModelNote> modelNotes;
    private Context mainActivityContext;


    public class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener {
        protected TextView nameNote;
        protected TextView lastUpdateNote;
        protected ImageView imgNote;

        public NoteViewHolder(View itemView) {
            super(itemView);
            nameNote = (TextView)itemView.findViewById(R.id.nameNote);
            lastUpdateNote = (TextView)itemView.findViewById(R.id.lastUpdateNote);
            imgNote = (ImageView)itemView.findViewById(R.id.imgNote);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(onItemClickListener != null) {
                onItemClickListener.OnItemClick(v, getPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if(onLongItemClickListener != null) {
                onLongItemClickListener.onLongItemClick(v, getPosition());
                return true;
            }
            return false;
        }
    }

    public MainAdapter(List<ModelNote> modelNotes, Context mainActivityContext){
        this.modelNotes = modelNotes;
        this.mainActivityContext = mainActivityContext;

    }

    public interface OnItemClickListener {
        void OnItemClick(View view, int position);
    }

    public interface OnLongItemClickListener {
        void onLongItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void SetOnItemLongClickListener(final OnLongItemClickListener onLongItemClickListener) {
        this.onLongItemClickListener = onLongItemClickListener;
    }

    @Override
    public int getItemCount() {
        return modelNotes.size();
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View myView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        return new NoteViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder noteViewHolder, int i) {
        noteViewHolder.nameNote.setText(modelNotes.get(i).getNameNote());
        noteViewHolder.lastUpdateNote.setText(modelNotes.get(i).getLastUpdateNote());

        if(!modelNotes.get(i).getPathImg().isEmpty()) {
            fetchDrawableOnThread(noteViewHolder, i);
        }

    }


    public void fetchDrawableOnThread (final NoteViewHolder noteViewHolder, final int i) {
        if (modelNotes.get(i).getBitmap() != null) {
            noteViewHolder.imgNote.setImageBitmap(modelNotes.get(i).getBitmap());
        }


        final android.os.Handler handler = new android.os.Handler() {
            @Override
            public void handleMessage(Message msg) {

                final Bitmap image = (Bitmap) msg.obj;
                noteViewHolder.imgNote.setImageBitmap(image);

            }
        };

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    modelNotes.get(i).setBitmap(scaleImg(i));
                    final Message message = handler.obtainMessage(1, modelNotes.get(i).getBitmap());
                    Log.d("mylog", "BIT: " + modelNotes.get(i).getBitmap());
                    handler.sendMessage(message);
                } catch (IOException ex) {

                }
            }
        });
        thread.start();
    }

    public Bitmap scaleImg(int position) throws IOException {
        InputStream inputStream;
        InputStream inputStreamScale;
        inputStream = mainActivityContext.getContentResolver()
                .openInputStream(Uri.parse(modelNotes.get(position).getPathImg()));

        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;

        BitmapFactory.decodeStream(inputStream, null, onlyBoundsOptions);

        onlyBoundsOptions.inSampleSize = calculateInSampleSize(onlyBoundsOptions, 128, 90);
        onlyBoundsOptions.inJustDecodeBounds = false;

        inputStreamScale = mainActivityContext.getContentResolver()
                .openInputStream(Uri.parse(modelNotes.get(position).getPathImg()));

        return BitmapFactory.decodeStream(inputStreamScale, null, onlyBoundsOptions);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        int tmp = 0;

        if (width > height){
            tmp = height;
            height = width;
            width = tmp;
        }
        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}