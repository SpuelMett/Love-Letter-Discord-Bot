package GameHandling;

public class CommandHandler {


    private final Player currentPlayer;
    private final Command command;

    public CommandHandler(Player player, Command command){
        this.command = command;
        this.currentPlayer = player;
    }

    /**
     * redirects the process to the right method.
     * @return
     */
    public String processCommand(){
        if(command == null){
            return "I don't know what you want.";
        }
        String commandString = command.getCommand();
        if(commandString.equals("go")) return go();


        return "I don't know what you want.";
    }
}
