/*
 * AppController.j
 * MobileClient
 *
 * Created by You on July 6, 2011.
 * Copyright 2011, Your Company All rights reserved.
 */

@import <Foundation/CPObject.j>


@implementation MyAppAppDelegate : CPObject
{
    CPWindow    window; //this "outlet" is connected automatically by the Cib
    CPTextField *barcodeField;
    CPTextField *longitudeField;
    CPTextField *latitudeField;
	var displayMessage;
	var saveBarcode;
}

- (void)sendEmpLoc: (id)sender
{
    [barcodeField setStringValue: @"Hello, world!"];
//    CPLoRegister(CPLogPopup);
	alert("just before debug message") ;
    CPLog.debug("a debug message");
//	request = [[CPURLRequest alloc] initWithURL:@"http://127.0.0.1:8080/jsonrpc"];
	var request = [CPURLRequest requestWithURL:@"/jsonrpc"] ;
	[request setHTTPMethod: @"POST"];
	alert("sendEmpLoc break point #2") ;
	[request setHTTPBody:"{\"version\":\"2.0\",\"method\":\"disp.display\",\"id\":1}"];

	displayMessage = [CPURLConnection connectionWithRequest:request delegate:self];
//	[barcodeConnection start];
		
//	barcodeConnection = [CPURLConnection connectionWithRequest: request delegate: self];
}

-(CPString)addParam: (CPString)param
{
    retParm = "[" + param + "]";
//CPLog.debug("test = " + param);
	return retParm;
}

-(void)procBarcode: (id)sender
{
	alert("procBarcode") ;
	var request = [CPURLRequest requestWithURL:@"/jsonrpc"] ;
	[request setHTTPMethod: @"POST"];
	var httpBody = "{\"version\":\"2.0\",\"method\":\"disp.processBarcode\",\"id\":2,\"params\":[";
	httpBody = httpBody + [barcodeField stringValue];
	httpBody = httpBody + "]}";
	CPLog.debug(httpBody);
	var jsonString = @"{\"version\":\"2.0\",\"method\":\"disp.processBarcode\",\"id\":2,\"params\":0}";
	CPLog.debug("jsonString = " + jsonString);
	
	var jsObject = [jsonString objectFromJSON];
	CPLog.debug("jsObject = " + jsObject);
	
	jsObject.params = [self addParam:[barcodeField stringValue]];
//	addParam:"test";
	
	CPLog.debug("jsObject.params = " + jsObject.params);
	
	var jsonString2 = [CPString JSONFromObject:jsObject];
	CPLog.debug("jsonString2 = " + jsonString2);

	[request setHTTPBody:httpBody];
	saveBarcode = [CPURLConnection connectionWithRequest:request delegate:self];
	
}

- (void)connection:(CPURLConnection) connection didReceiveData:(CPString)data
{
	var jdata = JSON.parse(data);
	if (connection == displayMessage)
	{
		alert(data) ;
		alert("results received in jdata.result were: " + jdata.result) ;
		[self showSearchResults:jdata] ;
	}
	else if (connection == saveBarcode)
	{
		alert("Barcode processed was:" + jdata.result) ;
		[self clearConnection:saveBarcode];
		
	}
}

- (void)connection:(CPURLConnection)connection didFailWithError:(CPString)error
{
    alert(error) ;
	if (connection == displayMessage)
	{
		alert("displayMessage failed")
		[self clearConnection:displayMessage];
	}
	else if (connection == saveBarcode)
	{
		alert("saveBarcode failed")
		[self clearConnection:saveBarcode];
	}
}


- (void)showSearchResults: jdata
{
    alert("results received were: " + jdata.result) ;
	[self clearConnection:displayMessage];
}

- (void)clearConnection:(CPURLConnection)aConnection
{
    //we no longer need to hold on to a reference to this connection
    if (aConnection == displayMessage)
        displayMessage = nil;
	else if (aConnection == saveBarcode)
    	saveBarcode = nil;
}
	
- (void)applicationDidFinishLaunching:(CPNotification)aNotification
{
	alert("didfinishlaunchin break point #1") ;
    // This is called when the application is done loading.
}

- (void)awakeFromCib
{
    // This is called when the cib is done loading.
    // You can implement this method on any object instantiated from a Cib.
    // It's a useful hook for setting up current UI values, and other things. 
    
    // In this case, we want the window from Cib to become our full browser window
    [window setFullBridge:YES];
}

- (void)testJscript: (id)sender
{
	CPLog.debug("enter testJscript");
	<script src="js/jsonrpc.js"></script>
//	<script>
	var jsonService = new JsonRpc.ServiceProxy("/jsonrpc", {
	            asynchronous: true,
	            methods: ['disp.processBarcode', 'disp.display']
	        });

	// access it asynchronously
	JsonRpc.setAsynchronous(jsonService, true);
	jsonService.disp.processBarcode({
	    params:[9999],
	    onSuccess: function(result) {
	        alert("result is " + result);
	    },
	    onException: function(e) {
	        alert("Unable to compute because: " + e);
	        return true;
	    }
	});
//	</script>
}

@end
