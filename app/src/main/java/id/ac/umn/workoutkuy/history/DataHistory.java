package id.ac.umn.workoutkuy.history;

import java.io.Serializable;

public class DataHistory implements Serializable {
    String date;
    String date2;
    String time;

    public DataHistory(String date, String date2, String time) {
        this.date = date;
        this.date2 = date2;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public String getDate2() {
        return date2;
    }

    public String getTime() {
        return time;
    }
}
