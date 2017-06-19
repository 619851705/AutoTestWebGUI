package com.dcits.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import com.dcits.bean.User;
import com.dcits.bean.WebTestRmi;
import com.dcits.main.TestController;

public class MainWindow extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static final int WIDTH = 1000;
	static final int HEIGHT = 800;
	static JFrame jf = null;
	static JTable table = null;
	static DefaultTableModel defaultModel = null;
	TestController t = null;
	User user = null;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public MainWindow(final TestController t,final User user){
		this.t = t;
		this.user = user;
		//��ʼ����������
				jf = new JFrame("Web�Զ�������["+user.getRealName()+"]");
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
				
				//��Ӳ˵���
				JMenuBar menubar = new JMenuBar();
				jf.setJMenuBar(menubar);
				JMenu[] menus={new JMenu("����")};
				for(JMenu menu:menus){
					menubar.add(menu);
				}
				//�������
				Container container = jf.getContentPane();
				
				//--------------����������ı�񲿷�-------------------------
				JButton[] buttons = {new JButton("ִ�в���"),new JButton("��ΪʧЧ"),new JButton("ɾ������"),new JButton("ˢ�������б�"),new JButton("�˳�")};
			    String[] headers = {"����ID","��������","����","�ύʱ��","���ʱ��","״̬��","״̬����"};
				//Object[][] cells = new StuInfoClass().queryDatabase();
				//���ص�ǰ�û��������б�
			    Object[][] cells = t.verifyTasks(user.getUserId());			    			    
				defaultModel = new DefaultTableModel(cells, headers);
				table=new JTable(defaultModel);
				((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);//���ñ�ͷ����
				table.setPreferredScrollableViewportSize(new Dimension(400, 80));
				table.setRowHeight(25);//�����и߶�
				table.setGridColor(Color.lightGray);
				
				table.setRowSorter(new TableRowSorter(defaultModel));//���ñ���������
				table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//���ñ��ģʽΪ��ѡ
				
				
				//���ñ�����ݾ���
				 DefaultTableCellRenderer render = new DefaultTableCellRenderer();
			     render.setHorizontalAlignment(SwingConstants.CENTER);
			     for (String s:headers){
			    	 table.getColumn(s).setCellRenderer(render);
			     }
			     
				
				JScrollPane scrollpane = new JScrollPane(table);
				JPanel jp = new JPanel();
				Box hbox = Box.createHorizontalBox();
				jp.add(hbox);
				
				for(JButton button:buttons){
					hbox.add(Box.createHorizontalStrut(15));
					hbox.add(button);
					button.setFont(new Font("΢���ź�", Font.PLAIN, 15));
				}
				container.add(scrollpane,BorderLayout.CENTER);
				container.add(jp,BorderLayout.SOUTH);
				
				//------------------------������Ҫ��д��ذ�ť���¼�-------------------------
				
				//ִ��ѡ���������
				buttons[0].addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						int selectRow = table.getSelectedRow();
						try {
							if(selectRow!=-1){
								String statusCode = table.getValueAt(selectRow, 5).toString();
								if(statusCode.equals("1")){
									WebTestRmi task = t.findTask(Integer.valueOf(table.getValueAt(selectRow, 0).toString()));
									if(task!=null){
										Map<String,String> result=new HashMap<String,String>();
										if(task.getTestMode().equals("0")){
											//�����������Կ�ʼ
											result=t.caseTest(task,user);
										}else{
											//���Լ����Կ�ʼ
											result=t.setTest(task,user);						
										}
										if(result.get("testStutas").equals("0")){
											table.setValueAt("0", selectRow, 5);
											table.setValueAt("�������", selectRow, 6);
											table.setValueAt(result.get("finishTime"),selectRow,4);
											JOptionPane.showMessageDialog(null, "�������,�뵽�Զ�������ƽ̨-Web�Զ���-���Ա���ģ��鿴��ϸ���Ա���!");											
										}else{
											table.setValueAt(result.get("testStutas"), selectRow, 5);
											table.setValueAt("ϵͳ����,����ʧ��", selectRow, 6);									
											JOptionPane.showMessageDialog(null, "���Թ���������������:\n"+result.get("msg"));										
										}																				
									}else{
										JOptionPane.showMessageDialog(null, "��ǰ������������ѱ�ɾ��,������ˢ�±��鿴");
									}
								}else{
									JOptionPane.showMessageDialog(null, "��ǰ�����Ǵ�����״̬,������ѡ��");
								}
							}else{
								JOptionPane.showMessageDialog(null, "��ѡ��һ����������");
							}
						} catch (Exception e2) {
							JOptionPane.showMessageDialog(null, "������δ֪����:\n"+e2.toString());
						}												
					}
				});
				
				
				//ѡ��������ΪʧЧ
				buttons[1].addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						int selectRow = table.getSelectedRow();
						if(selectRow!=-1){					
							if(!table.getValueAt(selectRow, 5).equals("0")&&!table.getValueAt(selectRow, 5).equals("5")){								
								int n = JOptionPane.showConfirmDialog(null, "��ȷ�Ͻ���������ΪʧЧ��?", "ȷ�ϸ���",JOptionPane.YES_NO_OPTION);//���ص��ǰ�ť��index  i=0����1  
								if(n==0){
									String updateCode = t.updateTaskStatus(Integer.valueOf(table.getValueAt(selectRow, 0).toString()), "5");
									if(updateCode.equals("0")){
										table.setValueAt("5", selectRow, 5);
										table.setValueAt("������ʧЧ", selectRow, 6);								
										JOptionPane.showMessageDialog(null, "���³ɹ�!");
									}else{
										JOptionPane.showMessageDialog(null, "����ʧ��:\n"+updateCode);
									}
								}
							}else{
								JOptionPane.showMessageDialog(null, "������ɺ���ʧЧ״̬����ִ�д˲���!");
							}																						
						}else{
							JOptionPane.showMessageDialog(null, "��ѡ��һ����������");
						}
						
					}
				});
				//ɾ��ѡ������
				buttons[2].addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
							int selectRow = table.getSelectedRow();
							if(selectRow!=-1){
								int n = JOptionPane.showConfirmDialog(null, "��ȷ�Ͻ�������ɾ����?", "ȷ��ɾ��",JOptionPane.YES_NO_OPTION);//���ص��ǰ�ť��index  i=0����1 
								if(n==0){
									String delCode = t.delTask(Integer.valueOf(table.getValueAt(selectRow, 0).toString()));	
									if(delCode.equals("0")){
										defaultModel.removeRow(selectRow);
										JOptionPane.showMessageDialog(null, "ɾ���ɹ�!");
									}else{
										JOptionPane.showMessageDialog(null, "ɾ��ʧ��:\n"+delCode);
									}
								}													
							}else{
								JOptionPane.showMessageDialog(null, "��ѡ��һ����������");
							}
						
					}
				});
				
				
				//ˢ�������б�
				buttons[3].addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						String[] headers = {"����ID","��������","����","�ύʱ��","���ʱ��","״̬��","״̬����"};
						defaultModel.setDataVector(t.verifyTasks(user.getUserId()), headers);
						DefaultTableCellRenderer render = new DefaultTableCellRenderer();
					    render.setHorizontalAlignment(SwingConstants.CENTER);
					    for (String s:headers){
					    	 table.getColumn(s).setCellRenderer(render);
					     }
					}
				});
				
				
				//�˳���ť����
				buttons[4].addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						int n = JOptionPane.showConfirmDialog(null, "ȷ���˳����Կͻ�����", "ȷ���˳�",JOptionPane.YES_NO_OPTION);//���ص��ǰ�ť��index  i=0����1
						if(n==0){
							System.exit(0);
						}						
					}
				});
	}

}
