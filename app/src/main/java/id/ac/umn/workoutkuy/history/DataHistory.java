package id.ac.umn.workoutkuy.history;

import java.io.Serializable;

public class DataHistory implements Serializable {
    String date;
    String date2;

    public DataHistory(String date, String date2) {
        this.date = date;
        this.date2 = date2;
        System.out.println(date2);
    }

    public String getDate() {
        return date;
    }

    public String getDate2() {
        return date2;
    }


}
