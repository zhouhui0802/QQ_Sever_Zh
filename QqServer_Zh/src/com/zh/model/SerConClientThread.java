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
	
	//���߳�ȥ֪ͨ�����û�
	public void notifyOther(String iam)
	{
		System.out.println("����notifyOther����");
		//�õ������˵��߳�
		HashMap hm=ManageClientThread.hm;
		Iterator it=hm.keySet().iterator();
		
		while(it.hasNext())
		{
			Message m=new Message();
			m.setCon(iam);
			m.setMesType(MessageType.message_ret_onLineFriend);
			
			//ȡ������������id
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
				System.out.println("�ڰ˲�: ��ȡ��¼�û�����ͳ���������ź�");
				ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
				Message m=(Message)ois.readObject();	
				
				if(m.getMesType().equals(MessageType.message_get_onLineFriend))
				{
					//�ѷ������ĺ��Ѹ��ͻ��˷���
					String res=ManageClientThread.getAllOnLineUserid();	
					System.out.println("res"+res);
					Message m2=new Message();
					//�������˷������ߺ���
					m2.setMesType(MessageType.message_ret_onLineFriend);
					m2.setCon(res);
					m2.setGetter(m.getSender());  //�������˵Ľ��������û��˵ķ�����
					ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
					//System.out.println("��ʮ��: ��������ͳ�ƺõ�����д��");
					oos.writeObject(m2);		
					
				}else if(m.getMesType().equals(MessageType.message_comm_mes)||m.getMesType().equals(MessageType.message_file))
				{
					System.out.println("����������Ϣ�����ļ�����");
					SerConClientThread sc=ManageClientThread.getClientThread(m.getGetter());
					
					ObjectOutputStream oos=new ObjectOutputStream(sc.s.getOutputStream());
					oos.writeObject(m);
					
				}else if(m.getMesType().equals(MessageType.message_exit))//�뿪��Ϣ
				{
                    ManageClientThread.removeClientThread(m.getSender());
                    System.out.println("����رպ���֮������ͳ����������");
                    notifyOther(ManageClientThread.getAllOnLineUserid());//����������Ϣ
                    break;
                    
				}else if(m.getMesType().equals(MessageType.message_multi))
				{
                    for (int i = 0 ; i <= 20 ; i++){
                        if (ManageClientThread.getClientThread(i+"" )!= null && !(i+"").equals(m.getSender())){
                            m.setGetter(i+"");
                            SerConClientThread sc = ManageClientThread.getClientThread(i+"");
                            ObjectOutputStream oos = new ObjectOutputStream(sc.s.getOutputStream());
                            oos.writeObject(m);
                            System.out.println("����"+i+" ��Ϣ���ͳɹ�");
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
