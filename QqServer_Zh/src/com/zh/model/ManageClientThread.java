package com.zh.model;

import java.util.HashMap;
import java.util.Iterator;

public class ManageClientThread {
	
	public static HashMap hm=new HashMap<String,SerConClientThread>();
	
	//向hm中添加一个客户端通讯线程
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
	
	//返回当前在线人数情况
	public static String getAllOnLineUserid()
	{
		System.out.println("第九步: 服务器统计所有在线人数给用户");
		
		
        Iterator<String> it = hm.keySet().iterator();
        String res = "";
        //System.out.println("在while前");
        while (it.hasNext()) {
          //  System.out.println("在while中");
            res += it.next() + " ";

        }
        //System.out.println("在while后");
        return res;
	}
}
