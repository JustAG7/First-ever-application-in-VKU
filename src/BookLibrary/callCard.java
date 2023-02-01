package BookLibrary;

public class callCard {
    private String name;
    private String bname;
    private String phone;
    public callCard() {
        // TODO Auto-generated constructor stub
    }
    public callCard(String name, String bname, String phone) {
        this.name = name;
        this.bname = bname;
        this.phone = phone;
    }
    public boolean setName(String name) {
        String regex = "^[\\p{L}\\s]+$";
        if(name.matches(regex) && name.length() > 0){
            return true;
        }
        return false;
    }
    public boolean setPhone(String phone) {
        //regex for phone number with any length number
        String regex = "^[0-9]+$";
        if (phone == null || phone.isEmpty() || !phone.matches(regex)) {
            return false;
        }
        return true;
    }
}
