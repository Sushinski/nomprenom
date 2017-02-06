package com.nomprenom2.model;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.From;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nomprenom2.R;

import java.util.ArrayList;
import java.util.List;

@Table(name = "ZodiacRecord", id = "_id")
public class ZodiacRecord extends Model {

    @SerializedName("zod_month")
    @Expose
    @Column(name="zod_month", unique = true)
    public int zod_month;

    @Column(name="zod_sign", notNull = true)
    public String zod_sign;

    public ZodiacRecord(){ super(); }

    public static ZodiacRecord getZodiacRec(ZodMonth month){
        int mon_id = month.getId();
        return new Select().
                from(ZodiacRecord.class).
                where("zod_month=?", mon_id).executeSingle();
    }

    public static String getMonthsForName(String name){
        List<String> str_res = new ArrayList<>();
        List<ZodiacRecord> res = getMonthsListForName(name);
        for ( ZodiacRecord z : res ) {
            str_res.add(ZodMonth.fromInt(z.zod_month)); // ordinal, not value
        }
        return TextUtils.join(", ", str_res);
    }

    public static List<ZodiacRecord> getMonthsListForName(String name){
        try {
            From sel = new Select().
                    from(ZodiacRecord.class).
                    where("ZodiacRecord._id in (select a.zodiac_id from NameZodiacRecord a " +
                            "inner join NameRecord b on a.name_id = b._id where b.name =\'" +
                            name + "\');");
            return  sel.execute();
        }catch (Exception e){
            return new ArrayList<>();
        }
    }

    public enum  ZodMonth{
        Capricorn(1),
        Aquarius(2),
        Pisces(3),
        Aries(4),
        Taurus(5),
        Gemini(6),
        Cancer(7),
        Leo(8),
        Virgo(9),
        Libra(10),
        Scorpio(11),
        Sagittarius(12);
        private final int month_id;
        static final ZodMonth[] vals = ZodMonth.values();
        ZodMonth( int id ){ this.month_id = id; }
        public final int getId(){ return month_id; }
        public static String fromInt(int val){
            return vals[val].toString();
        }
    }

    public static int getPicIdByMonth(@Nullable ZodMonth month){
        return R.drawable.virgo30;
        // todo get real pic
        // todo cast to month

    }
}
