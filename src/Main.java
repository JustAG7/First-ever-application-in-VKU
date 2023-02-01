
import DBConnection.*;
import Form.*;
import javax.swing.*;
import java.sql.SQLException;
import java.lang.ClassNotFoundException;

public class Main {
    public static void main(String[] args) throws SQLException {
        try {
            SignIn dialog = new SignIn();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}