package com.code0.comm_test.server;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private final Logger logger=LoggerFactory.getLogger(getClass());

	public ServerUI() {
		super("server测试");
		
		taShow = new JTextArea();// 显示区域
		
		
		labelPort = new JLabel("绑定端口:");//端口标签
		tfPort = new JTextField("1313",5);// 端口文本框
		
		btLink = new JButton("启动Server");
		
		

		btLink.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CommServerStart server=new CommServerStart();
				server.start(Integer.valueOf(tfPort.getText()));
			}
		});
		
		JPanel bottomPanel = new JPanel(new FlowLayout());
		//TODO 配置显示按钮
		bottomPanel.add(labelPort);
		bottomPanel.add(tfPort);
		
		bottomPanel.add(btLink);
		
		
		final JScrollPane sp = new JScrollPane();
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		sp.setViewportView(this.taShow);
		this.taShow.setEditable(false);
		this.add(sp, BorderLayout.CENTER);
		

		this.add(bottomPanel, BorderLayout.SOUTH);
		bottomPanel.setPreferredSize(new Dimension(800,100));
	
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 500);
		this.setLocation(300, 100);
		this.setVisible(true);
	}

	/** @Fields labelPort : 端口文本框标签 */
	public JLabel labelPort;
	/** @Fields tfPort : 端口文本框 */
	public JTextField tfPort;
	
	/** @Fields btLink : 链接AGV */
	public JButton btLink;
	
	
	/** @Fields taShow : 信息显示区域 */
	public JTextArea taShow;
	
	
	
    //重写窗口事件，自定义关闭事件  
    @Override  
    protected void processWindowEvent(WindowEvent e) {  
        if (e.getID() == WindowEvent.WINDOW_CLOSING){
        	this.close(e.getComponent());
        	return; //直接返回，阻止默认动作，阻止窗口关闭
        }else{
        	super.processWindowEvent(e); //非关闭操作时，执行窗口事件的默认动作
        }
    }
    
    /**关闭窗口方法<br/>
     * 关闭前会进行询问
     * @param component 确定在其中显示对话框的 Frame；如果为 null 或者 parentComponent 不具有 Frame，则使用默认的 Frame
     */
    private void close(Component component){
		int a = JOptionPane.showConfirmDialog(component, "确定关闭吗？", "温馨提示",
				JOptionPane.YES_NO_OPTION);
		System.out.println("点击关闭按钮后返回值（0关闭1不关闭）："+a);
		if (a == JOptionPane.YES_OPTION) {
			System.out.println("值为0关闭窗口");
			System.exit(0); // 关闭
		}
    }
    
    /**向展示区域追加显示内容,当大于指定行数时,会清空展示区域
     * @param s
     */
    public synchronized void println(String s) {
        if (s != null) {
        	SimpleDateFormat smd=new SimpleDateFormat("HH:mm:ss");
        	String nowTime=smd.format(new Date());
        	s=nowTime+" "+s+"\n";
        	
        	JTextArea textArea=taShow;
        	int rows=textArea.getLineCount();
        	if(rows>=200){
        		textArea.setText(s);//大于指定行时，清空窗口。
        	}else{
        		textArea.append(s);
        	}
        	logger.info(s);
        }
    }
    
    public void displayAndWriter(String msg) {
    	//String pmsg=new StringBuilder("发送到客户端的消息为：").append(msg).toString();
    	println(msg);
    }
}