package com.nomprenom2.utils;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.nomprenom2.R;
import com.nomprenom2.interfaces.IListItemDeleter;
import com.nomprenom2.model.NameRecord;
import com.nomprenom2.model.SelectedName;
import com.nomprenom2.model.ZodiacRecord;

import java.util.ArrayList;
import java.util.List;


public class SelectedNameAdapter extends RecyclerView.Adapter<SelectedNameAdapter.ViewHolder>
        implements View.OnClickListener{
    private List<SelectedName> name_list;
    private Context context;
    private String patronymic;
    private NameRecord.Sex sex;
    private ZodiacRecord.ZodMonth zod;
    private NamePatrComp comp;
    int tw_id;

    public SelectedNameAdapter(Context context, int textViewResourceId,
                           List<String> nameList, String patronymic, String sex, String zod) {
        //super(context, textViewResourceId, nameList);
        this.context = context;
        this.name_list = new ArrayList<>();
        for (String s : nameList) {
            name_list.add(new SelectedName(s,false));
        }
        this.tw_id = textViewResourceId;
        this.context = context;
        this.patronymic = patronymic != null ? patronymic : "";
        this.comp = new NamePatrComp(context);
        this.sex = sex != null ? NameRecord.Sex.valueOf(sex) : null;
        this.zod = zod != null ? ZodiacRecord.ZodMonth.valueOf(zod) : null;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView patronymic;
        public TextView compl;
        public CheckBox selector;
        public ImageView zodiac_pic;

        public ViewHolder(View v){
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            patronymic = (TextView) v.findViewById(R.id.patronymic);
            selector = (CheckBox) v.findViewById(R.id.checkBox1);
            compl = (TextView) v.findViewById(R.id.compl);
            zodiac_pic = (ImageView) v.findViewById(R.id.zodiac_pic);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        ViewHolder holder;
        View v = LayoutInflater.from(parent.getContext())
                .inflate(this.tw_id, parent, false);
        holder = new ViewHolder(v);
        if( this.patronymic.isEmpty() || this.sex == null )
            holder.compl.setVisibility(View.GONE);
        //convertView.setTag(holder);
        holder.selector.setOnClickListener(this);
        return holder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        SelectedName sn = name_list.get(position);
        holder.name.setText(sn.getName());
        holder.patronymic.setText(this.patronymic);
        holder.selector.setChecked(sn.isSelected());
        if( this.sex != null ) {
            String c = Integer.toString(comp.compare(sn.getName(), this.patronymic, this.sex));
            holder.compl.setText(c);
        }
        holder.zodiac_pic.setImageResource(ZodiacRecord.getPicIdByMonth(this.zod));
        holder.selector.setTag(sn);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return name_list.size();
    }

    @Override
    public void onClick(View v) {
        CheckBox cb = (CheckBox) v;
        SelectedName nm = (SelectedName) cb.getTag();
        int pos = name_list.indexOf(nm);
        if(pos >= 0) {
            this.name_list.remove(nm);
            notifyItemRemoved(pos);
            IListItemDeleter deleter = (IListItemDeleter) this.context;
            if (deleter != null)
                deleter.onDeleteListItem(nm.getName());
        }
    }
}
