package mobileswipeserver;


public class SimpleDisplayResponse implements DisplayResponse{
	
	public String display()
	{
		return "this is a test";
	}
	public BarcodeResults processBarcode(int barcode)
	{
		BarcodeResults results = new BarcodeResults();
		results.barcode = String.valueOf(barcode);
		return results;
	}
	public String rpcprocessBarcode(String barcode)
	{
		BarcodeResults results = new BarcodeResults();
		results.barcode = String.valueOf(barcode);
		return barcode;
	}
}
