package Form;
import DBConnection.*;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

public class userMenu extends JFrame {

	private Connect con = new Connect();

	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	private userBook book;
	private userInfo userinfo;

	private userCard callcard;

	private SignIn signin;


	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public userMenu(String id) throws SQLException {

	

		setVisible(true);
		setSize(new Dimension(1000, 1000));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000,1200);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("Hệ thống");
		menuBar.add(mnNewMenu);

		JMenuItem mntmNewMenuItem = new JMenuItem("Đăng xuất");

		mnNewMenu.add(mntmNewMenuItem);

		JSeparator separator = new JSeparator();
		mnNewMenu.add(separator);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Thoát");
		mnNewMenu.add(mntmNewMenuItem_1);

		JMenu mnNewMenu_1 = new JMenu("Quản lý");
		menuBar.add(mnNewMenu_1);

		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Mượn sách");
		mntmNewMenuItem_2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if(book==null){
					try {
						book = new userBook(id);
					} catch (SQLException e) {
						throw new RuntimeException(e);
					}
					tabbedPane.addTab("Mượn sách", book);
				}
				tabbedPane.setSelectedComponent(book);
			}
		});

		JMenuItem mntmNewMenuItem_5 = new JMenuItem("Quản lý thẻ mượn");
		mntmNewMenuItem_5.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if(callcard==null){
					try {
						callcard = new userCard(id);
					} catch (SQLException e) {
						throw new RuntimeException(e);
					}
					tabbedPane.addTab("Quản lý thẻ mượn", callcard);
				}
				tabbedPane.setSelectedComponent(callcard);
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_5);
		
		JMenuItem mntmThngTinC = new JMenuItem("Thông tin cá nhân");
		mntmThngTinC.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if(userinfo==null){
					try {
						userinfo = new userInfo(id);
					} catch (SQLException e) {
						throw new RuntimeException(e);
					}
					tabbedPane.addTab("Thông tin cá nhân", userinfo);
				}
				tabbedPane.setSelectedComponent(userinfo);
			}
		});
		mnNewMenu_1.add(mntmThngTinC);
		mnNewMenu_1.add(mntmNewMenuItem_2);

		

		JMenu mnNewMenu_2 = new JMenu("Trợ giúp");
		menuBar.add(mnNewMenu_2);

		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Thông tin");
		mntmNewMenuItem_4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JOptionPane.showMessageDialog(null, "Bài tập cuối kì: Lập trình hướng đối tượng\nSinh viên: Võ Thanh Phong\nMã sinh viên: 22IT222\nLớp: 22GIT1 \nChủ đề: Quản lý thư viện");
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem_4);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JToolBar toolBar = new JToolBar();
		toolBar.setBounds(0, 0, 606, 38);
		contentPane.add(toolBar);

		JButton btnNewButton = new JButton("Đăng xuất");
		btnNewButton.addActionListener(e -> {
			try {
				signin = new SignIn();
			} catch (SQLException ex) {
				throw new RuntimeException(ex);
			}
			signin.setVisible(true);
			dispose();
		});
		toolBar.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Mượn sách");
		btnNewButton_1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if(book==null) {
					try {
						book = new userBook(id);
					} catch (SQLException e) {
						throw new RuntimeException(e);
					}
					tabbedPane.addTab("Mượn sách", book);
				}
				tabbedPane.setSelectedComponent(book);
			}
		});

		JButton btnQu = new JButton("Quản lý thẻ mượn");
		btnQu.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if(callcard==null) {
					try {
						callcard = new userCard(id);
					} catch (SQLException e) {
						throw new RuntimeException(e);
					}
					tabbedPane.addTab("Quản lý thẻ mượn", callcard);
				}
				tabbedPane.setSelectedComponent(callcard);
			}
		});
		toolBar.add(btnQu);
		toolBar.add(btnNewButton_1);

		

		JButton btnNewButton_3 = new JButton("Trợ giúp");
		btnNewButton_3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JOptionPane.showMessageDialog(null, "Bài tập cuối kì: Lập trình hướng đối tượng\nSinh viên: Võ Thanh Phong\nMã sinh viên: 22IT222\nLớp: 22GIT1 \nChủ đề: Quản lý thư viện");
			}
		});
		
		JButton btnThngTinC = new JButton("Thông tin cá nhân");
		btnThngTinC.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if(userinfo==null){
					try {
						userinfo = new userInfo(id);
					} catch (SQLException e) {
						throw new RuntimeException(e);
					}
					tabbedPane.addTab("Thông tin cá nhân", userinfo);
				}
				tabbedPane.setSelectedComponent(userinfo);
			}
		});
		toolBar.add(btnThngTinC);
		toolBar.add(btnNewButton_3);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 50, 950, 621);
		contentPane.add(tabbedPane);
	}
}
