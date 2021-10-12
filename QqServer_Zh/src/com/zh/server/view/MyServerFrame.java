package com.zh.server.view;

import java.awt.event.*;

import javax.swing.*;

import com.zh.model.MyQqServer;
import com.zh.util.FrameUtil;

public class MyServerFrame extends JFrame implements ActionListener{

	JPanel jp1;
	JButton jb1,jb2;
	MyQqServer mqs;
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource()==jb1)
		{
			System.out.println("启动服务器");
			mqs=new MyQqServer();
		}else if(arg0.getSource()==jb2)
		{
			System.out.println("关闭服务器");
			
			this.dispose();
			System.gc();
			//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
	}
	
	public static void main(String args[])
	{
		MyServerFrame mf=new MyServerFrame();
	}
	
	public MyServerFrame()
	{
		jp1=new JPanel();
		jb1=new JButton("启动服务器");
		jb1.addActionListener(this);
		jb2=new JButton("关闭服务器");
		jb2.addActionListener(this);
		jp1.add(jb1);
		jp1.add(jb2);
		
		this.add(jp1);
		this.setSize(500, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		FrameUtil.setFrameCenter(this);
	}

}
