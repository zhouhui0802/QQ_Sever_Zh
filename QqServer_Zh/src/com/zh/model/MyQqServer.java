package com.zh.model;

import java.io.*;
import java.net.*;


import com.zh.common.Message;
import com.zh.common.User;

public class MyQqServer {

	public MyQqServer()
	{
		try{
			
			ServerSocket ss=new ServerSocket(9999);
			
			while(true)
			{
				Socket s=ss.accept();
				//读取处理器传过来的数据
				System.out.println("第二步，服务器读取从客户端封装好的用户数据");
				ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
				User u=(User)ois.readObject();
				
				//读取服务器处理之后的数据的预处理
				Message m=new Message();
				ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
				
				if(u.getType().equals("0"))  //第一种登录方式的验证
				{
					if(u.getPasswd().equals("123456"))
					{
						m.setMesType("1");
						oos.writeObject(m);
						//System.out.println("第三步，将用户数据进行比较，得到登录成功的信号，然后将其写入");
						
						//这里单开一个线程，该线程与客户端保持通讯
						//System.out.println("第四步,单开一个线程，该线程与客户端保持通信");
						SerConClientThread scct=new SerConClientThread(s);
						ManageClientThread.addClientThread(u.getUserId(), scct);
						//启动与该客户端通信的线程
						scct.start();
						
						//并且通知其他线程			
						scct.notifyOther(ManageClientThread.getAllOnLineUserid());
					}else
					{
						m.setMesType("2");
						oos.writeObject(m);
						//关闭连接
						s.close();
					}
				}else if(u.getType().equals("1")) //第二种方式  电话方式验证
				{
					if(u.getPhonePasswd().equals("123456789"))
					{
						m.setMesType("1");
						oos.writeObject(m);
						System.out.println("第三步，将用户数据进行比较，得到登录成功的信号，然后将其写入");
						
						//这里单开一个线程，该线程与客户端保持通讯
						System.out.println("第四步,单开一个线程，该线程与客户端保持通信");
						SerConClientThread scct=new SerConClientThread(s);
						ManageClientThread.addClientThread(u.getUserId(), scct);
						//启动与该客户端通信的线程
						scct.start();
						
						//并且通知其他线程
						//todo  通过数据库查询与号码对应的编号
						//scct.notifyOther(u.getUserId());
					}else
					{
						m.setMesType("2");
						oos.writeObject(m);
						//关闭连接
						s.close();
					}
				}else if(u.getType().equals("2"))  //第三种方式  邮箱验证
				{
					if(u.getEmailPasswd().equals("123"))
					{
						m.setMesType("1");
						oos.writeObject(m);
						System.out.println("第三步，将用户数据进行比较，得到登录成功的信号，然后将其写入");
						
						//这里单开一个线程，该线程与客户端保持通讯
						System.out.println("第四步,单开一个线程，该线程与客户端保持通信");
						SerConClientThread scct=new SerConClientThread(s);
						ManageClientThread.addClientThread(u.getUserId(), scct);
						//启动与该客户端通信的线程
						scct.start();
						
						//并且通知其他线程
						//todo  通过数据库查询与邮箱对应的编号
						//scct.notifyOther(u.getUserId());
					}else
					{
						m.setMesType("2");
						oos.writeObject(m);
						//关闭连接
						s.close();
					}
				}

				
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			
		}finally{
			
		}
	}
}
