package BookLibrary;

public class invoice {
    private String amount;

    public boolean setAmount(String amount){
        String regex = "^[0-9]+$";
        if(!amount.matches(regex) || amount.isEmpty() || amount == null){
            return false;
        }
        return true;
    }
}
