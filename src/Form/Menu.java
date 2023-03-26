package Form;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

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

public class Menu extends JFrame {

	private Connect con = new Connect();

	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	private Book book;
	private CallCard callcard;

	private Invoice invoice;

	private SignIn signin;


	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public Menu() throws SQLException {

		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Thống kê");

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

		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Quản lý sách");
		mntmNewMenuItem_2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if(book==null){
					try {
						book = new Book();
					} catch (SQLException e) {
						throw new RuntimeException(e);
					}
					tabbedPane.addTab("Quản lý sách", book);
				}
				tabbedPane.setSelectedComponent(book);
			}
		});

		JMenuItem mntmNewMenuItem_5 = new JMenuItem("Quản lý thẻ mượn");
		mntmNewMenuItem_5.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if(invoice==null){
					try {
						invoice = new Invoice();
					} catch (SQLException e) {
						throw new RuntimeException(e);
					}
					tabbedPane.addTab("Quản lý thẻ mượn", invoice);
				}
				tabbedPane.setSelectedComponent(invoice);
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_5);
		mnNewMenu_1.add(mntmNewMenuItem_2);

		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Quản lý khách");
		mntmNewMenuItem_3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if(callcard==null){
					try {
						callcard = new CallCard();
					} catch (SQLException e) {
						throw new RuntimeException(e);
					}
					tabbedPane.addTab("Quản lý khách", callcard);
				}
				tabbedPane.setSelectedComponent(callcard);
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_3);

		JMenu mnNewMenu_2 = new JMenu("Trợ giúp");
		menuBar.add(mnNewMenu_2);

		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Thông tin");
		mntmNewMenuItem_4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JOptionPane.showMessageDialog(null, "Bài tập cuối kì: Lập trình hướng đối tượng\nSinh viên: Võ Thanh Phong\nMã sinh viên: 22IT222\nLớp: 22GIT1 \nChủ đề: Quản lý thư viện");
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem_4);

		JMenuItem mntmNewMenuItem_6 = new JMenuItem("Thống kê");
		mntmNewMenuItem_6.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				String st= "";
				try {
					String[] data = {"Số lượng sách trong thư viện: "+con.countBook()+"\n","Số lượng sách đã mượn: "+con.countBookBorrowed()+"\n","Số lượng sách chưa mượn: "+(Integer.parseInt(con.countBook())-Integer.parseInt(con.countBookBorrowed()))+"\n","Số lượng sách quá 3 ngày chưa trả: "+con.countBookOverdue()+"\n","Số lượng khách trong thư viện: "+con.countCard()+"\n","Số lượng khách đang mượn: "+con.countCardBorrowed()+"\n","Số lượng khách chưa trả sách quá 3 ngày: "+con.countCardOverdue()+"\n"};
					for(int i=0;i<data.length;i++){
						st+=data[i];
					}
					st+= "\nBạn có muốn xuất file thống kê không?";
					//ask if user want to export file
					int result = JOptionPane.showConfirmDialog(null, st, "Thống kê", JOptionPane.YES_NO_OPTION);
					if(result == JOptionPane.YES_OPTION){
						Row titleRow = sheet.createRow(0);
						Cell titleCell = titleRow.createCell(0);
						titleCell.setCellValue("THỐNG KÊ THƯ VIỆN SÁCH");
						CellStyle style = workbook.createCellStyle();
						XSSFRichTextString richText = new XSSFRichTextString("THỐNG KÊ THƯ VIỆN SÁCH");
						XSSFFont font = (XSSFFont)workbook.createFont();
						font.setBold(true);
						font.setFontHeight(16);
						richText.applyFont(font);
						style.setAlignment(HorizontalAlignment.CENTER);
						style.setVerticalAlignment(VerticalAlignment.CENTER);
						titleRow.setHeight((short) ((short) 30*30));
						titleCell.setCellValue(richText);
						titleCell.setCellStyle(style);
						for (int i = 0; i < data.length; i++) {
							String[] keyValue = data[i].split(":");
							XSSFRow row = (XSSFRow) sheet.createRow(i+1);
							XSSFCell keyCell = row.createCell(0);
							keyCell.setCellValue(keyValue[0].trim());
							XSSFCell valueCell = row.createCell(1);
							valueCell.setCellValue(keyValue[1].trim());
						}
						sheet.autoSizeColumn(0);
						sheet.autoSizeColumn(1);
						FileOutputStream fileOut = new FileOutputStream("Thống kê.xlsx");
						workbook.write(fileOut);
						fileOut.close();
						workbook.close();
						JOptionPane.showMessageDialog(null, "Xuất file thành công!");
					}
				} catch (SQLException | FileNotFoundException e) {
					throw new RuntimeException(e);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}


			}
		});
		mnNewMenu_2.add(mntmNewMenuItem_6);
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

		JButton btnNewButton_1 = new JButton("Quản lý sách");
		btnNewButton_1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if(book==null) {
					try {
						book = new Book();
					} catch (SQLException e) {
						throw new RuntimeException(e);
					}
					tabbedPane.addTab("Quản lý sách", book);
				}
				tabbedPane.setSelectedComponent(book);
			}
		});

		JButton btnQu = new JButton("Quản lý thẻ mượn");
		btnQu.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if(invoice==null) {
					try {
						invoice = new Invoice();
					} catch (SQLException e) {
						throw new RuntimeException(e);
					}
					tabbedPane.addTab("Quản lý thẻ mượn", invoice);
				}
				tabbedPane.setSelectedComponent(invoice);
			}
		});
		toolBar.add(btnQu);
		toolBar.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("Quản lý khách");
		btnNewButton_2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if(callcard==null) {
					try {
						callcard = new CallCard();
					} catch (SQLException e) {
						throw new RuntimeException(e);
					}
					tabbedPane.addTab("Quản lý khách", callcard);
				}
				tabbedPane.setSelectedComponent(callcard);
			}
		});
		toolBar.add(btnNewButton_2);

		JButton btnNewButton_3 = new JButton("Trợ giúp");
		btnNewButton_3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JOptionPane.showMessageDialog(null, "Bài tập cuối kì: Lập trình hướng đối tượng\nSinh viên: Võ Thanh Phong\nMã sinh viên: 22IT222\nLớp: 22GIT1 \nChủ đề: Quản lý thư viện");
			}
		});
		toolBar.add(btnNewButton_3);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 50, 950, 621);
		contentPane.add(tabbedPane);
	}
}
