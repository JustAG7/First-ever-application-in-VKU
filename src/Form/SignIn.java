package Form;

import DBConnection.*;

import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class SignIn extends JDialog {
	private JTextField textField;
	private JPasswordField pwdThot;

	private Connect con = new Connect();

	/**
	 * Create the dialog.
	 */
	public SignIn() throws SQLException{
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Đăng Nhập");
		lblNewLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(12, 12, 173, 183);
		getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBounds(189, 12, 249, 183);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Tài khoản");
		lblNewLabel_1.setBounds(12, 31, 70, 15);
		panel.add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setBounds(12, 71, 198, 19);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Mật khẩu");
		lblNewLabel_2.setBounds(12, 113, 132, 15);
		panel.add(lblNewLabel_2);
		
		pwdThot = new JPasswordField();
		pwdThot.setBounds(12, 152, 198, 19);
		panel.add(pwdThot);
		
		JButton btnNewButton = new JButton("Đăng nhập");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					String user = textField.getText();
					String pass = pwdThot.getText();
					String role = con.checkLogin(user,pass);
					if (role!="-1"){
						System.out.println(role);
                        int Role = Integer.parseInt(role);
						if(Role==0){
							Menu menu = new Menu();
							menu.setVisible(true);
							dispose();
						}
						else{
							userMenu menu = new userMenu(role);
							menu.setVisible(true);
							dispose();
						}
					} else {
						JOptionPane.showMessageDialog(null, "Đăng nhập thất bại");
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(189, 227, 117, 25);
		getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Thoát");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnNewButton_1.setBounds(318, 227, 117, 25);
		getContentPane().add(btnNewButton_1);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 213, 438, 2);
		getContentPane().add(separator);
	}
}
