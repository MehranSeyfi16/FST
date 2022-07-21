import java.util.ArrayList;

public class State {
    public String stateName;
    public int stateNum;
    public boolean isFinal;
    public static ArrayList<State> states = new ArrayList<>();

    public State(String stateName, boolean isFinal, int stateNum) {
        this.stateName = stateName;
        this.isFinal = isFinal;
        this.stateNum = stateNum;
    }
}
