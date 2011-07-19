package mobileswipeserver;

import java.util.*;

public class GsonObject {
//	{"version":"2.0","method":"disp.processBarcode","id":2,"params":[34234]}
	public String version;
	public String method;
	public int id;
	public LinkedList <Integer>params = new LinkedList<Integer>();
	public String test = "test";
}
