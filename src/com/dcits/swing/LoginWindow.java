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
		//��ʼ����������
		jf = new JFrame("web�Զ�������");
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
		
		//��ʼ������
		JPanel jp = new JPanel();
		jp.setBorder(BorderFactory.createTitledBorder(""));//���ñ߿�
		jf.setContentPane(jp);
		//����һ��BorderLayout
		BorderLayout borlayout = new BorderLayout();
		jf.setLayout(borlayout);
		
		JLabel username=new JLabel("��  ��:");//������ǩ������ı������
		username.setFont(new Font("΢���ź�", Font.PLAIN, 15));
	    nameField=new JTextField(15);
	    
	    nameField.setMaximumSize(nameField.getPreferredSize());
	    
	    Box hbox1=Box.createHorizontalBox();//����һ��ˮƽ����
	    hbox1.add(username); //��ˮƽ���������һ����ǩ��������Ҵ���һ�����ɼ��ġ�20����λ�����������֮�������һ���ı������
	    hbox1.add(Box.createHorizontalStrut(20));
	    hbox1.add(nameField);  
	    
	    JLabel passwd=new JLabel("��  ��:");//������ǩ������ı������
	    passwd.setFont(new Font("΢���ź�", Font.PLAIN, 15));
	    //JTextField passField=new JTextField(15);
	    passField = new JPasswordField(15);
	    passField.setMaximumSize(passField.getPreferredSize());
	    Box hbox2=Box.createHorizontalBox();//����һ��ˮƽ����
	    hbox2.add(passwd); //��ˮƽ���������һ����ǩ��������Ҵ���һ�����ɼ��ġ�20����λ�����������֮�������һ���ı������
	    hbox2.add(Box.createHorizontalStrut(20));
	    hbox2.add(passField);  
	    
	    //������¼���ڵ�ȷ����ť��ȡ����ť
	    JButton submit=new JButton("ȷ��");//����������ͨ��ť��������Ҵ���һ��ˮƽ���ӣ���������ť��ӵ�������
	    JButton cancel=new JButton("ȡ��");
	    submit.setFont(new Font("΢���ź�", Font.PLAIN, 15));
	    cancel.setFont(new Font("΢���ź�", Font.PLAIN, 15));
	    Box hbox3=Box.createHorizontalBox();
	    hbox3.add(submit);
	    hbox3.add(Box.createHorizontalStrut(120));//����������ť֮���ˮƽ����
	    hbox3.add(cancel);
	    
	    Box vbox=Box.createVerticalBox();//����һ����ֱ���ӣ�������ӽ�����ˮƽ������ӵ����У�����һ������ glue �����
	    vbox.add(Box.createVerticalStrut(30));
	    vbox.add(hbox1);
	    vbox.add(hbox2);
	    vbox.add(Box.createVerticalGlue());
	    vbox.add(hbox3);
	    jp.add(vbox,"Center"); // ����ֱ������ӵ�BorderLayout���ֹ������е��м�λ��  
	    
	    //������ť������¼�  
	    //ͨ���س�������ť����
	    submit.registerKeyboardAction(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//new MainForm();
				buttondeal(nameField,passField);
//		
			}
		}, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
	    
	    //ͨ�������ť��¼
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
					dialogMsg = "��ǰ�û�״̬��������״̬,����ϵ����Ա";
				}else{
					//dialogMsg = "��¼�ɹ���";
					new MainWindow(t,user);
					jf.dispose();
					return;
				}
			}else{
				dialogMsg = "�û����������";
			}
		}else{
			dialogMsg = "����д�����ٵ�¼!";
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
