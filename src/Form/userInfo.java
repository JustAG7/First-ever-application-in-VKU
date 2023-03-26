package Form;

import DBConnection.*;
import BookLibrary.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

public class userInfo extends JPanel {
	private Connect con = new Connect();
	private invoice inv = new invoice();
	private String obid = "";
	private String opid = "";
	private String oamount = "";

	private String id = "";
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_5;
	private JLabel lblNewLabel_6;
	private JLabel lblNewLabel_7;
	private JLabel lblNewLabel_7_1;
	private JLabel lblNewLabel_7_2;
	private JLabel lblNewLabel_7_1_1;
	private JLabel lblSLnMn;
	private callCard card = new callCard();

	/**
	 * Create the panel.
	 */
	public userInfo(String id) throws SQLException {
		setLayout(null);
		setSize(1000,1000);
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 12, 933, 2);
		add(separator);
		
		JLabel lblNewLabel = new JLabel("Họ và Tên");
		lblNewLabel.setBounds(10, 26, 70, 15);
		add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(112, 26, 174, 19);
		add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("SĐT");
		lblNewLabel_1.setBounds(10, 65, 110, 15);
		add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(112, 65, 174, 19);
		add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Địa chỉ");
		lblNewLabel_2.setBounds(10, 102, 94, 15);
		add(lblNewLabel_2);
		
		textField_2 = new JTextField();
		textField_2.setBounds(112, 102, 174, 19);
		add(textField_2);
		textField_2.setColumns(10);
		
		btnNewButton_1 = new JButton("Chỉnh sửa");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String name = textField.getText();
					String phone = textField_1.getText();
					String address = textField_2.getText();
					boolean check = card.setName(name) && card.setPhone(phone);
					if(check){
						if(con.checkCallCard(phone)){ //thật ra mỗi người có nhiều sđt nhưng mà sđt luôn là riêng biệt nên lấy cái này làm chính
							JOptionPane.showMessageDialog(null, " Khách đã tồn tại");
						}
						else{
							JOptionPane.showMessageDialog(null, "Chỉnh sửa thành công");
							con.editCallCard(id,name, phone, address);
						}
					}
					else JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng định dạng");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_1.setBounds(616, 97, 117, 25);
		add(btnNewButton_1);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 140, 905, 2);
		add(separator_1);
		
		JButton btnNewButton_3 = new JButton("Hiện thống kê");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblNewLabel_4.setVisible(true);
				lblNewLabel_5.setVisible(true);
				lblNewLabel_6.setVisible(true);
				lblNewLabel_7.setVisible(true);
				lblNewLabel_7_1.setVisible(true);
				lblNewLabel_7_1_1.setVisible(true);
				lblNewLabel_7_2.setVisible(true);
				lblSLnMn.setVisible(true);
				try {
					//so lan muon, so sach da muon,  so giao dich bi qua han, so luong sach da muon
					lblNewLabel_7.setText(con.getNumberOfTransactions(id)+"");
					lblNewLabel_7_1.setText(con.getNumberOfBorrowedBooks(id)+"");
					lblNewLabel_7_1_1.setText(con.getNumberOfOverdueTransactions(id)+"");
					lblNewLabel_7_2.setText(con.getNumberOfBorrowedTransactions(id)+"");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_3.setBounds(616, 154, 133, 25);
		add(btnNewButton_3);
		
		lblSLnMn = new JLabel("Số lần mượn");
		lblSLnMn.setBounds(10, 154, 94, 15);
		add(lblSLnMn);
		
		lblNewLabel_4 = new JLabel("Số sách đã mượn");
		lblNewLabel_4.setBounds(10, 193, 154, 15);
		add(lblNewLabel_4);
		
		lblNewLabel_5 = new JLabel("Số lượng sách đã mượn");
		lblNewLabel_5.setBounds(12, 233, 174, 15);
		add(lblNewLabel_5);
		
		lblNewLabel_6 = new JLabel("Số giao dịch bị quá hạn");
		lblNewLabel_6.setBounds(10, 276, 174, 15);
		add(lblNewLabel_6);
		
		lblNewLabel_7 = new JLabel("");
		lblNewLabel_7.setBounds(278, 154, 194, 15);
		add(lblNewLabel_7);
		
		lblNewLabel_7_1 = new JLabel("");
		lblNewLabel_7_1.setBounds(278, 193, 194, 15);
		add(lblNewLabel_7_1);
		
		lblNewLabel_7_1_1 = new JLabel("");
		lblNewLabel_7_1_1.setBounds(278, 272, 194, 15);
		add(lblNewLabel_7_1_1);
		
		lblNewLabel_7_2 = new JLabel("");
		lblNewLabel_7_2.setBounds(278, 233, 194, 15);
		add(lblNewLabel_7_2);

		ResultSet rs = con.getCard(id);
		//get the name, phone and address
		while(rs.next()) {
			textField.setText(rs.getString("name"));
			textField_1.setText(rs.getString("phone"));
			textField_2.setText(rs.getString("address"));
		}
		//set visible to all label below the separator false

		lblNewLabel_4.setVisible(false);
		lblNewLabel_5.setVisible(false);
		lblNewLabel_6.setVisible(false);
		lblNewLabel_7.setVisible(false);
		lblNewLabel_7_1.setVisible(false);
		lblNewLabel_7_1_1.setVisible(false);
		lblNewLabel_7_2.setVisible(false);
		lblSLnMn.setVisible(false);
	}
}
