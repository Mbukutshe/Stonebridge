package wiseman.stonebridge.Objects;

/**
 * Created by Wiseman on 2018-02-08.
 */

public class ClarityObject {
    public String time;
    public String message;
    public String who;
    public ClarityObject() {
        this.time = "";
        this.message = "";
        this.who = "";
    }

    public ClarityObject(String time, String message, String who) {
        this.time = time;
        this.message = message;
        this.who = who;
    }
}
