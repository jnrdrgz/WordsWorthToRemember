package xyz.juanrodriguez.www.wordsworthtoremember3;

public class Settings {
    private int _id;
    private int notification_interval_minutes;
    private boolean notification_sound;

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
}
