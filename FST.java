import java.util.ArrayList;
import java.util.List;

class Graph {
    public static int s ;
    public static int d ;
    public static ArrayList<List> allPathes = new ArrayList<List>();
    public static String result = "";
    private int v;
    private ArrayList<Integer>[] adjList;
    public Graph(int vertices) {
        this.v = vertices;
        initAdjList();
    }

    @SuppressWarnings("unchecked")
    private void initAdjList() {
        adjList = new ArrayList[v];

        for (int i = 0; i < v; i++) {
            adjList[i] = new ArrayList<>();
        }
    }

    public void addEdge(int u, int v) {
        adjList[u].add(v);
    }

    public void printAllPaths(int s, int d,String input) {
        boolean[] isVisited = new boolean[v];
        ArrayList<Integer> pathList = new ArrayList<>();
        pathList.add(s);
        printAllPathsUtil(s, d, isVisited, pathList, input);
    }

    public void parseInput(String input){
        printAllPaths(s,d,input);
    }

    public void printAllPathsUtil(Integer u, Integer d,
                                  boolean[] isVisited,
                                  List<Integer> localPathList, String input) {


        if (u.equals(d)) {
            int k = 0;
            int charAt = 0;
            State currentState = null;
            State nextSate = null;
            System.out.println(localPathList);

            for (int i = 0; i < localPathList.size(); i++) {
                for (int j = 0; j < State.states.size(); j++) {
                    if (State.states.get(j).stateNum == localPathList.get(i)) {
                        currentState = State.states.get(j);
//                        continue;
                    }
                    if (i+1<localPathList.size()) {
                        if (State.states.get(j).stateNum == localPathList.get(i + 1)) {
                            nextSate = State.states.get(j);
//                            break;
                        }
                    }
                }
//
                try {
                    ArrayList<Transition> currentTransition = findTransitions(currentState, currentState);
                    for (int j = 0; j <currentTransition.size() ; j++) {
                        if (charAt == input.length()-1 && currentTransition.get(j).input == '?') {
                            result += currentTransition.get(j).output;
                            break;
                        }
                        if (input.charAt(charAt) == currentTransition.get(j).input) {
                            result += currentTransition.get(j).output;
                            if (charAt == input.length()-1 && currentTransition.get(j).outputState.isFinal){
                                System.out.println("Accept");
                                System.out.println("Result: "+result);
                                break;
                            }else {
                                charAt++;
                            }

                        }
                        else if (currentTransition.get(j).input == '?') {
                            result += currentTransition.get(j).output;
                            charAt++;
                        }
                    }

                } catch (Exception e) {
                    System.out.println("Reject");
                }
                try {
                    ArrayList<Transition> currentTransition = findTransitions(currentState, nextSate);
                    int counter=0;
                    for (int j = 0; j < currentTransition.size(); j++) {
//                        if (charAt == input.length() && currentTransition.get(j).input == '?' && currentTransition.get(j).outputState.isFinal) {
//                            result += currentTransition.get(j).output;
//                            System.out.println(result);
//                            break;
//                        }
                        if (input.charAt(charAt) == currentTransition.get(j).input) {
                            if (currentTransition.get(j).output != '?') {
                                result += currentTransition.get(j).output;

                            }
                            if (charAt == input.length() - 1 && currentTransition.get(j).outputState.isFinal) {
                                System.out.println("Accept");
                                System.out.println("Accepted Result: " + result);
                                break;
                            } else {
                                charAt++;
                                if (charAt >= input.length()) {
                                    System.out.println("Accept");
                                    System.out.println("Accepted Result: " + result);
                                    break;
                                }
                            }
                        } else if (currentTransition.get(j).input == '?') {
                            result += currentTransition.get(j).output;
//                            charAt++;

                        }
//                        else if (input.charAt(charAt) != currentTransition.get(j).input){
//                            counter++;
//                            if (counter==currentTransition.size()){
//                                System.out.println("Reject");
//                                result="";
//                                return;
//                            }
//                        }
                    }

                }catch (Exception e) {
                    System.out.println("Reject");
                }

            }
            result= "";
            return;
        }
        isVisited[u] = true;
        for (Integer i : adjList[u]) {
            if (!isVisited[i]) {
                localPathList.add(i);
                printAllPathsUtil(i, d, isVisited, localPathList, input);
                localPathList.remove(i);
            }
        }
        isVisited[u] = false;
    }

    public ArrayList<Transition> findTransitions(State currentState, State nextState) {
        ArrayList<Transition> transitionArrayList = new ArrayList<>();
        for (int i = 0; i < Transition.transitions.size(); i++) {
            if (Transition.transitions.get(i).inputState == currentState && Transition.transitions.get(i).outputState == nextState) {
                transitionArrayList.add(Transition.transitions.get(i));
            }
        }
        return transitionArrayList;
    }



    public static void addState(String stateName , boolean isFinal,int stateNum){
        State.states.add(new State(stateName,isFinal,stateNum));
    }

    public static void addTransition(String inputState, char input, char output, String outputState){
        State firstStateTransition = null;
        State secondStateTransition = null;
        for (int i = 0; i < State.states.size(); i++) {
            if (State.states.get(i).stateName.equals(inputState)){
                firstStateTransition = State.states.get(i);
            }if (State.states.get(i).stateName.equals(outputState)){
                secondStateTransition = State.states.get(i);
            }
        }
        Transition.transitions.add(new Transition(firstStateTransition, input, output, secondStateTransition));
    }

    public static void addSetTransition(String inputState, String inputSet, String outputState){
        for (int i = 0; i < inputSet.length(); i++) {
            addTransition(inputState, inputSet.charAt(i), inputSet.charAt(i), outputState);
        }
    }

    public static void main(String[] args) {
        Graph g = new Graph(6);
        /*Test Case*/
        addState("q0",false,0);
        addState("q1",false,1);
        addState("q2",true,2);
        addState("q3",false,3);
        addState("q4",false,4);
        addState("q5",false,5);

        addTransition("q0",'a','?',"q1");
        addTransition("q1",'s','d',"q2");
        addTransition("q0",'a','e',"q3");
        addTransition("q0",'a','q',"q3");
        addTransition("q3",'s','x',"q4");
        addTransition("q4",'s','y',"q4");
        addTransition("q0",'a','q',"q5");
        addTransition("q5",'s','b',"q2");


        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(0, 3);
        g.addEdge(3,4);
        g.addEdge(4,2);
        g.addEdge(0,5);
        g.addEdge(5,2);

        /* Morphological Generation */
//        addState("q0",false,0);
//        addState("q1",false,1);
//        addState("q2",false,2);
//        addState("q3",false,3);
//        addState("q4",false,4);
//        addState("q5",false,5);
//        addState("q6",false,6);
//        addState("q7",false,7);
//        addState("q8",false,8);
//        addState("q9",false,9);
//        addState("q10",false,10);
//        addState("q11",false,11);
//        addState("q12",false,12);
//        addState("q13",false,13);
//        addState("q14",false,14);
//        addState("q15",false,15);
//        addState("q16",false,16);
//        addState("q17",false,17);
//        addState("q18",false,18);
//        addState("q19",false,19);
//        addState("q20",false,20);
//        addState("q21",false,21);
//        addState("q22",false,22);
//        addState("q23",false,23);
//        addState("q24",true,24);
//
//        addSetTransition("q0","maxbcdefghijklmnopqrstuvwxyz","q1");
//
//        addSetTransition("q1","szxo","q2");
//        addTransition("q2",'?','e',"q3");
//        addTransition("q3",'?','s',"q24");
//
//        addSetTransition("q1","aueio","q10");
//        addTransition("q10",'y','y',"q11");
//        addTransition("q11",'?','s',"q24");
//
//        addTransition("q1",'f','v',"q12");
//        addTransition("q12",'e','e',"q13");
//        addTransition("q13",'?','s',"q24");
//
//        addTransition("q12",'?','e',"q14");
//        addTransition("q14",'?','s',"q24");
//
//        addTransition("q1",'s','s',"q15");
//        addTransition("q15",'s','s',"q16");
//        addTransition("q16",'?','e',"q17");
//        addTransition("q16",'?','s',"q24");
//
//        addSetTransition("q1","abdefghijklmnopqrtuvwxyz","q18");
//        addTransition("q18",'h','h',"q19");
//        addTransition("q19",'?','s',"q24");
//
//
//        addSetTransition("q1","sc","q4");
//        addTransition("q4",'h','h',"q5");
//        addTransition("q5",'?','e',"q6");
//        addTransition("q6",'?','s',"q24");
//
//
//        addSetTransition("q1","bdfghjklmnpqrtvwxz","q7");
//        addTransition("q7",'y','i',"q8");
//        addTransition("q8",'?','e',"q9");
//        addTransition("q9",'?','s',"q24");
//
//        addSetTransition("q1","abcdeghijklmnopqrstuvwxyz","q20");
//        addTransition("q20",'e','e',"q21");
//        addTransition("q21",'?','s',"q24");
//
//        addSetTransition("q1","bdgjklmnpqrtvw","q22");
//        addTransition("q22",'?','s',"q24");
//
//        g.addEdge(0, 1);
//        g.addEdge(1, 2);
//        g.addEdge(2, 3);
//        g.addEdge(3, 24);
//
//        g.addEdge(1, 4);
//        g.addEdge(4, 5);
//        g.addEdge(5, 6);
//        g.addEdge(6, 24);
//
//        g.addEdge(1, 7);
//        g.addEdge(7, 8);
//        g.addEdge(8, 9);
//        g.addEdge(9, 24);
//
//        g.addEdge(1, 20);
//        g.addEdge(20, 21);
//        g.addEdge(21, 24);
//
//        g.addEdge(1, 22);
//        g.addEdge(22, 24);
//
//        g.addEdge(1, 10);
//        g.addEdge(10, 11);
//        g.addEdge(11, 24);
//
//        g.addEdge(1, 12);
//        g.addEdge(12, 13);
//        g.addEdge(13, 24);
//
//        g.addEdge(12, 14);
//        g.addEdge(14, 24);
//
//        g.addEdge(1, 15);
//        g.addEdge(15, 16);
//        g.addEdge(16, 17);
//        g.addEdge(17, 24);
//
//        g.addEdge(1, 18);
//        g.addEdge(18, 19);
//        g.addEdge(19, 24);

        s = 0;
        d = 2;

        g.parseInput("as");


//        g.printAllPaths(s, d, "asd");
    }
}


