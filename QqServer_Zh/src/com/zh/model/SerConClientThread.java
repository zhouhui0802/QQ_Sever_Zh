package com.zh.model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

import com.zh.common.Message;
import com.zh.common.MessageType;

public class SerConClientThread extends Thread{
	
	Socket s;
	
	public SerConClientThread(Socket s)
	{
		this.s=s;
	}
	
	//让线程去通知其他用户
	public void notifyOther(String iam)
	{
		System.out.println("调用notifyOther函数");
		//得到所有人的线程
		HashMap hm=ManageClientThread.hm;
		Iterator it=hm.keySet().iterator();
		
		while(it.hasNext())
		{
			Message m=new Message();
			m.setCon(iam);
			m.setMesType(MessageType.message_ret_onLineFriend);
			
			//取出在线人数的id
			String onLineUserId=it.next().toString();
			System.out.println("onLineUserId="+onLineUserId);
			
			try{
				ObjectOutputStream oos=new ObjectOutputStream(ManageClientThread.getClientThread(onLineUserId).s.getOutputStream());
				m.setGetter(onLineUserId);
				oos.writeObject(m);
				
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
	}
	
	public void run()
	{
		while(true)
		{
			try{
				System.out.println("第八步: 读取登录用户发出统计人数的信号");
				ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
				Message m=(Message)ois.readObject();	
				
				if(m.getMesType().equals(MessageType.message_get_onLineFriend))
				{
					//把服务器的好友给客户端返回
					String res=ManageClientThread.getAllOnLineUserid();	
					System.out.println("res"+res);
					Message m2=new Message();
					//服务器端返回在线好友
					m2.setMesType(MessageType.message_ret_onLineFriend);
					m2.setCon(res);
					m2.setGetter(m.getSender());  //服务器端的接受者是用户端的发送者
					ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
					//System.out.println("第十部: 服务器将统计好的人数写入");
					oos.writeObject(m2);		
					
				}else if(m.getMesType().equals(MessageType.message_comm_mes)||m.getMesType().equals(MessageType.message_file))
				{
					System.out.println("进入聊天信息或者文件传输");
					SerConClientThread sc=ManageClientThread.getClientThread(m.getGetter());
					
					ObjectOutputStream oos=new ObjectOutputStream(sc.s.getOutputStream());
					oos.writeObject(m);
					
				}else if(m.getMesType().equals(MessageType.message_exit))//离开消息
				{
                    ManageClientThread.removeClientThread(m.getSender());
                    System.out.println("进入关闭函数之后重新统计在线人数");
                    notifyOther(ManageClientThread.getAllOnLineUserid());//发送下线消息
                    break;
                    
				}else if(m.getMesType().equals(MessageType.message_multi))
				{
                    for (int i = 0 ; i <= 20 ; i++){
                        if (ManageClientThread.getClientThread(i+"" )!= null && !(i+"").equals(m.getSender())){
                            m.setGetter(i+"");
                            SerConClientThread sc = ManageClientThread.getClientThread(i+"");
                            ObjectOutputStream oos = new ObjectOutputStream(sc.s.getOutputStream());
                            oos.writeObject(m);
                            System.out.println("给："+i+" 消息发送成功");
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
}
