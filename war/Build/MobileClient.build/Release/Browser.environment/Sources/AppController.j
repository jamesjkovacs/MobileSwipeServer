@STATIC;1.0;I;21;Foundation/CPObject.jt;429;
objj_executeFile("Foundation/CPObject.j",NO);
var _1=objj_allocateClassPair(CPObject,"MyAppAppDelegate"),_2=_1.isa;
class_addIvars(_1,[new objj_ivar("window")]);
objj_registerClassPair(_1);
class_addMethods(_1,[new objj_method(sel_getUid("applicationDidFinishLaunching:"),function(_3,_4,_5){
with(_3){
}
}),new objj_method(sel_getUid("awakeFromCib"),function(_6,_7){
with(_6){
objj_msgSend(window,"setFullBridge:",YES);
}
})]);
