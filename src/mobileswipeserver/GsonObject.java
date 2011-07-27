package mobileswipeserver;

import java.util.*;

public class GsonObject {
//	{"version":"2.0","method":"disp.processBarcode","id":2,"params":[34234]}
	public String getVersion() {
		return version;
	}
	public String getMethod() {
		return method;
	}
	public int getId() {
		return id;
	}
	public String getParam() {
		return param;
	}
	public int[] getParams() {
		return params;
	}
	private String version;
	private String method;
	private int id;
	private String param;
	private int[] params = new int[1];
	//public LinkedList <Integer>params = new LinkedList<Integer>();
	private String test = "test";
}
