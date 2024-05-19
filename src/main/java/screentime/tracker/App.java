package screentime.tracker;

import com.example.digitaldetox.model.User;

public class App {

    private String name;
    private int app_time;
    private int overall_time;
    private int accountID;

    public App(int accountID, String name, int app_time) {
        this.name = name;
        this.app_time = app_time;
        this.accountID = accountID;
    }


    public int getAccountID() {
        return accountID;
    }

    public String getName() {
        return name;
    }

    public int getTime() { return app_time; }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(int time) {
        this.app_time = time;
    }


}
