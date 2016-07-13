package com.nomprenom2.utils;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NavUtils;
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
import com.nomprenom2.view.NameDetailActivity;

import java.util.ArrayList;
import java.util.List;


public class SelectedNameAdapter extends RecyclerView.Adapter<SelectedNameAdapter.ViewHolder>
        implements View.OnClickListener{
    public final static String NAME = "com.nomprenom2.utils.name";
    public final static String NAME_DESCR = "com.nomprenom2.utils.name_descr";
    private List<SelectedName> name_list;
    private Context context;
    private String patronymic;
    private NameRecord.Sex sex;
    private ZodiacRecord.ZodMonth zod;
    private NamePatrComp comp;
    private int tw_id;
    private String info_prefx;

    public SelectedNameAdapter(Context context, int textViewResourceId,
                           List<String> nameList, String patronymic, String sex, String zod) {
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


    public void setInfoPrefx( String str ){
        info_prefx = str;
    }

    public String getInfoPrefix(){ return info_prefx; }

    public String getCompatibility(String name){
        return Integer.toString(comp.compare(name, this.patronymic, this.sex));
    }

    public void setTitle(String title){

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView patronymic;
        public TextView info;
        public CheckBox selector;

        public ViewHolder(View v){
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            patronymic = (TextView) v.findViewById(R.id.patronymic);
            selector = (CheckBox) v.findViewById(R.id.checkBox1);
            info = (TextView) v.findViewById(R.id.compl);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        ViewHolder holder;
        View v = LayoutInflater.from(parent.getContext())
                .inflate(this.tw_id, parent, false);
        holder = new ViewHolder(v);

        //if( this.patronymic.isEmpty() && this.sex == null && this.zod == null )
        //    holder.info.setVisibility(View.GONE);
        holder.selector.setOnClickListener(this);
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(v.getContext(), NameDetailActivity.class);
                TextView tw = (TextView) v;
                String n = tw.getText().toString();
                String d = NameRecord.getNameDescr(n);
                in.putExtra(NAME, n);
                if( d != null )
                    in.putExtra(NAME_DESCR, d);
                v.getContext().startActivity(in);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        SelectedName sn = name_list.get(position);
        holder.name.setText(sn.getName());
        holder.patronymic.setText(this.patronymic);
        holder.selector.setChecked(sn.isSelected());
        holder.info.setText(getInfoText(sn.getName()));
        holder.selector.setTag(sn);
    }

    public String getInfoText(String name){
        return "";
    }

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
