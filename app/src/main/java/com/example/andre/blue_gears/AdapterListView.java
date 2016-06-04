package com.example.andre.blue_gears;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Andre on 6/1/2016.
 */
public class AdapterListView extends ArrayAdapter<ItemListView> {

    private Context context;
    private LayoutInflater mInflater = null;
    private ArrayList<ItemListView> itens;
    private SparseBooleanArray mSelectedItemsIds;

    public AdapterListView(Context context, int resourceId, ArrayList<ItemListView> itens) {
        super(context, resourceId, itens);
        mSelectedItemsIds = new SparseBooleanArray();
        this.context = context;
        this.itens = itens;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return itens.size();
    }

    @Override
    public ItemListView getItem(int position) {
        return itens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if(view==null) {
            holder = new ViewHolder();
            view = mInflater.inflate(R.layout.item_list, null);
            holder.name = (TextView)view.findViewById(R.id.name);
            holder.description = (TextView)view.findViewById(R.id.description);
            holder.datetime = (TextView)view.findViewById(R.id.txt_datetime);
            holder.datetime_selected = (TextView)view.findViewById(R.id.txt_datetime_selected);
            holder.picture = (ImageView)view.findViewById(R.id.picture);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        ItemListView item = new ItemListView();
        item = itens.get(position);

        holder.name.setText(item.getName());
        holder.description.setText(item.getDescription());
        holder.datetime.setText(item.getDatetime());
        holder.datetime_selected.setText(item.getDatetime_selected());
        if (item.getImgWeb() != null)
            new GetImg(holder.picture, itens.get(position)).execute(item.getImgWeb());
        else {
            if (item.getImg() != null)
                holder.picture.setImageBitmap(Utils.convertByteToBitmap(item.getImg()));
            else
                holder.picture.setImageResource(R.drawable.no_image);

        }
        return view;
    }

    private class ViewHolder {
        TextView name;
        TextView description;
        TextView datetime;
        TextView datetime_selected;
        ImageView picture;
    }

    @Override
    public void remove(ItemListView object) {
        itens.remove(object);
        notifyDataSetChanged();
    }

    public void addItemsList(ArrayList<ItemListView> items) {
        itens.addAll(items);
    }

    public List<ItemListView> getWorldPopulation() {
        return itens;
    }

    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }

    public void removeSelection(boolean downlaod, ArrayList<Integer> list) {
        mSelectedItemsIds = new SparseBooleanArray();
        if (!downlaod) {
            for (int i = 0; i < list.size(); i++) {
                itens.get(list.get(i)).setDatetime_selected(null);
            }
        }
        notifyDataSetChanged();
    }

    public void selectView(int position, boolean value) {
        if (value) {
            mSelectedItemsIds.put(position, value);
            itens.get(position).setDatetime_selected(Utils.getCurrentDateTime());
        }
        else
            mSelectedItemsIds.delete(position);
        notifyDataSetChanged();
    }

    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }
}
