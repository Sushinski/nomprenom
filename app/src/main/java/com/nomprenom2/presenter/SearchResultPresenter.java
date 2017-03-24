package com.nomprenom2.presenter;

import android.content.Context;
import com.nomprenom2.model.NameRecord;
import java.util.Iterator;
import java.util.List;


public class SearchResultPresenter extends AbsPresenter{
    public SearchResultPresenter(Context c){
        super(c);
    }

    public void onOk(){
    }

    public List<NameRecord> getNames(String[] groups, int sex, int zod ){
        return NameRecord.getNames(groups, sex, zod);
    }

    public List<NameRecord> getSuggestions(String name_part, String[] regions, int sex, int zod){
        List<NameRecord> n_l = NameRecord.getNames(regions, sex, zod); //[todo] cache candidate
        for (Iterator<NameRecord> nr = n_l.iterator(); nr.hasNext(); ) {
            if( !nr.next().name.regionMatches(true, 0, name_part, 0, name_part.length()))
                nr.remove();
        }
        return n_l;
    }

    public NameRecord getLastName(){
        return NameRecord.getLast();
    }
}
