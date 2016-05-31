package com.nomprenom2.utils;
import android.content.Context;
import android.content.DialogInterface;
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
import java.util.List;


public class SelectedNameAdapter extends ArrayAdapter<String> implements View.OnClickListener {
    private List<SelectedName> name_list;
    private Context context;
    private String patronymic;
    private boolean is_male;
    private int zod;
    private NamePatrComp comp;
    int tw_id;

    public SelectedNameAdapter(Context context, int textViewResourceId,
                           List<String> nameList, String patronymic, boolean isMale, int zod) {
        super(context, textViewResourceId, nameList);
        this.name_list = new ArrayList<>();
        for (String s : nameList) {
            name_list.add(new SelectedName(s,false));
        }
        this.tw_id = textViewResourceId;
        this.context = context;
        this.patronymic = patronymic;
        this.comp = new NamePatrComp(context);
        this.is_male = isMale;
    }

    private class ViewHolder{
        TextView name;
        TextView patronymic;
        TextView compl;
        CheckBox selector;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater li = (LayoutInflater)context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(this.tw_id, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.patronymic = (TextView) convertView.findViewById(R.id.patronymic);
            holder.selector = (CheckBox) convertView.findViewById(R.id.checkBox1);
            holder.compl = (TextView) convertView.findViewById(R.id.compl);
            convertView.setTag(holder);
            holder.selector.setOnClickListener(this);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        SelectedName sn = name_list.get(position);
        holder.name.setText(sn.getName());
        holder.patronymic.setText(this.patronymic);
        holder.selector.setChecked(sn.isSelected());
        String c = Integer.toString(comp.compare(sn.getName(), this.patronymic, this.is_male));
        holder.compl.setText(c);
        holder.selector.setTag(sn);
        return convertView;
    }

    @Override
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
}
