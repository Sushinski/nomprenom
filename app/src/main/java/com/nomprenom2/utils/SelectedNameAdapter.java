/*
 * created by Pavel Golubev golubev.pavel.spb@gmail.com
 * no license applied
 * You may use this file without any restrictions
 */
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

/**
 * Data adapter for selected names list
 */
public class SelectedNameAdapter extends RecyclerView.Adapter<SelectedNameAdapter.ViewHolder>
        implements View.OnClickListener {
    public final static String NAME = "com.nomprenom2.utils.name";
    public final static String NAME_DESCR = "com.nomprenom2.utils.name_descr";
    public List<NameRecord> name_list;
    private Context context;
    protected String patronymic;
    private final NameRecord.Sex sex;
    private final ZodiacRecord.ZodMonth zod;
    private final NamePatrComp comp;
    private final int tw_id;
    private String info_prefx;
    private static String[] zodiac_repr_names;


    public Context getContext() {
        return this.context;
    }

    /**
     * Constructs adapter for parameters
     *
     * @param context
     * @param textViewResourceId view layout id
     * @param nameList           list of name model objects
     * @param patronymic         Patronymic  if applied
     * @param sex                Gender if applied
     * @param zod                Zodiacal sign if applied
     */
    public SelectedNameAdapter(Context context, int textViewResourceId,
                               List<NameRecord> nameList, String patronymic, int sex, int zod) {
        this.context = context;
        this.name_list = new ArrayList<>();
        name_list = nameList;
        this.tw_id = textViewResourceId;
        this.context = context;
        this.patronymic = patronymic != null ? patronymic : "";
        this.comp = new NamePatrComp(context);
        this.sex = sex > 0 ? NameRecord.Sex.valueOf(NameRecord.Sex.fromInt(sex - 1)) : null;
        this.zod = zod > 0 ?
                ZodiacRecord.ZodMonth.valueOf(ZodiacRecord.ZodMonth.fromInt(zod - 1)) :
                null;
        zodiac_repr_names = getContext().getResources().getStringArray(R.array.zod_sels);
        setInfoPrefx(context.getResources().getText(R.string.zod_pref).toString());
    }


    void setInfoPrefx(String str) {
        info_prefx = str;
    }

    /**
     * Gets name and patronymic compatibility
     *
     * @param name Name model object
     * @return
     */
    String getCompatibility(NameRecord name) {
        return Integer.toString(comp.compare(name, this.patronymic, this.sex, this.zod));
    }

    /**
     * Holds item fielda
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView name;
        final TextView name_info;
        final CheckBox selector;

        ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            name_info = (TextView) v.findViewById(R.id.name_info);
            selector = (CheckBox) v.findViewById(R.id.checkBox1);
        }
    }

    /**
     * Fills item with data and set click listeners
     *
     * @param parent   parent view group
     * @param viewType view type
     * @return View holder object
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder;
        View v = LayoutInflater.from(parent.getContext())
                .inflate(this.tw_id, parent, false);
        holder = new ViewHolder(v);
        holder.selector.setOnClickListener(this);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(v.getContext(), NameDetailActivity.class);
                TextView tw = (TextView) v;
                NameRecord nr = (NameRecord) tw.getTag();
                String nm = nr.name;
                String sx = getContext().getResources().getText(R.string.descr_sex) +
                        getContext().getResources().getStringArray(R.array.sex_sels)[nr.sex];
                String zd = ZodiacRecord.getMonthsForName(nr.name, zodiac_repr_names);
                if (!zd.isEmpty())
                    zd = getContext().getResources().getString(R.string.descr_zod) + zd;
                else
                    zd = getContext().getResources().getString(R.string.descr_zod) +
                            getContext().getResources().getString(R.string.unknown);
                GroupRecord g = GroupRecord.getGroupForName(nr);
                String gr = getContext().getResources().getString(R.string.region_repr) +
                        (g == null ? getContext().getResources().getString(R.string.unknown) :
                                g.group_name);
                String descr = sx + '\n' +
                        gr + '\n' +
                        zd + "\n\n" +
                        (nr.description == null ?
                                getContext().getResources().getString(R.string.empty_name_descr) :
                                nr.description);
                in.putExtra(NAME, nm);
                in.putExtra(NAME_DESCR, descr);
                v.getContext().startActivity(in);
            }
        };
        holder.name.setOnClickListener(listener);
        holder.name_info.setOnClickListener(listener);
        return holder;
    }

    /**
     * Binds namemodel object with currnt position to item fields
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        NameRecord n = name_list.get(position);
        holder.name.setText(n.name);
        holder.selector.setChecked(n.selected == 1);
        holder.name_info.setText(getInfoText(n));
        holder.selector.setTag(n);
        holder.name.setTag(n);
        holder.name_info.setTag(n);
    }


    public String getInfoText(NameRecord name) {
        return "";
    }


    @Override
    public int getItemCount() {
        return name_list.size();
    }

    /**
     * Processes checkbox click, delete item from list
     *
     * @param v check box clicked
     */
    @Override
    public void onClick(View v) {
        CheckBox cb = (CheckBox) v;
        NameRecord nm = (NameRecord) cb.getTag();
        int pos = name_list.indexOf(nm);
        if (pos >= 0) {
            this.name_list.remove(nm);
            notifyItemRemoved(pos);
            IListItemDeleter deleter = (IListItemDeleter) this.context;
            if (deleter != null)
                deleter.onDeleteListItem(nm);
        }
    }
}
