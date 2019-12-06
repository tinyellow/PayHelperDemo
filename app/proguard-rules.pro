# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-dontwarn android.net.**
-keep class android.net.SSLCertificateSocketFactory{*;}
#跳过它们不被混淆

-dontwarn com.alipay.**

#保持他们的类不被混淆
-keep class com.alipay.** { *;}

-keep public class com.littleyellow.payhelper.GlobalInfoProvider{ * ; }
