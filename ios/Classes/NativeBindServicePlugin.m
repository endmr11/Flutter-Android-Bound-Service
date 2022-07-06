#import "NativeBindServicePlugin.h"
#if __has_include(<native_bind_service/native_bind_service-Swift.h>)
#import <native_bind_service/native_bind_service-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "native_bind_service-Swift.h"
#endif

@implementation NativeBindServicePlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftNativeBindServicePlugin registerWithRegistrar:registrar];
}
@end
