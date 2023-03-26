package Form;

import DBConnection.*;
import BookLibrary.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.*;

public class userCard extends JPanel {
	private Connect con = new Connect();
	private String[] columnNames = {"I.id","C.name","C.phone","C.address","B.name","I.amount","I.bdate"};
	private String sql = "select I.id, B.name N'Tên sách', I.amount N'Số lượng', I.bdate N'Ngày mượn'\n" +
			"from Book B inner join Invoice I on B.id=I.book_id \n" +
			"        inner join Card C on I.card_id=C.id\n";
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
	public userCard(String cid) throws SQLException {
		System.out.println(cid);
		setLayout(null);
		setSize(1000,1000);
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 12, 933, 2);
		add(separator);
		
		JLabel lblNewLabel = new JLabel("Mã sách");
		lblNewLabel.setBounds(10, 26, 70, 15);
		add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(112, 26, 174, 19);
		add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Ngày mượn");
		lblNewLabel_1.setBounds(10, 65, 110, 15);
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
		
		
		btnNewButton_3 = new JButton("Sắp xếp");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					try{
						con.clearData(table);
						con.sortData(table, sql,"I.bdate");
					} catch (SQLException e1) {
						e1.printStackTrace();
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
		
		lblTnSch = new JLabel("Mã giao dịch");
		lblTnSch.setBounds(10, 147, 94, 15);
		add(lblTnSch);
		
		textField_3 = new JTextField();
		textField_3.setBounds(112, 145, 174, 19);
		add(textField_3);
		textField_3.setColumns(10);
		
		btnNewButton_4 = new JButton("Tìm mã");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = textField_3.getText();
				if(id!=""){
					try {
						sql = "select I.id, B.name N'Tên sách',  I.amount N'Số lượng', I.bdate N'Ngày mượn' \n" +
								"from Book B inner join Invoice I on B.id=I.book_id \n" +
								"        inner join Card C on I.card_id=C.id " +
								"where I.id = '"+id+"' and C.id = '"+cid+"'";
						con.clearData(table);
						con.printData(table, sql);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				else JOptionPane.showMessageDialog(null, "Vui lòng nhập mã thẻ");
			}
		});
		btnNewButton_4.setBounds(327, 139, 115, 25);
		add(btnNewButton_4);
		table=new JTable();
		sPane= new JScrollPane(table);
		sPane.setBounds(10, 178, 911, 514);
		add(sPane);
		
		JButton btnNewButton_5 = new JButton("Hiện lịch sử mượn");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sql = "select I.id, B.name N'Tên sách',  I.amount N'Số lượng', I.bdate N'Ngày mượn' \n" +
						"from Book B inner join Invoice I on B.id=I.book_id \n" +
						"        inner join Card C on I.card_id=C.id " +" where C.id = '"+cid+"'";
				try {
					con.clearData(table);
					con.printData(table,sql);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_5.setBounds(522, 137, 210, 25);
		add(btnNewButton_5);

		table.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int row = table.rowAtPoint(evt.getPoint());
				int col = table.columnAtPoint(evt.getPoint());
				if (row >= 0 && col >= 0) {
					textField.setText(table.getValueAt(row, 1).toString());
					textField_1.setText(table.getValueAt(row, 3).toString());
					textField_2.setText(table.getValueAt(row, 2).toString());
				}
			}
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textField.setText("");
				textField_1.setText("");
				textField_2.setText("");
			}
		});

		sql+=" where C.id = '"+cid+"'";
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
