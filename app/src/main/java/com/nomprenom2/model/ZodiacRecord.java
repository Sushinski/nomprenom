/*
 * created by Pavel Golubev golubev.pavel.spb@gmail.com
 * no license applied
 * You may use this file without any restrictions
 */

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

import java.util.ArrayList;
import java.util.List;

/**
 * Represents name`s zodiac sign
 */
@Table(name = "ZodiacRecord", id = "_id")
public class ZodiacRecord extends Model {

    @SerializedName("zod_month")
    @Expose
    @Column(name = "zod_month", unique = true)
    public int zod_month;

    @Column(name = "zod_sign", notNull = true)
    public String zod_sign;

    public ZodiacRecord() {
        super();
    }

    public static ZodiacRecord getZodiacRec(Integer mon_id) {
        return new Select().
                from(ZodiacRecord.class).
                where("zod_month=?", mon_id).executeSingle();
    }

    /**
     * Gets localized month string representation for certain name
     *
     * @param name        Name to get zodiac sign for
     * @param translation Array of zodiac signs titles for current app locale
     * @return Localized sring representation of zodiacal sign
     */
    public static String getMonthsForName(String name, @Nullable String[] translation) {
        List<String> str_res = new ArrayList<>();
        List<ZodiacRecord> res = getMonthsListForName(name);
        for (ZodiacRecord z : res) {
            str_res.add(translation == null ? ZodMonth.fromInt(z.zod_month - 1) :
                    translation[z.zod_month - 1]); // ordinal, not value
        }
        return TextUtils.join(", ", str_res);
    }

    /**
     * Gets list of zodiac model objects for certain name
     *
     * @param name Name to get zodiacs for
     * @return List of zodiac model objects
     */
    private static List<ZodiacRecord> getMonthsListForName(String name) {
        return new Select().
                from(ZodiacRecord.class).
                where("ZodiacRecord._id in (select a.zodiac_id from NameZodiacRecord a " +
                        "inner join NameRecord b on a.name_id = b._id where b.name =\'" +
                        name + "\');").execute();
    }

    /**
     * Zodiacal signs enumeration
     */
    public enum ZodMonth {
        Aries(1),
        Taurus(2),
        Gemini(3),
        Cancer(4),
        Leo(5),
        Virgo(6),
        Libra(7),
        Scorpio(8),
        Sagittarius(9),
        Capricorn(10),
        Aquarius(11),
        Pisces(12);
        private final int month_id;
        static final ZodMonth[] vals = ZodMonth.values();

        ZodMonth(int id) {
            this.month_id = id;
        }

        public final int getId() {
            return month_id;
        }

        public static String fromInt(int val) {
            return vals[val].toString();
        }
    }
}
