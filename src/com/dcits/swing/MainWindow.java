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
		//初始化窗体设置
				jf = new JFrame("Web自动化测试["+user.getRealName()+"]");
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
				
				//添加菜单条
				JMenuBar menubar = new JMenuBar();
				jf.setJMenuBar(menubar);
				JMenu[] menus={new JMenu("帮助")};
				for(JMenu menu:menus){
					menubar.add(menu);
				}
				//添加容器
				Container container = jf.getContentPane();
				
				//--------------创建主窗体的表格部分-------------------------
				JButton[] buttons = {new JButton("执行测试"),new JButton("置为失效"),new JButton("删除任务"),new JButton("刷新任务列表"),new JButton("退出")};
			    String[] headers = {"任务ID","任务名称","类型","提交时间","完成时间","状态码","状态详情"};
				//Object[][] cells = new StuInfoClass().queryDatabase();
				//加载当前用户的任务列表
			    Object[][] cells = t.verifyTasks(user.getUserId());			    			    
				defaultModel = new DefaultTableModel(cells, headers);
				table=new JTable(defaultModel);
				((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);//设置表头居中
				table.setPreferredScrollableViewportSize(new Dimension(400, 80));
				table.setRowHeight(25);//设置行高度
				table.setGridColor(Color.lightGray);
				
				table.setRowSorter(new TableRowSorter(defaultModel));//设置表格的排序器
				table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//设置表格模式为单选
				
				
				//设置表格内容居中
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
					button.setFont(new Font("微软雅黑", Font.PLAIN, 15));
				}
				container.add(scrollpane,BorderLayout.CENTER);
				container.add(jp,BorderLayout.SOUTH);
				
				//------------------------以下主要编写相关按钮的事件-------------------------
				
				//执行选中任务测试
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
											//测试用例测试开始
											result=t.caseTest(task,user);
										}else{
											//测试集测试开始
											result=t.setTest(task,user);						
										}
										if(result.get("testStutas").equals("0")){
											table.setValueAt("0", selectRow, 5);
											table.setValueAt("测试完成", selectRow, 6);
											table.setValueAt(result.get("finishTime"),selectRow,4);
											JOptionPane.showMessageDialog(null, "测试完成,请到自动化测试平台-Web自动化-测试报告模块查看详细测试报告!");											
										}else{
											table.setValueAt(result.get("testStutas"), selectRow, 5);
											table.setValueAt("系统错误,测试失败", selectRow, 6);									
											JOptionPane.showMessageDialog(null, "测试过程中遇到了问题:\n"+result.get("msg"));										
										}																				
									}else{
										JOptionPane.showMessageDialog(null, "当前测试任务可能已被删除,请重新刷新表格查看");
									}
								}else{
									JOptionPane.showMessageDialog(null, "当前任务不是待测试状态,请重新选择");
								}
							}else{
								JOptionPane.showMessageDialog(null, "请选择一个测试任务");
							}
						} catch (Exception e2) {
							JOptionPane.showMessageDialog(null, "发生了未知错误:\n"+e2.toString());
						}												
					}
				});
				
				
				//选中任务置为失效
				buttons[1].addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						int selectRow = table.getSelectedRow();
						if(selectRow!=-1){					
							if(!table.getValueAt(selectRow, 5).equals("0")&&!table.getValueAt(selectRow, 5).equals("5")){								
								int n = JOptionPane.showConfirmDialog(null, "你确认将此任务置为失效吗?", "确认更新",JOptionPane.YES_NO_OPTION);//返回的是按钮的index  i=0或者1  
								if(n==0){
									String updateCode = t.updateTaskStatus(Integer.valueOf(table.getValueAt(selectRow, 0).toString()), "5");
									if(updateCode.equals("0")){
										table.setValueAt("5", selectRow, 5);
										table.setValueAt("任务已失效", selectRow, 6);								
										JOptionPane.showMessageDialog(null, "更新成功!");
									}else{
										JOptionPane.showMessageDialog(null, "更新失败:\n"+updateCode);
									}
								}
							}else{
								JOptionPane.showMessageDialog(null, "测试完成和已失效状态不能执行此操作!");
							}																						
						}else{
							JOptionPane.showMessageDialog(null, "请选择一个测试任务");
						}
						
					}
				});
				//删除选中任务
				buttons[2].addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
							int selectRow = table.getSelectedRow();
							if(selectRow!=-1){
								int n = JOptionPane.showConfirmDialog(null, "你确认将此任务删除吗?", "确认删除",JOptionPane.YES_NO_OPTION);//返回的是按钮的index  i=0或者1 
								if(n==0){
									String delCode = t.delTask(Integer.valueOf(table.getValueAt(selectRow, 0).toString()));	
									if(delCode.equals("0")){
										defaultModel.removeRow(selectRow);
										JOptionPane.showMessageDialog(null, "删除成功!");
									}else{
										JOptionPane.showMessageDialog(null, "删除失败:\n"+delCode);
									}
								}													
							}else{
								JOptionPane.showMessageDialog(null, "请选择一个测试任务");
							}
						
					}
				});
				
				
				//刷新任务列表
				buttons[3].addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						String[] headers = {"任务ID","任务名称","类型","提交时间","完成时间","状态码","状态详情"};
						defaultModel.setDataVector(t.verifyTasks(user.getUserId()), headers);
						DefaultTableCellRenderer render = new DefaultTableCellRenderer();
					    render.setHorizontalAlignment(SwingConstants.CENTER);
					    for (String s:headers){
					    	 table.getColumn(s).setCellRenderer(render);
					     }
					}
				});
				
				
				//退出按钮功能
				buttons[4].addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						int n = JOptionPane.showConfirmDialog(null, "确认退出测试客户端吗？", "确认退出",JOptionPane.YES_NO_OPTION);//返回的是按钮的index  i=0或者1
						if(n==0){
							System.exit(0);
						}						
					}
				});
	}

}
