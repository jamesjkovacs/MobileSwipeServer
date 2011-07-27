package mobileswipeserver;

public interface DisplayResponse {

	   String display();
	   BarcodeResults processBarcode(int barcode);
	   String rpcprocessBarcode(String barcode);
	}
