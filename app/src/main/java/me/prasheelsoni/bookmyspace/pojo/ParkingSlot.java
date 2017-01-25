 package me.prasheelsoni.bookmyspace.pojo;

        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

        public class ParkingSlot {

    @SerializedName("bookedSlots")
    @Expose
    private String bookedSlots;
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
    @SerializedName("totalSlots")
    @Expose
    private String totalSlots;
    @SerializedName("type")
    @Expose
    private String type;

    public String getBookedSlots() {
        return bookedSlots;
    }

    public void setBookedSlots(String bookedSlots) {
        this.bookedSlots = bookedSlots;
    }

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoc() {
        return poc;
    }

    public void setPoc(String poc) {
        this.poc = poc;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getTotalSlots() {
        return totalSlots;
    }

    public void setTotalSlots(String totalSlots) {
        this.totalSlots = totalSlots;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}