package com.nomprenom2.utils;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.nomprenom2.R;
import com.nomprenom2.interfaces.IListItemDeleter;
import com.nomprenom2.model.SelectedName;

import java.util.ArrayList;


public class SelectedNameAdapter extends ArrayAdapter<String> {
    private ArrayList<SelectedName> name_list;
    private Context context;

    public SelectedNameAdapter(Context context, int textViewResourceId,
                           ArrayList<String> nameList) {
        super(context, textViewResourceId, nameList);
        this.name_list = new ArrayList<>();
        for (String s : nameList) {
            name_list.add(new SelectedName(s,false));
        }
        this.context = context;
    }


    private class ViewHolder{
        TextView name;
        CheckBox selector;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater li = (LayoutInflater)context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.name_list_item, parent, false);

            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.selector = (CheckBox) convertView.findViewById(R.id.checkBox1);
            convertView.setTag(holder);

            holder.selector.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    SelectedName nm = (SelectedName) cb.getTag();
                    SelectedNameAdapter.this.remove(nm.getName());
                    SelectedNameAdapter.this.name_list.remove(nm);
                    SelectedNameAdapter.this.notifyDataSetChanged();
                    IListItemDeleter deleter = (IListItemDeleter)getContext();
                    if( deleter != null)
                        deleter.onDeleteListItem(nm.getName());
                }
            });
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        SelectedName sn = name_list.get(position);
        holder.name.setText(sn.getName());
        holder.selector.setChecked(sn.isSelected());
        holder.selector.setTag(sn);
        return convertView;
    }
}
