 package me.prasheelsoni.bookmyspace.pojo;

        import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ParkingSlot {

    @SerializedName("full")
    @Expose
    private String full;
    @SerializedName("gps")
    @Expose
    private String gps;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("poc")
    @Expose
    private String poc;
    @SerializedName("rate")
    @Expose
    private String rate;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("slots")
    @Expose
    private List<Slot> slots = new ArrayList<Slot>();

    /**
     *
     * @return
     * The full
     */
    public String getFull() {
        return full;
    }

    /**
     *
     * @param full
     * The full
     */
    public void setFull(String full) {
        this.full = full;
    }

    /**
     *
     * @return
     * The gps
     */
    public String getGps() {
        return gps;
    }

    /**
     *
     * @param gps
     * The gps
     */
    public void setGps(String gps) {
        this.gps = gps;
    }

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The poc
     */
    public String getPoc() {
        return poc;
    }

    /**
     *
     * @param poc
     * The poc
     */
    public void setPoc(String poc) {
        this.poc = poc;
    }

    /**
     *
     * @return
     * The rate
     */
    public String getRate() {
        return rate;
    }

    /**
     *
     * @param rate
     * The rate
     */
    public void setRate(String rate) {
        this.rate = rate;
    }

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
     * The slots
     */
    public List<Slot> getSlots() {
        return slots;
    }

    /**
     *
     * @param slots
     * The slots
     */
    public void setSlots(List<Slot> slots) {
        this.slots = slots;
    }

}



