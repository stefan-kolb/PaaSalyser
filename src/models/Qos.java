
package models;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Qos {

    @SerializedName("uptime")
    @Expose
	private String uptime;
    @SerializedName("compliance")
    @Expose
    private List<String> compliance = null;

    public double getUptime() {
        return Double.parseDouble(uptime);
    }

    public List<String> getCompliance() {
        return compliance;
    }

}