package com.nomprenom2.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ZodiacPojo {

    @SerializedName("zod_month")
    @Expose
    private int zodMonth;

    /**
     *
     * @return
     * The zodMonth
     */
    public int getZodMonth() {
        return zodMonth;
    }

    /**
     *
     * @param zodMonth
     * The zod_month
     */
    public void setZodMonth(int zodMonth) {
        this.zodMonth = zodMonth;
    }

}
