package xyz.juanrodriguez.www.wordsworthtoremember3;

public class Settings {
    private int _id;
    private int notification_interval_minutes;
    private boolean notification_sound;
    private int min_hour;
    private int max_hour;

    public Settings(){

    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_interval() {
        return notification_interval_minutes;
    }

    public void set_interval(int minutes) {
        minutes = notification_interval_minutes;
    }

    public int get_min_hour() { return min_hour;}

    public void set_min_hour(int min_hour) {
        this.min_hour = min_hour;
    }

    public int get_max_hour() { return max_hour;}

    public void set_max_hour(int max_hour) {
        this.max_hour = max_hour;
    }
}
