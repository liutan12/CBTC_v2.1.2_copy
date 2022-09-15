//package main;
//
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.net.InetAddress;
//import java.net.UnknownHostException;
//import java.util.ArrayList;
//import org.eclipse.swt.SWT;
//import org.eclipse.swt.events.FocusEvent;
//import org.eclipse.swt.events.FocusListener;
//import org.eclipse.swt.events.MouseEvent;
//import org.eclipse.swt.events.MouseListener;
//import org.eclipse.swt.events.PaintEvent;
//import org.eclipse.swt.events.PaintListener;
//import org.eclipse.swt.events.SelectionAdapter;
//import org.eclipse.swt.events.SelectionEvent;
//import org.eclipse.swt.events.ShellAdapter;
//import org.eclipse.swt.events.ShellEvent;
//import org.eclipse.swt.graphics.Image;
//import org.eclipse.swt.widgets.Button;
//import org.eclipse.swt.widgets.Composite;
//import org.eclipse.swt.widgets.Display;
//import org.eclipse.swt.widgets.Label;
//import org.eclipse.swt.widgets.MessageBox;
//import org.eclipse.swt.widgets.Shell;
//import org.eclipse.swt.widgets.Text;
//import org.eclipse.wb.swt.SWTResourceManager;
//import org.eclipse.swt.events.PaintListener;
//import javax.swing.ImageIcon;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//
//import POJO.RecvInfo;
//import POJO.SendInfo;
//
//public class Login {
//
//	static Display display = Display.getDefault();
//	/**
//	 * @wbp.parser.entryPoint
//	 */
//
//public void getUI() throws UnknownHostException {
//		
//		final Shell mainShell = new Shell();
//		mainShell.setImage(SWTResourceManager.getImage("C:\\Users\\wirel\\Desktop\\CBTC_v2.1.1\\CBTC_v2.1.1\\image\\Back.jpg"));
//		mainShell.setSize(1025, 816);
//		mainShell.setText("网络测试");
//		mainShell.setLayout(null);
//		Composite composite = new Composite(mainShell,SWT.NONE);
//						//composite.setBounds(0,0,1046,686);
//						
//						Label label = new Label(mainShell,SWT.NONE);
//						label.setLocation(21, 31);
//						label.setSize(818, 395);
//						label.setImage(new Image(Display.getDefault(),"image/background.jpg"));
//					
//						
//						Label usernameLabel = new Label(mainShell, SWT.NONE);
//						usernameLabel.setLocation(281, 455);
//						usernameLabel.setSize(150, 60);
//						usernameLabel.setText("用户名");
//						usernameLabel.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 19, SWT.BOLD));
//						
//								Text username = new Text(mainShell, SWT.NONE);
//								username.setLocation(539, 455);
//								username.setSize(262, 60);
//								//username.setText("Username");
//								username.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 15, SWT.NORMAL));
//								
//								Label passwordLabel = new Label(mainShell, SWT.NONE);
//								passwordLabel.setLocation(281, 534);
//								passwordLabel.setSize(122, 60);
//								passwordLabel.setText("密码");
//								passwordLabel.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 19, SWT.BOLD));
//								
//								Text password = new Text(mainShell, SWT.PASSWORD);
//								password.setLocation(539, 534);
//								password.setSize(262, 60);
//								//password.setText("password");
//								password.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 15, SWT.NORMAL));
//								
//								
//								Button btnStart = new Button(mainShell, SWT.NONE);
//								btnStart.setLocation(281, 612);
//								btnStart.setSize(134, 60);
//								btnStart.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 15, SWT.BOLD));
//								
//										btnStart.addSelectionListener(new SelectionAdapter() {
//											@Override
//											public void widgetSelected(SelectionEvent e) {
//										MessageBox messageBox = null;
//										if(username.getText().equals("admin")&&password.getText().equals("123456")) {
//											UI ui=new UI();
//											try {
//												mainShell.dispose();
//												ui.getUI();
//											} catch (UnknownHostException e1) {
//												e1.printStackTrace();
//											}
//										} else {
//											messageBox = new MessageBox(mainShell, SWT.APPLICATION_MODAL);
//											messageBox.setText("登陆失败");
//											messageBox.setMessage("用户名或者密码输入错误！");
//											messageBox.open();
//										}
//											}
//										});
//										btnStart.setText("登录");
//										
//										final Button btnEnd = new Button(mainShell, SWT.NONE);
//										btnEnd.setLocation(539, 612);
//										btnEnd.setSize(144, 60);
//										btnEnd.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 15, SWT.BOLD));
//										btnEnd.addSelectionListener(new SelectionAdapter() {
//											@Override
//											public void widgetSelected(SelectionEvent e) {
//												username.setText("");
//												password.setText("");
//											}
//										});
//										btnEnd.setText("重置");
//								password.addFocusListener(new FocusListener() {
//									
//									@Override
//									public void focusLost(FocusEvent arg0) {
//										// TODO Auto-generated method stub
//										
//									}
//									
//									@Override
//									public void focusGained(FocusEvent arg0) {
//										// TODO Auto-generated method stub
//										password.setText("");
//									}
//								});
//								username.addFocusListener(new FocusListener() {
//									
//									@Override
//									public void focusLost(FocusEvent arg0) {
//										// TODO Auto-generated method stub
//										
//									}
//									
//									@Override
//									public void focusGained(FocusEvent arg0) {
//										// TODO Auto-generated method stub
//										username.setText("");
//									}
//								});
////		composite.addPaintListener(new PaintListener() {
////			public void paintControl(PaintEvent e) {
////				org.eclipse.swt.graphics.Image im = SWTResourceManager.getImage(getClass(),"/tupian/ground.jpg");
////				e.gc.drawImage(im, 0, 0,im.getBounds().width,im.getBounds().height,0,0,composite.getBounds().width,composite.getBounds().height);
////			}
////		});
////		
//		/*Label lblNewLabel = new Label(mainShell, SWT.NONE);
//		lblNewLabel.setImage(SWTResourceManager.getImage("C:\\Users\\wirel\\Desktop\\CBTC_v2.1.1\\CBTC_v2.1.1\\image\\ground.jpg"));
//		lblNewLabel.setBounds(0, 11, 1003, 749);*/
//		
//
//		
//		mainShell.open();
//		mainShell.layout();
//		mainShell.addShellListener(new ShellAdapter(){
//			 public void shellClosed(ShellEvent arg0){
//				 System.exit(0);
//			 }
//		});
//		while (!mainShell.isDisposed()) {
//			if (!display.readAndDispatch()) {
//				display.sleep();
//			}
//	}
//		
//	}
//	
//
//
//	private void init(){
//		
//    }
//}




//新版本
package main;

import java.net.UnknownHostException;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.widgets.ProgressBar;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.PaintListener;

//
public class Login{
	public static Text textPassword;
	//public static boolean flag=false;
	public static Text textUser;
	public static Text textPort;
	private Text txtUsername;
	private Text txtPassword;
	/**
	 * Launch the application.
	 * @param args
	 * @wbp.parser.entryPoint
	 */
	public void getUI() throws UnknownHostException {
			
			Display display = Display.getDefault();
			Shell shell = new Shell();
			shell.setImage(SWTResourceManager.getImage("C:\\Users\\wirel\\Desktop\\CBTC_v2.1.1\\CBTC_v2.1.1\\image\\1.jpg"));
			
			shell.setSize(1062, 725);
			shell.setText("网络测试");
			
			Composite composite = new Composite(shell, SWT.NONE);
			composite.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 19, SWT.BOLD));
			composite.addPaintListener(new PaintListener() {
				public void paintControl(PaintEvent e) {
					org.eclipse.swt.graphics.Image im=SWTResourceManager.getImage(getClass(),"/tupian/ground.jpg");
					e.gc.drawImage(im, 0, 0, im.getBounds().width, im.getBounds().height, 0, 0, composite.getBounds().width, composite.getBounds().height);
				}
			});
			composite.setBounds(10, 10, 1046, 669);
			
		    Label usernameLabel = new Label(composite, SWT.NONE);
		    usernameLabel.setLocation(324, 253);
			usernameLabel.setSize(150, 45);
			usernameLabel.setText("用户名:");
			usernameLabel.setFont(SWTResourceManager.getFont("宋体", 20, SWT.BOLD));
		
			Text username = new Text(composite, SWT.NONE);
			username.setLocation(562, 253);
			username.setSize(134, 48);
			username.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 15, SWT.NORMAL));
					
			Label passwordLabel = new Label(composite, SWT.NONE);
			passwordLabel.setLocation(324, 379);
			passwordLabel.setSize(150, 45);
			passwordLabel.setText("密码:");
			passwordLabel.setFont(SWTResourceManager.getFont("宋体", 20, SWT.BOLD));
				
			Text password = new Text(composite, SWT.PASSWORD);
			password.setLocation(562, 379);
			password.setSize(134, 45);
			password.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 15, SWT.NORMAL));
			
			
			Button btnStart = new Button(composite, SWT.NONE);
			btnStart.setLocation(324, 492);
			btnStart.setSize(134, 60);
			btnStart.setFont(SWTResourceManager.getFont("宋体", 15, SWT.BOLD));
			
					btnStart.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
					MessageBox messageBox = null;
//					if(username.getText().equals("admin")&&password.getText().equals("123456")) {
						UI ui=new UI();
						try {
							shell.dispose();
							ui.getUI();
						} catch (UnknownHostException e1) {
							e1.printStackTrace();
						}
							/*
							 * } else { messageBox = new MessageBox(shell, SWT.APPLICATION_MODAL);
							 * messageBox.setText("登陆失败"); messageBox.setMessage("用户名或者密码输入错误！");
							 * messageBox.open(); }
							 */
						}
					});
					
					btnStart.setText("登录");
					
					final Button btnEnd = new Button(composite, SWT.NONE);
					btnEnd.setLocation(562, 492);
					btnEnd.setSize(134, 60);
					btnEnd.setFont(SWTResourceManager.getFont("宋体", 15, SWT.BOLD));
					btnEnd.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							username.setText("");
							password.setText("");
						}
					});
					btnEnd.setText("重置");
			password.addFocusListener(new FocusListener() {
				
				@Override
				public void focusLost(FocusEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void focusGained(FocusEvent arg0) {
					// TODO Auto-generated method stub
					password.setText("");
				}
			});
			username.addFocusListener(new FocusListener() {
				
				@Override
				public void focusLost(FocusEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void focusGained(FocusEvent arg0) {
					// TODO Auto-generated method stub
					username.setText("");
				}
			});
			
			/*Label lblNewLabel = new Label(composite, SWT.NONE);
			lblNewLabel.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 19, SWT.BOLD));
			lblNewLabel.setToolTipText("");
			lblNewLabel.setBounds(192, 336, 142, 52);
			lblNewLabel.setText("用户名：");
			
				txtUsername = new Text(composite, SWT.BORDER);
				txtUsername.setBounds(381, 336, 264, 52);
			
			Label lblPassword = new Label(composite, SWT.NONE);
			lblPassword.setBounds(192, 420, 142, 52);
			lblPassword.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 19, SWT.BOLD));
			lblPassword.setText("密码：");
			
			txtPassword = new Text(composite, SWT.BORDER);
			txtPassword.setBounds(381, 420, 264, 52);*/
			
			shell.open();
			shell.layout();
			shell.addShellListener(new ShellAdapter(){
				 public void shellClosed(ShellEvent arg0){
					 System.exit(0);
				 }
			});
			
//			shell.open();
//			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
	}

private void init(){

}
}