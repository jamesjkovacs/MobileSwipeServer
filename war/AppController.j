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
}

- (void)sendEmpLoc: (id)sender
{
    [barcodeField setStringValue: @"Hello, world!"];
//    CPLoRegister(CPLogPopup);
    CPLog.debug("a debug message");
//	request = [[CPURLRequest alloc] initWithURL:@"http://127.0.0.1:8080/jsonrpc"];
	var request = [CPURLRequest requestWithURL:@"/jsonrpc"] ;
	[request setHTTPMethod: @"POST"];
/*	[request setValue:"disp.display"
			 forHTTPHeaderField:"method"];	
	[request setValue:"2.0"
			 forHTTPHeaderField:"version"];	
	[request setValue:"1"
			 forHTTPHeaderField:"id"];	*/
			alert("bp1") ;
	[request setHTTPBody:"{\"version\":\"2.0\",\"method\":\"disp.display\",\"id\":1}"];

	barcodeConnection = [CPURLConnection connectionWithRequest:request delegate:self];
//	[barcodeConnection start];
		
//	barcodeConnection = [CPURLConnection connectionWithRequest: request delegate: self];
}

- (void)connection:(CPURLConnection) connection didReceiveData:(CPString)data
{
	alert(data) ;
//    CPJSObjectCreateWithJSON(data);
    var jdata = JSON.parse(data);
    alert("results received were: " + jdata.result) ;
	[self showSearchResults:data] ;
//	[self showSearchResults:data] ;
}

- (void)connection:(CPURLConnection)connection didFailWithError:(CPString)error
{
	alert("bp3") ;
    alert(error) ;
	[self clearConnection:barcodeConnection];
}


- (void)showSearchResults:(CPString)result
{
    alert("results received were: " + result) ;
	[self clearConnection:barcodeConnection];
}

- (void)clearConnection:(CPURLConnection)aConnection
{
    //we no longer need to hold on to a reference to this connection
    if (aConnection == barcodeConnection)
        barcodeConnection = nil;
}
	
- (void)applicationDidFinishLaunching:(CPNotification)aNotification
{
	alert("bp0") ;
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

@end
