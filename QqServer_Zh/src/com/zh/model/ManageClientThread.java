package com.zh.model;

import java.util.HashMap;
import java.util.Iterator;

public class ManageClientThread {
	
	public static HashMap hm=new HashMap<String,SerConClientThread>();
	
	//��hm�����һ���ͻ���ͨѶ�߳�
	public static void addClientThread(String uid,SerConClientThread ct)
	{
		hm.put(uid, ct);
	}
	
    public static void removeClientThread(String uid){
        hm.remove(uid);
    }
    
	public static SerConClientThread getClientThread(String uid)
	{
		return (SerConClientThread)hm.get(uid);
	}
	
	//���ص�ǰ�����������
	public static String getAllOnLineUserid()
	{
		System.out.println("�ھŲ�: ������ͳ�����������������û�");
		
		
        Iterator<String> it = hm.keySet().iterator();
        String res = "";
        //System.out.println("��whileǰ");
        while (it.hasNext()) {
          //  System.out.println("��while��");
            res += it.next() + " ";

        }
        //System.out.println("��while��");
        return res;
	}
}
