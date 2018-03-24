package wiseman.stonebridge.Objects;

/**
 * Created by Wiseman on 2018-02-27.
 */

public class QuoteObject {
    public String time;
    public String message;
    public String who;
    public QuoteObject(String time, String message, String who) {
        this.time = time;
        this.message = message;
        this.who = who;
    }
}
