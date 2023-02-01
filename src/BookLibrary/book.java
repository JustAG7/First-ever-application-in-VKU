package BookLibrary;

public class book {
    private String name;
    private String author;
    private String amount;

    public book(){

    }
    public book(String name, String author, String amount) {
        this.name = name;
        this.author = author;
        this.amount = amount;
    }
    public boolean setName(String name){
        String regex = "^[\\p{L}\\s]+$";
        if(name.matches(regex) && name.length() > 0){
            return true;
        }
        return false;
    }
    public boolean setAmount(String amount){
        String regex = "^[0-9]+$";
        if(!amount.matches(regex) || amount.isEmpty() || amount == null){
            return false;
        }
        return true;
    }
}
