package ru.Artem.meganotes.app.Adapters;

import android.content.ClipData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import ru.Artem.meganotes.app.Fragments.DeleteFragment;
import ru.Artem.meganotes.app.Models.ModelNote;
import ru.Artem.meganotes.app.R;

import java.io.IOException;
import java.util.List;

/**
 * Created by Артем on 16.04.2016.
 */
public class DeleteFragmentAdapter extends RecyclerView.Adapter<DeleteFragmentAdapter.DeleteNoteViewHolder> {


    private List<ModelNote> modelNotes;
    private ModelNote modelNote;
    private Context mainActivityContext;
    private MainAdapter mainAdapter;
   // private OnItemCheckListener onItemCheckListener;

    public class DeleteNoteViewHolder extends RecyclerView.ViewHolder {

        private TextView lastUpdateNoteDel;
        private ImageView imgNoteDel;
        private TextView nameNoteDel;
        private CheckBox checkBox;

        public DeleteNoteViewHolder(View itemView) {
            super(itemView);
            nameNoteDel = (TextView) itemView.findViewById(R.id.nameNoteDel);
            lastUpdateNoteDel = (TextView) itemView.findViewById(R.id.lastUpdateNoteDel);
            imgNoteDel = (ImageView) itemView.findViewById(R.id.imgNoteDel);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
        }
    }

    public DeleteFragmentAdapter(List<ModelNote> modelNotes, Context mainActivityContext) {//, @NonNull OnItemCheckListener onItemCheckListener){
        this.modelNotes = modelNotes;
        this.mainActivityContext = mainActivityContext;
        this.mainAdapter = new MainAdapter(modelNotes, mainActivityContext);
        //this.onItemCheckListener = onItemCheckListener;
    }

    @Override
    public DeleteNoteViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View myView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_delete, viewGroup, false);
        DeleteNoteViewHolder deleteNoteViewHolder = new DeleteNoteViewHolder(myView);

        return deleteNoteViewHolder;
    }

    @Override
    public void onBindViewHolder(final DeleteNoteViewHolder holder, final int position) {
        holder.lastUpdateNoteDel.setText(modelNotes.get(position).getLastUpdateNote());
        holder.nameNoteDel.setText(modelNotes.get(position).getNameNote());

        try {
            holder.imgNoteDel.setImageBitmap(mainAdapter.scaleImg(position));
        } catch (IOException ex) {

        }
/*
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkBox.isChecked())
                    onItemCheckListener.onItemCheck(position);
                else
                    onItemCheckListener.onItemUncheck(position);
                //modelNotes.get(position).setSelect(holder.checkBox.isChecked());

            }
        });
        Log.d("123", " + " + modelNotes.get(position).isSelect());
        for (ModelNote obj: modelNotes) {
            if (obj.isSelect()) {
                holder.checkBox.setChecked(true);
                Log.d("123", "lala");
            }
        }*/
    }

    @Override
    public int getItemCount() {
        return modelNotes.size();
    }

    public interface OnItemCheckListener {
        void onItemCheck(int position);
        void onItemUncheck(int position);
    }

}
