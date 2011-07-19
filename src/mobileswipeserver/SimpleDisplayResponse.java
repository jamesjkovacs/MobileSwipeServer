package mobileswipeserver;


public class SimpleDisplayResponse implements DisplayResponse{
	
	public String display()
	{
		return "this is a test";
	}
	public String processBarcode(String barcode)
	{
		return barcode;
	}
}
