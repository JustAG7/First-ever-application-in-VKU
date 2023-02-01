package Form;

import BookLibrary.*;
import DBConnection.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.*;

public class CallCard extends JPanel {
	private callCard card = new callCard();
	private String id = "";
	private String sql = "select id, name N'Tên khách', phone N'SĐT', address N'Địa chỉ' from Card";
	private Connect con = new Connect();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private JButton btnNewButton_3;
	private JSeparator separator_1;
	private JSeparator separator_2;
	private JLabel lblTnSch;
	private JTextField textField_3;
	private JButton btnNewButton_4;
	private JTable table;
	private JScrollPane sPane;
	private JLabel lblNewLabel_3;
	/**
	 * Create the panel.
	 */
	public CallCard() throws SQLException {
		setLayout(null);
		setSize(1000,1000);
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 12, 933, 2);
		add(separator);
		
		JLabel lblNewLabel = new JLabel("Tên khách");
		lblNewLabel.setBounds(10, 26, 84, 15);
		add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(112, 26, 174, 19);
		add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("SĐT");
		lblNewLabel_1.setBounds(10, 65, 70, 15);
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
		
		btnNewButton = new JButton("Thêm");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String name = textField.getText();
					String phone = textField_1.getText();
					String address = textField_2.getText();
					boolean check = card.setName(name) && card.setPhone(phone);
					if(check){
						if(con.checkCallCard(phone)){
							JOptionPane.showMessageDialog(null, " SDT đã tồn tại vui lòng chọn dưới bảng");
							sql = " select id, name N'Tên khách', phone N'SĐT', address N'Địa chỉ' from Card where phone = '"+phone+"'";
							con.clearData(table);
							con.printData(table,sql);
						}
						else{
							JOptionPane.showMessageDialog(null, "Thêm thành công");
							con.addCallCard(name, phone, address);
							con.clearData(table);
							sql = "select id, name N'Tên khách', phone N'SĐT', address N'Địa chỉ' from Card";
							con.printData(table,sql);
						}
					}
					else JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng định dạng");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(804, 24, 117, 25);
		add(btnNewButton);
		
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
							JOptionPane.showMessageDialog(null, " Khách đã tồn tại vui lòng chọn sđt khác dưới bảng");
							sql = " select id, name N'Tên khách', phone N'SĐT', address N'Địa chỉ' from Card where phone = '"+phone+"'";
							con.clearData(table);
							con.printData(table,sql);
						}
						else{
							JOptionPane.showMessageDialog(null, "Chỉnh sửa thành công");
							con.editCallCard(id,name, phone, address);
							sql = "select id, name N'Tên khách', phone N'SĐT', address N'Địa chỉ' from Card";
							con.clearData(table);
							con.printData(table,sql);
						}
					}
					else JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng định dạng");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_1.setBounds(804, 63, 117, 25);
		add(btnNewButton_1);
		
		btnNewButton_2 = new JButton("Xóa");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(con.checkCard(id)){
						con.deleteCallCard(id);
						JOptionPane.showMessageDialog(null, "Xóa thành công");
						con.clearData(table);
						sql = "select id, name N'Tên khách', phone N'SĐT', address N'Địa chỉ' from Card";
						con.printData(table,sql);
					}
					else JOptionPane.showMessageDialog(null, "Khách hàng này đang mượn sách vui lòng trả sách trước khi xóa");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_2.setBounds(804, 100, 117, 25);
		add(btnNewButton_2);
		
		btnNewButton_3 = new JButton("Sắp xếp"); //cho vui chứ cũng ko cần thiết vì có search theo cột rồi :v
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(sql!=""){
					con.clearData(table);
					try {
						con.sortBook(table,sql);
					} catch (SQLException ex) {
						throw new RuntimeException(ex);
					}
				}
			}
		});
		btnNewButton_3.setBounds(804, 137, 117, 25);
		add(btnNewButton_3);
		
		separator_1 = new JSeparator();
		separator_1.setBounds(4, 129, 454, 6);
		add(separator_1);
		
		separator_2 = new JSeparator();
		separator_2.setOrientation(SwingConstants.VERTICAL);
		separator_2.setBounds(454, 129, 34, 50);
		add(separator_2);
		
		lblTnSch = new JLabel("Tên khách");
		lblTnSch.setBounds(10, 147, 105, 15);
		add(lblTnSch);
		
		textField_3 = new JTextField();
		textField_3.setBounds(112, 145, 174, 19);
		add(textField_3);
		textField_3.setColumns(10);
		
		btnNewButton_4 = new JButton("Tìm khách");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = textField_3.getText();
				if(name!="" && card.setName(name)){
					sql = " select id, name N'Tên khách', phone N'SĐT', address N'Địa chỉ' from Card where lower(name) like N'%"+name+"%'";
					con.clearData(table);
					try {
						con.printData(table,sql);
					} catch (SQLException ex) {
						throw new RuntimeException(ex);
					}
				}
				else JOptionPane.showMessageDialog(null, "Vui lòng nhập lại tên khách");
			}
		});
		btnNewButton_4.setBounds(337, 139, 105, 25);
		add(btnNewButton_4);
		table=new JTable();
		sPane= new JScrollPane(table);
		sPane.setBounds(0, 182, 911, 522);
		add(sPane);
		
		JButton btnNewButton_5 = new JButton("Hiện khách");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sql = "select id, name N'Tên khách', phone N'SĐT', address N'Địa chỉ' from Card";
				con.clearData(table);
				try {
					con.printData(table,sql);
				} catch (SQLException ex) {
					throw new RuntimeException(ex);
				}
			}
		});
		btnNewButton_5.setBounds(549, 137, 174, 25);
		add(btnNewButton_5);

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//blur button them
				btnNewButton.setEnabled(false);
				btnNewButton_1.setEnabled(true);
				btnNewButton_2.setEnabled(true);
				int row = table.getSelectedRow();
				id = table.getModel().getValueAt(row, 0).toString();
				String name = table.getModel().getValueAt(row, 1).toString();
				String phone = table.getModel().getValueAt(row, 2).toString();
				String address = table.getModel().getValueAt(row, 3).toString();
				textField.setText(name);
				textField_1.setText(phone);
				textField_2.setText(address);
			}
		});

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textField.setText("");
				textField_1.setText("");
				textField_2.setText("");
				textField_3.setText("");
				btnNewButton.setEnabled(true);
				btnNewButton_1.setEnabled(false);
				btnNewButton_2.setEnabled(false);
			}
		});
		btnNewButton_1.setEnabled(false);
		btnNewButton_2.setEnabled(false);
		con.printData(table,sql);

		table.getTableHeader().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int col = table.columnAtPoint(e.getPoint());
				String name = table.getColumnName(col);
				if(name!=""){
					con.clearData(table);
					try {
						con.sortData(table,sql,name);
					} catch (SQLException ex) {
						throw new RuntimeException(ex);
					}
				}
			}
		});
	}
}
