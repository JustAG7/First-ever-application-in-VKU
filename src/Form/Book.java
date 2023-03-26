package Form;

import BookLibrary.*;
import DBConnection.*;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class Book extends JPanel {
	private String[] columnNames = {"id", "name", "author", "amount"};
	private Connect con = new Connect();
	private book bk = new book();
	private String id = "";
	private String sql = "select id, name N'Tên sách', author N'Tác giả', amount N'Số lượng trong kho' \n" +
			"from Book";
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private JButton btnNewButton_3;
	private JSeparator separator_1;
	private JSeparator separator_2;
	private JSeparator separator_3;
	private JLabel lblTnSch;
	private JTextField textField_3;
	private JButton btnNewButton_4;
	private JTable table;
	private JScrollPane sPane;
	private JLabel lblNewLabel_3;
	/**
	 * Create the panel.
	 */
	public Book() throws SQLException {
		setLayout(null);
		setSize(1000,1000);
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 12, 933, 2);
		add(separator);
		
		JLabel lblNewLabel = new JLabel("Tên sách");
		lblNewLabel.setBounds(10, 26, 84, 15);
		add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(112, 26, 174, 19);
		add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Tác giả");
		lblNewLabel_1.setBounds(10, 65, 70, 15);
		add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(112, 65, 174, 19);
		add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Số lượng");
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
					String author = textField_1.getText();
					String amount = textField_2.getText();
					boolean check = bk.setName(author) && bk.setAmount(amount);
					if(check){
						int index = con.checkExist(name,author);
						if(index!=0) {
							//ask if user want to update amount of book
							int dialogButton = JOptionPane.YES_NO_OPTION;
							int dialogResult = JOptionPane.showConfirmDialog (null, "Sách đã tồn tại, bạn có muốn thêm số lượng không?","Warning",dialogButton);
							if(dialogResult == JOptionPane.YES_OPTION){
								//update amount of book
								JOptionPane.showMessageDialog(null, "Cập nhật thành công");
								con.updateAmount(index, amount);
								con.clearData(table);
								sql = "select id, name N'Tên sách', author N'Tác giả', amount N'Số lượng trong kho' \n" +
										"from Book";
								con.printData(table,sql);
							}
						}
						else{
							JOptionPane.showMessageDialog(null, "Thêm thành công");
							con.addBook(name, author, amount);
							con.clearData(table);
							sql = "select id, name N'Tên sách', author N'Tác giả', amount N'Số lượng trong kho' \n" +
									"from Book";
							con.printData(table,sql);
						}
					}
					else{
						String error = "";
						if(!bk.setName(author)){
							if(!bk.setAmount(amount)) error = "Tên tác giả và số lượng không hợp lệ";
							else error = "Tên tác giả không hợp lệ";
						}
						else error = "Số lượng không hợp lệ";
						JOptionPane.showMessageDialog(null, error);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					sql = "select id, name N'Tên sách', author N'Tác giả', amount N'Số lượng trong kho' \n"  +
							"from Book";
					con.clearData(table);
					try {
						con.printData(table,sql);
					} catch (SQLException ex) {
						throw new RuntimeException(ex);
					}
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
					String author = textField_1.getText();
					String amount = textField_2.getText();
					boolean check = bk.setName(author) && bk.setAmount(amount);
					int index = con.checkExist(name,author);
					if(check) {
						if(index==0){
							JOptionPane.showMessageDialog(null, "Cập nhật thành công");
							con.updateBook(id, name, author, amount);
							sql = "select id, name N'Tên sách', author N'Tác giả', amount N'Số lượng trong kho' \n" +
									"from Book";
							con.clearData(table);
							con.printData(table,sql);
						}
						else{
							if(index==Integer.parseInt(id)){
								int dialogButton = JOptionPane.YES_NO_OPTION;
								int dialogResult = JOptionPane.showConfirmDialog (null, "Sách đã tồn tại, bạn có muốn cập nhật số lượng không?","Warning",dialogButton);
								if(dialogResult == JOptionPane.YES_OPTION){
									//update amount of book
									con.updateAmount(index, amount);
									JOptionPane.showMessageDialog(null, "Cập nhật thành công");
									sql = "select id, name N'Tên sách', author N'Tác giả', amount N'Số lượng trong kho' \n" +
											"from Book";
									con.clearData(table);
									con.printData(table,sql);
								}
							}
							else{
								JOptionPane.showMessageDialog(null, "Sách đã tồn tại, vui lòng chọn sách dưới danh sách");
								sql = "select id, name N'Tên sách', author N'Tác giả', amount N'Số lượng trong kho' \n" +
										"from Book where id = "+index;
								con.clearData(table);
								con.printData(table,sql);
							}
						}
					}
					else JOptionPane.showMessageDialog(null, "Vui lòng nhập lại thông tin");

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					sql = "select id, name N'Tên sách', author N'Tác giả', amount N'Số lượng trong kho' \n"  +
							"from Book";
					con.clearData(table);
					try {
						con.printData(table,sql);
					} catch (SQLException ex) {
						throw new RuntimeException(ex);
					}
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
					if(con.checkBookInUsed(id)) JOptionPane.showMessageDialog(null, "Sách đã được mượn, không thể xóa");
					else{
						JOptionPane.showMessageDialog(null, "Xóa thành công");
						con.deleteBook(id);
						sql = "select id, name N'Tên sách', author N'Tác giả', amount N'Số lượng trong kho' \n" +
								"from Book";
						con.clearData(table);
						con.printData(table,sql);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					sql = "select id, name N'Tên sách', author N'Tác giả', amount N'Số lượng trong kho' \n"  +
							"from Book";
					con.clearData(table);
					try {
						con.printData(table,sql);
					} catch (SQLException ex) {
						throw new RuntimeException(ex);
					}
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_2.setBounds(804, 100, 117, 25);
		add(btnNewButton_2);
		
		btnNewButton_3 = new JButton("Sắp xếp");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(sql!="") {
					con.clearData(table);
					try {
						con.sortBook(table, sql);
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
		
		lblTnSch = new JLabel("Mã sách");
		lblTnSch.setBounds(10, 147, 105, 15);
		add(lblTnSch);
		
		textField_3 = new JTextField();
		textField_3.setBounds(112, 145, 174, 19);
		add(textField_3);
		textField_3.setColumns(10);
		
		btnNewButton_4 = new JButton("Tìm sách");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				id = textField_3.getText();
				sql = "select id, name N'Tên sách', author N'Tác giả', amount N'Số lượng trong kho' \n"+
						"from Book WHERE id = '"+id+"'";
				con.clearData(table);
				try {
					con.printData(table, sql);
				} catch (SQLException ex) {
					throw new RuntimeException(ex);
				}
			}
		});
		btnNewButton_4.setBounds(337, 139, 105, 25);
		add(btnNewButton_4);
		table=new JTable();
		sPane= new JScrollPane(table);
		sPane.setBounds(0, 182, 911, 522);
		add(sPane);
		
		JButton btnNewButton_5 = new JButton("Hiện sách");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					sql = "select id, name N'Tên sách', author N'Tác giả', amount N'Số lượng trong kho' \n" +
							" from Book";
					con.clearData(table);
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
				btnNewButton.setEnabled(false);
				btnNewButton_1.setEnabled(true);
				btnNewButton_2.setEnabled(true);
				int row = table.getSelectedRow();
				id = table.getModel().getValueAt(row, 0).toString();
				String name = table.getModel().getValueAt(row, 1).toString();
				String author = table.getModel().getValueAt(row, 2).toString();
				String quantity = table.getModel().getValueAt(row, 3).toString();
				textField.setText(name);
				textField_1.setText(author);
				textField_2.setText(quantity);
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
				String name = columnNames[col];
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
