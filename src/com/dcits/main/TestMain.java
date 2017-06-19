package com.dcits.main;

import java.text.ParseException;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dcits.swing.LoginWindow;

public class TestMain {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws InterruptedException, ParseException {
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		final TestController t=(TestController) ctx.getBean("testController");
		
		try {
			//ʹ�ù۸���
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			//ʹ�ó�ʼ���߳�
			SwingUtilities.invokeAndWait(new Runnable() {
				
				@SuppressWarnings("unused")
				@Override
				public void run() {					
					LoginWindow loginGui = new LoginWindow(t);
					//User user = loginGui.getUser();
				}
			});
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,"��������\n"+e.toString());
		}
	}
	
	
}
