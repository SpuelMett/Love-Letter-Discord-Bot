package GameHandling;

public class Command {

    private String command;
    private String secondWord;
    private String thirdWord;
    private String fourthWord;

    public Command(String command, String secondWord, String thirdWord, String fourthWord){
        this.command = command;
        this.secondWord = secondWord;
        this.thirdWord = thirdWord;
        this.fourthWord = fourthWord;
    }

    public String getCommand(){
        return command;
    }
    public String getSecondWord(){
        return secondWord;
    }
    public String getThirdWord(){
        return thirdWord;
    }
    public String getFourthWord(){
        return fourthWord;
    }

    public boolean hasSecondWord(){
        return secondWord != null;
    }
    public boolean hasThirdWord(){
        return thirdWord != null;
    }
    public boolean hasFourthWord(){
        return fourthWord != null;
    }
}
