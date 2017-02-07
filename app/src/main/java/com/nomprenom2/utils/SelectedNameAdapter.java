package com.nomprenom2.utils;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import com.nomprenom2.R;
import com.nomprenom2.interfaces.IListItemDeleter;
import com.nomprenom2.model.GroupRecord;
import com.nomprenom2.model.NameRecord;
import com.nomprenom2.model.ZodiacRecord;
import com.nomprenom2.view.NameDetailActivity;
import java.util.ArrayList;
import java.util.List;


public class SelectedNameAdapter extends RecyclerView.Adapter<SelectedNameAdapter.ViewHolder>
        implements View.OnClickListener{
    public final static String NAME = "com.nomprenom2.utils.name";
    public final static String NAME_DESCR = "com.nomprenom2.utils.name_descr";
    private List<NameRecord> name_list;
    private Context context;
    private String patronymic;
    private NameRecord.Sex sex;
    private ZodiacRecord.ZodMonth zod;
    private NamePatrComp comp;
    private int tw_id;
    private String info_prefx;
    private static String[] zodiac_repr_names;



    public Context getContext(){
        return this.context;
    }

    public SelectedNameAdapter(Context context, int textViewResourceId,
                           List<NameRecord> nameList, String patronymic, String sex, String zod) {
        this.context = context;
        this.name_list = new ArrayList<>();
        name_list = nameList;
        this.tw_id = textViewResourceId;
        this.context = context;
        this.patronymic = patronymic != null ? patronymic : "";
        this.comp = new NamePatrComp(context);
        this.sex = sex != null ? NameRecord.Sex.valueOf(sex) : null;
        this.zod = zod != null ? ZodiacRecord.ZodMonth.valueOf(zod) : null;
        zodiac_repr_names = getContext().getResources().getStringArray(R.array.zod_sels);
    }


    public void setInfoPrefx( String str ){
        info_prefx = str;
    }


    public String getInfoPrefix(){ return info_prefx; }


    public String getCompatibility(NameRecord name){
        return Integer.toString(comp.compare(name, this.patronymic, this.sex, this.zod));
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView name_info;
        public CheckBox selector;

        public ViewHolder(View v){
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            name_info = (TextView) v.findViewById(R.id.name_info);
            selector = (CheckBox) v.findViewById(R.id.checkBox1);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        ViewHolder holder;
        View v = LayoutInflater.from(parent.getContext())
                .inflate(this.tw_id, parent, false);
        holder = new ViewHolder(v);
        holder.selector.setOnClickListener(this);
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(v.getContext(), NameDetailActivity.class);
                TextView tw = (TextView) v;
                NameRecord nr = (NameRecord)tw.getTag();
                String n = tw.getText().toString();
                String d = getContext().getResources().getText(R.string.descr_sex) +
                        NameRecord.Sex.fromInt(nr.sex);
                String str = ZodiacRecord.getMonthsForName(nr.name, zodiac_repr_names);
                GroupRecord gr = GroupRecord.getGroupForName(nr);
                d += getContext().getResources().getString(R.string.region_repr) +
                        ( gr== null ? getContext().getResources().getString(R.string.unknown) : gr);
                d += "\n" + getInfoPrefix() +
                        (str.equals("") ? getContext().getResources()
                                .getString(R.string.unknown) : str);
                d += "\n\n" + (nr.description == null ?
                        getContext().getResources().getString(R.string.empty_name_descr) :
                        nr.description);
                in.putExtra(NAME, n);
                in.putExtra(NAME_DESCR, d);
                v.getContext().startActivity(in);
            }
        });
        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        NameRecord n = name_list.get(position);
        holder.name.setText(n.name);
        holder.selector.setChecked(n.selected == 1);
        holder.name_info.setText(getInfoText(n));
        holder.selector.setTag(n);
        holder.name.setTag(n);
    }


    public String getInfoText(NameRecord name){
        return "";
    }


    @Override
    public int getItemCount() {
        return name_list.size();
    }


    @Override
    public void onClick(View v) {
        CheckBox cb = (CheckBox) v;
        NameRecord nm = (NameRecord) cb.getTag();
        int pos = name_list.indexOf(nm);
        if(pos >= 0) {
            this.name_list.remove(nm);
            notifyItemRemoved(pos);
            IListItemDeleter deleter = (IListItemDeleter) this.context;
            if (deleter != null)
                deleter.onDeleteListItem(nm);
        }
    }
}
