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
				//��ȡ������������������
				System.out.println("�ڶ�������������ȡ�ӿͻ��˷�װ�õ��û�����");
				ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
				User u=(User)ois.readObject();
				
				//��ȡ����������֮������ݵ�Ԥ����
				Message m=new Message();
				ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
				
				if(u.getType().equals("0"))  //��һ�ֵ�¼��ʽ����֤
				{
					if(u.getPasswd().equals("123456"))
					{
						m.setMesType("1");
						oos.writeObject(m);
						//System.out.println("�����������û����ݽ��бȽϣ��õ���¼�ɹ����źţ�Ȼ����д��");
						
						//���ﵥ��һ���̣߳����߳���ͻ��˱���ͨѶ
						//System.out.println("���Ĳ�,����һ���̣߳����߳���ͻ��˱���ͨ��");
						SerConClientThread scct=new SerConClientThread(s);
						ManageClientThread.addClientThread(u.getUserId(), scct);
						//������ÿͻ���ͨ�ŵ��߳�
						scct.start();
						
						//����֪ͨ�����߳�			
						scct.notifyOther(ManageClientThread.getAllOnLineUserid());
					}else
					{
						m.setMesType("2");
						oos.writeObject(m);
						//�ر�����
						s.close();
					}
				}else if(u.getType().equals("1")) //�ڶ��ַ�ʽ  �绰��ʽ��֤
				{
					if(u.getPhonePasswd().equals("123456789"))
					{
						m.setMesType("1");
						oos.writeObject(m);
						System.out.println("�����������û����ݽ��бȽϣ��õ���¼�ɹ����źţ�Ȼ����д��");
						
						//���ﵥ��һ���̣߳����߳���ͻ��˱���ͨѶ
						System.out.println("���Ĳ�,����һ���̣߳����߳���ͻ��˱���ͨ��");
						SerConClientThread scct=new SerConClientThread(s);
						ManageClientThread.addClientThread(u.getUserId(), scct);
						//������ÿͻ���ͨ�ŵ��߳�
						scct.start();
						
						//����֪ͨ�����߳�
						//todo  ͨ�����ݿ��ѯ������Ӧ�ı��
						//scct.notifyOther(u.getUserId());
					}else
					{
						m.setMesType("2");
						oos.writeObject(m);
						//�ر�����
						s.close();
					}
				}else if(u.getType().equals("2"))  //�����ַ�ʽ  ������֤
				{
					if(u.getEmailPasswd().equals("123"))
					{
						m.setMesType("1");
						oos.writeObject(m);
						System.out.println("�����������û����ݽ��бȽϣ��õ���¼�ɹ����źţ�Ȼ����д��");
						
						//���ﵥ��һ���̣߳����߳���ͻ��˱���ͨѶ
						System.out.println("���Ĳ�,����һ���̣߳����߳���ͻ��˱���ͨ��");
						SerConClientThread scct=new SerConClientThread(s);
						ManageClientThread.addClientThread(u.getUserId(), scct);
						//������ÿͻ���ͨ�ŵ��߳�
						scct.start();
						
						//����֪ͨ�����߳�
						//todo  ͨ�����ݿ��ѯ�������Ӧ�ı��
						//scct.notifyOther(u.getUserId());
					}else
					{
						m.setMesType("2");
						oos.writeObject(m);
						//�ر�����
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
