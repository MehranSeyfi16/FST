import java.util.ArrayList;

public class Transition {
    public State inputState;
    public char input;
    public char output;
    public State outputState;
    public static String result;
    public static State currentState;
    public static ArrayList<Transition> transitions = new ArrayList<>();

    public Transition(State inputState, char input, char output, State outputState) {
        this.inputState = inputState;
        this.input = input;
        this.output = output;
        this.outputState = outputState;
    }
}
