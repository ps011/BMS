package me.prasheelsoni.bookmyspace.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ps11 on 03/12/16.
 */

public class Slot {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("booked")
    @Expose
    private String booked;

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The booked
     */
    public String getBooked() {
        return booked;
    }

    /**
     *
     * @param booked
     * The booked
     */
    public void setBooked(String booked) {
        this.booked = booked;
    }

}