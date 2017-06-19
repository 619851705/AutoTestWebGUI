package com.dcits.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import com.dcits.bean.User;
import com.dcits.main.TestController;

public class LoginWindow extends Thread{
	TestController t = null;
	User user = null;
	//static String Loginuser = null;
	static final int WIDTH = 400;
	static final int HEIGHT = 300;
	JTextField nameField =null;
	JPasswordField passField = null;
	JFrame jf = null;
	public LoginWindow(final TestController t){
		this.t = t;
		//初始化窗口设置
		jf = new JFrame("web自动化测试");
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screensize = kit.getScreenSize();
		
		int width = screensize.width;
		int height = screensize.height;
		
		int x = (width - WIDTH)/2;
		int y = (height - HEIGHT)/2;
		
		jf.setLocation(x, y);
		jf.setSize(WIDTH, HEIGHT);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setResizable(false);
		
		//初始化容器
		JPanel jp = new JPanel();
		jp.setBorder(BorderFactory.createTitledBorder(""));//设置边框
		jf.setContentPane(jp);
		//创建一个BorderLayout
		BorderLayout borlayout = new BorderLayout();
		jf.setLayout(borlayout);
		
		JLabel username=new JLabel("用  户:");//创建标签组件、文本框组件
		username.setFont(new Font("微软雅黑", Font.PLAIN, 15));
	    nameField=new JTextField(15);
	    
	    nameField.setMaximumSize(nameField.getPreferredSize());
	    
	    Box hbox1=Box.createHorizontalBox();//创建一个水平箱子
	    hbox1.add(username); //在水平箱子上添加一个标签组件，并且创建一个不可见的、20个单位的组件。在这之后再添加一个文本框组件
	    hbox1.add(Box.createHorizontalStrut(20));
	    hbox1.add(nameField);  
	    
	    JLabel passwd=new JLabel("密  码:");//创建标签组件、文本框组件
	    passwd.setFont(new Font("微软雅黑", Font.PLAIN, 15));
	    //JTextField passField=new JTextField(15);
	    passField = new JPasswordField(15);
	    passField.setMaximumSize(passField.getPreferredSize());
	    Box hbox2=Box.createHorizontalBox();//创建一个水平箱子
	    hbox2.add(passwd); //在水平箱子上添加一个标签组件，并且创建一个不可见的、20个单位的组件。在这之后再添加一个文本框组件
	    hbox2.add(Box.createHorizontalStrut(20));
	    hbox2.add(passField);  
	    
	    //创建登录窗口的确定按钮和取消按钮
	    JButton submit=new JButton("确定");//创建两个普通按钮组件，并且创建一个水平箱子，将两个按钮添加到箱子中
	    JButton cancel=new JButton("取消");
	    submit.setFont(new Font("微软雅黑", Font.PLAIN, 15));
	    cancel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
	    Box hbox3=Box.createHorizontalBox();
	    hbox3.add(submit);
	    hbox3.add(Box.createHorizontalStrut(120));//设置两个按钮之间的水平距离
	    hbox3.add(cancel);
	    
	    Box vbox=Box.createVerticalBox();//创建一个垂直箱子，这个箱子将两个水平箱子添加到其中，创建一个横向 glue 组件。
	    vbox.add(Box.createVerticalStrut(30));
	    vbox.add(hbox1);
	    vbox.add(hbox2);
	    vbox.add(Box.createVerticalGlue());
	    vbox.add(hbox3);
	    jp.add(vbox,"Center"); // 将垂直箱子添加到BorderLayout布局管理器中的中间位置  
	    
	    //创建按钮的相关事件  
	    //通过回车触发按钮操作
	    submit.registerKeyboardAction(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//new MainForm();
				buttondeal(nameField,passField);
//		
			}
		}, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
	    
	    //通过点击按钮登录
	    submit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {				
				buttondeal(nameField,passField);
			}
		});
	     
	    cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				
			}
		});
	    	    
	}
	public void  buttondeal(JTextField nameField,JPasswordField passField){
		String passwd = String.valueOf(passField.getPassword());
		String username = nameField.getText();
		String dialogMsg = "";
		if(passwd!=null&&!passwd.equals("")&&username!=null&&!username.equals("")){
			user = t.verifyUser(username, passwd);
			//User user =null;
			if(user!=null){
				if(!user.getStatus().equals("0")){
					dialogMsg = "当前用户状态不是正常状态,请联系管理员";
				}else{
					//dialogMsg = "登录成功！";
					new MainWindow(t,user);
					jf.dispose();
					return;
				}
			}else{
				dialogMsg = "用户名密码错误";
			}
		}else{
			dialogMsg = "请填写完整再登录!";
		}
		JOptionPane.showMessageDialog(null,dialogMsg);
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
}
