package com.nomprenom2.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;


public class ZodiacRecord extends Model {
    @Column(name="zod_month", unique = true)
    public int zod_month;

    @Column(name="name_id", onUpdate = Column.ForeignKeyAction.CASCADE,
            onDelete = Column.ForeignKeyAction.CASCADE)
    public NameRecord name_id;

    public enum ZodMonth{
        January(1),
        February(2),
        March(3),
        April(4),
        May(5),
        June(6),
        July(7),
        August(8),
        September(9),
        October(10),
        November(11),
        December(12);
        private final int month_id;
        ZodMonth( int id ){ this.month_id = id; }
        public int getId(){ return month_id; }
    }

}
