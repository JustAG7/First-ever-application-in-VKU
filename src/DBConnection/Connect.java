package DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.lang.*;

public class Connect {
    private Connection conn= null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private ResultSetMetaData rsmd = null;

    private DefaultTableModel model;

    public Connect() throws SQLException{
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost:1433;DatabaseName=JavaCK;user=sa;password=Thanhphong12;encrypt=true;trustServerCertificate=true;";
            conn = DriverManager.getConnection(url);
            stmt = conn.createStatement();
            System.out.println("Connect to database successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public boolean checkLogin(String user,String pass) throws  SQLException{
        try{
            String sql = "select * from DANGNHAP where Username = '"+user+"' and Password = '"+pass+"'";
            rs = stmt.executeQuery(sql);
            if(rs.next()){
                return true;
            }
            else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void clearData(JTable table){
        model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        model.setColumnCount(0);
    }

    public void printData(JTable table, String sql) throws  SQLException{
        try{
            rs = stmt.executeQuery(sql);
            rsmd = rs.getMetaData();
            int c = rsmd.getColumnCount();
            model = (DefaultTableModel) table.getModel();
            for(int i=0;i<c;i++){
                model.addColumn(rsmd.getColumnName(i+1));
            }
            while(rs.next()){
                Object[] row = new Object[c];
                for(int i=0;i<c;i++){
                    row[i] = rs.getString(i+1);
                }
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void sortBook(JTable table,String sql) throws SQLException{
        sql += " order by name";
        printData(table,sql);
    }

    public int checkExist(String name,String author) throws SQLException{
        String sql = "select * from book where name = N'"+name+"' and author = N'"+author+"'";
        rs = stmt.executeQuery(sql);
        //if exist return that book id else return 0
        if(rs.next()){
            return rs.getInt(1);
        }
        else {
            return 0;
        }
    }
    public boolean checkID(int id,String table) throws SQLException{
        String sql = "select * from "+table+" where id = '"+id+"'";
        rs = stmt.executeQuery(sql);
        if(rs.next()){
            return true;
        }
        else {
            return false;
        }
    }
    public void addBook(String name,String author,String amount) throws SQLException{
        int id = 1;
        int amountInt = Integer.parseInt(amount);
        while(checkID(id,"Book")){
            id++;
        }
        String sql = "insert into book values('"+id+"',N'"+name+"',N'"+author+"','"+amountInt+"')";
        stmt.executeUpdate(sql);
    }
    public void editBook(String id,String name,String author,String amount) throws SQLException{
        int bid = Integer.parseInt(id);
        String sql = "update book set name = N'"+name+"',author = N'"+author+"',amount = amount + '"+amount+"' where id = '"+bid+"'";
        stmt.executeUpdate(sql);
    }
    public void updateAmount(int id,String amount) throws SQLException{
        String sql = "update book set amount = '"+amount+"' where id = '"+id+"'";
        stmt.executeUpdate(sql);
    }
    public void updateBook(String id,String name,String author,String amount) throws SQLException{
        int bid = Integer.parseInt(id);
        String sql = "update book set name = N'"+name+"',author = N'"+author+"',amount = '"+amount+"' where id = '"+bid+"'";
        stmt.executeUpdate(sql);
    }

    public boolean checkBookInUsed(String id) throws SQLException{
        int bid = Integer.parseInt(id);
        String sql = "select * from Invoice where book_id = '"+bid+"'";
        rs = stmt.executeQuery(sql);
        if(rs.next()){
            return true;
        }
        else {
            return false;
        }
    }
    public void deleteBook(String id) throws SQLException{
        int bid = Integer.parseInt(id);
        String sql = "delete from book where id = '"+bid+"'";
        stmt.executeUpdate(sql);
    }
    public boolean checkCallCard(String phone) throws SQLException{
        String sql = "select * from Card where phone = '"+phone+"'";
        rs = stmt.executeQuery(sql);
        if(rs.next()){
            return true;
        }
        else {
            return false;
        }
    }
    public void addCallCard(String name ,String phone,String address) throws SQLException{
        int id = 1;
        while(checkID(id,"Card")){
            id++;
        }
        String sql = "insert into Card values('"+id+"',N'"+name+"','"+phone+"',N'"+address+"')";
        stmt.executeUpdate(sql);
    }

    public void editCallCard(String id,String name,String phone,String address) throws SQLException{
        int cid = Integer.parseInt(id);
        String sql = "update Card set name = N'"+name+"',phone = '"+phone+"',address = N'"+address+"' where id = '"+cid+"'";
        stmt.executeUpdate(sql);
    }
    public void sortData(JTable table,String sql,String column) throws SQLException{
        sql += " order by "+column;
        printData(table,sql);
    }
    public void deleteCallCard(String id) throws SQLException{
        int cid = Integer.parseInt(id);
        String sql = "delete from Card where id = '"+cid+"'";
        stmt.executeUpdate(sql);
    }
    public boolean checkCard(String id) throws SQLException{
        int cid = Integer.parseInt(id);
        String sql = "select * from Invoice where card_id = '"+cid+"'";
        rs = stmt.executeQuery(sql);
        if(rs.next()){
            return false;
        }
        else {
            return true;
        }
    }
    public int getAmount(String id) throws SQLException{
        int bid = Integer.parseInt(id);
        String sql = "select amount from book where id = '"+bid+"'";
        rs = stmt.executeQuery(sql);
        if(rs.next()){
            return rs.getInt(1);
        }
        else {
            return 0;
        }
    }
    public void addInvoice(String book_id,String card_id,String amount) throws SQLException{
        int id = 1;
        while(checkID(id,"Invoice")){
            id++;
        }
        int bid = Integer.parseInt(book_id);
        int cid = Integer.parseInt(card_id);
        int amountInt = Integer.parseInt(amount);
        String sql = "update Book set amount = amount - '"+amountInt+"' where id = '"+bid+"'";
        stmt.executeUpdate(sql);
        sql = "insert into Invoice values('"+id+"','"+bid+"','"+cid+"','"+amountInt+"',getdate())";
        stmt.executeUpdate(sql);
        System.out.println(bid);
    }

    public void deleteInvoice(String id,String book_id,String amount) throws SQLException{
        int iid = Integer.parseInt(id);
        int bid = Integer.parseInt(book_id);
        int amountInt = Integer.parseInt(amount);
        String sql = "update book set amount = amount + '"+amountInt+"' where id = '"+bid+"'";
        stmt.executeUpdate(sql);
        sql = "delete from Invoice where id = '"+iid+"'";
        stmt.executeUpdate(sql);

    }
    public void addInvoice(String id,String book_id,String card_id,String amount) throws SQLException{
        int iid = Integer.parseInt(id);
        int bid = Integer.parseInt(book_id);
        int cid = Integer.parseInt(card_id);
        int amountInt = Integer.parseInt(amount);
        // iid,bid,cid,amountInt and getdate
        String sql = "insert into Invoice values('"+iid+"','"+bid+"','"+cid+"','"+amountInt+"',getdate())";
        stmt.executeUpdate(sql);
        sql = "update book set amount = amount - '"+amountInt+"' where id = '"+bid+"'";
        stmt.executeUpdate(sql);
    }

    public void updateInvoice(String id,String oamount,String quantity) throws SQLException {
        int iid = Integer.parseInt(id);
        int oamountInt = Integer.parseInt(oamount);
        int amountInt = Integer.parseInt(quantity);
        int amount = amountInt - oamountInt;
        String sql = "update book set amount = amount - '"+amount+"' where id = '"+iid+"'";
        stmt.executeUpdate(sql);
        sql = "update Invoice set amount = '"+amountInt+"' where id = '"+iid+"'";
        stmt.executeUpdate(sql);
    }

    public String getObid(String id) throws SQLException{
        int iid = Integer.parseInt(id);
        String sql = "select book_id from Invoice where id = '"+iid+"'";
        rs = stmt.executeQuery(sql);
        if(rs.next()){
            return rs.getString(1);
        }
        else {
            return null;
        }
    }
    public String getOpid(String id) throws SQLException{
        int iid = Integer.parseInt(id);
        String sql = "select card_id from Invoice where id = '"+iid+"'";
        rs = stmt.executeQuery(sql);
        if(rs.next()){
            return rs.getString(1);
        }
        else {
            return null;
        }
    }
    public String getOamount(String id) throws SQLException{
        int iid = Integer.parseInt(id);
        String sql = "select amount from Invoice where id = '"+iid+"'";
        rs = stmt.executeQuery(sql);
        if(rs.next()){
            return rs.getString(1);
        }
        else {
            return null;
        }
    }
    public String countBook() throws SQLException{
        String sql = "select sum(amount) from Book";
        rs = stmt.executeQuery(sql);
        if(rs.next()){
            return rs.getString(1);
        }
        else {
            return "0";
        }
    }
    public String countBookBorrowed() throws SQLException{
        String sql = "select sum(amount) from Invoice";
        rs = stmt.executeQuery(sql);
        if(rs.next()){
            return rs.getString(1);
        }
        else {
            return "0";
        }
    }
    public String countBookOverdue() throws SQLException{
        //datediff column bdate in Invoice
        String sql = "select sum(amount) from Invoice where datediff(day,bdate,getdate()) >= 3";
        rs = stmt.executeQuery(sql);
        if(rs.next()){
            if(rs.getString(1)!=null){
                return rs.getString(1);
            }
            else {
                return "0";
            }
        }
        else {
            return "0";
        }
    }
    public String countCard() throws SQLException{
        String sql = "select count(*) from Card";
        rs = stmt.executeQuery(sql);
        if(rs.next()){
            return rs.getString(1);
        }
        else {
            return "0";
        }
    }

    public String countCardBorrowed() throws SQLException{
        String sql = "select count(distinct card_id) from Invoice";
        rs = stmt.executeQuery(sql);
        if(rs.next()){
            return rs.getString(1);
        }
        else {
            return "0";
        }
    }
    public String countCardOverdue() throws SQLException{
        //datediff column bdate in Invoice
        String sql = "select count(distinct card_id) from Invoice where datediff(day,bdate,getdate()) >= 3";
        rs = stmt.executeQuery(sql);
        if(rs.next()){
            return rs.getString(1);
        }
        else {
            return "0";
        }
    }

    public void exportToFile(){

    }
}
