package com.nomprenom2.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.io.Serializable;
import java.util.List;

@Table(name = "GroupRecord", id="_id")
public class GroupRecord extends Model implements Parcelable {
    public static final String LIST_FIELD = "group_name";
    @Column(name="id", unique = true)
    public long id;

    @Column(name = "group_name", unique = true)
    public String group_name;

    public GroupRecord(){
        super();
    }

    public GroupRecord(String _name, long _id){
        super();
        id = _id;
        group_name = _name;
    }

    public GroupRecord(Parcel in){
        id = in.readLong();
        group_name = in.readString();
    }

    public static List<GroupRecord> getAll() {
        return new Select().all()
                .from(GroupRecord.class)
                .orderBy("group_name ASC")
                .execute();
    }

    public static GroupRecord getOne(){
        return new Select().from(GroupRecord.class).executeSingle();
    }

    @Override
    public String toString(){
        return group_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(group_name);
    }

    public static final Parcelable.Creator<GroupRecord> CREATOR = new Parcelable.Creator<GroupRecord>() {

        @Override
        public GroupRecord createFromParcel(Parcel source) {
            return new GroupRecord(source);
        }

        @Override
        public GroupRecord[] newArray(int size) {
            return new GroupRecord[size];
        }
    };
}
