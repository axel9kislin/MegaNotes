package ru.Artem.meganotes.app.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.mikepenz.iconics.IconicsDrawable;
import ru.Artem.meganotes.app.Models.ModelTypeApp;
import ru.Artem.meganotes.app.R;

import java.util.List;

/**
 * Created by Артем on 09.04.2016.
 */
public class FragmentDialogAdapter extends ArrayAdapter<ModelTypeApp> {
    private List<ModelTypeApp> modelTypeApps;
    private LayoutInflater inflater;
    private Context context;
    public FragmentDialogAdapter(Context context, List<ModelTypeApp> objects) {
        super(context, R.layout.item_fragment_dialog, objects);
        this.context = context;
        this.modelTypeApps = objects;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    static class ViewHolder {
        public ImageView icoApp;
        public TextView nameApp;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        View view = convertView;
        if(view == null) {
            view = inflater.inflate(R.layout.item_fragment_dialog, parent, false);
            ModelTypeApp modelTypeApp = getItem(position);
            holder = new ViewHolder();

            holder.nameApp = (TextView) view.findViewById(R.id.appName);
            holder.icoApp = (ImageView) view.findViewById(R.id.icoApp);

            holder.nameApp.setText(modelTypeApp.getNameApp());
            Drawable drawable = new IconicsDrawable(context)
                    .icon(modelTypeApp.getIco())
                    .color(context.getResources().getColor(R.color.material_drawer_dark_primary_icon))  //R.color.material_drawer_dark_primary_icon
                    .sizeDp(24);
            holder.icoApp.setImageDrawable(drawable);
        }
        return view;
    }
}
