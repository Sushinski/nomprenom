package com.nomprenom2.presenter;

import android.content.Context;

import com.nomprenom2.model.NameRecord;
import java.util.ArrayList;
import java.util.List;


public abstract class AbsPresenter{
    private Context context;
    AbsPresenter(Context c){
        context = c;
    }

    public List<NameRecord> getNames(String[] groups, int sex, int zod ){
        return new ArrayList<>();
    }

    public List<NameRecord> getSuggestions(String name_part, String[] regions, int sex, int zod){
       return getNames( regions, sex, zod );
    }

}
