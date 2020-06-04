require "json"
package = JSON.parse(File.read('../package.json'))

Pod::Spec.new do |s|
  s.name         = 'RNJYPay'
  s.version      = package['version']
  s.summary      = package['description']
  s.requires_arc = true
  s.license      = "MIT"
  s.homepage     = "https://github.com/qijingyu2013/react-native-jy-pay"
  s.source       = { :git => "https://github.com/qijingyu2013/react-native-jy-pay", :tag => "1.0.1" }
  s.author       = "qijingyu2013"
  s.source_files = "**/*.{h,m}"
  s.platform     = :ios, "8.0"

  s.dependency "React"
  s.dependency "AlipaySDK-iOS"
  #s.resource = "AlipaySDK.bundle"
  #s.vendored_frameworks = 'AlipaySDK.framework'
  s.vendored_libraries = "libWeChatSDK.a"
  s.frameworks = "SystemConfiguration", "CoreTelephony", "QuartzCore", "CoreText", "CoreGraphics", "UIKit", "Foundation", "CFNetwork", "CoreMotion"
  s.library = "c++", "z", "sqlite3.0", "sqlite3"

end

