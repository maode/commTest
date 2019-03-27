package com.code0.comm_test;

import com.code0.comm_test.server.ServerUI;

/**  
 * @Title: Utils.java
 * @Package com.code0.comm_test
 * @Description: TODO
 * @author ZhengMaoDe   
 * @date 2019年3月25日 下午1:02:04 
 */
public class Utils {

	
	private static ServerUI sui;
	
	public static ServerUI getServerUI() {
		if(sui==null) {
			sui=new ServerUI();
		}
		return sui;
	}
}
