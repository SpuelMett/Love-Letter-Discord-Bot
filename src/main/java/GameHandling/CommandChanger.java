package GameHandling;

import java.util.Arrays;

public class CommandChanger {

    //first word in line is the command
    private static final String[] join
            = {"join", "participate"};
    private static final String[] leave
            = {"leave"};
    private static final String[] score
            = {"score"};
    private static final String[] start
            = {"start"};
    private static final String[] play
            = {"play"};
    private static final String[] cardcount
            = {"cardcount"};

    /**
     * Converts the first word to the right command word.
     * @param input
     * @return CommandWord
     */
    public static String convertCommand(String input){
        if(Arrays.asList(join).contains(input)) return "join";
        if(Arrays.asList(leave).contains(input)) return "leave";
        if(Arrays.asList(score).contains(input)) return "score";
        if(Arrays.asList(start).contains(input)) return "start";
        if(Arrays.asList(play).contains(input)) return "play";
        if(Arrays.asList(play).contains(input)) return "play";

        //default
        return null;
    }
}
