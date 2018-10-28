# This is a configuration file for ProGuard.
# http://proguard.sourceforge.net/index.html#manual/usage.html

-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-verbose

# Optimization is turned off by default. Dex does not like code run
# through the ProGuard optimize and preverify steps (and performs some
# of these optimizations on its own).
-dontoptimize
-dontpreverify

-keepattributes *Annotation*
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService

# For native methods, see http://proguard.sourceforge.net/manual/examples.html#native
-keepclasseswithmembernames class * {
    native <methods>;
}

# keep setters in Views so that animations can still work.
# see http://proguard.sourceforge.net/manual/examples.html#beans
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}

# We want to keep methods in Activity that could be used in the XML attribute onClick
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

# For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep public class com.aspirecn.eplus.R$*{
public static final int *;
}
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}


-keepclassmembers class **.R$* {
    public static <fields>;
}

# The support library contains references to newer platform versions.
# Don't warn about those in case this app is linking against an older
# platform version.  We know about them, and they are safe.
-dontwarn android.support.**
-keep class android.support.v4.** { *; }

##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.

# Explicitly preserve all serialization members. The Serializable interface
# is only a marker interface, so it wouldn't save them.
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
    public <fields>;
}

-keepattributes Signature

-dontwarn com.umeng.**
-dontwarn com.google.gson.**
-dontwarn com.google.common.**
-dontwarn org.apache.commons.codec.**
-dontwarn com.aspire.pinyin.**
-dontwarn okio.**
-dontwarn com.squareup.**
-dontwarn okhttp3.**
-dontwarn retrofit2.**
-dontwarn io.reactivex.**
-dontwarn org.reactivestreams.**


-keep class com.google.gson.**{*;}
-keep class com.google.common.**{*;}
-keep class org.apache.commons.codec.**{*;}
-keep class com.google.webp.**{*;}
-keep class * extends java.lang.annotation.Annotation { *; }
-keep class okio.**{*;}
-keep class com.squareup.**{*;}
-keep class okhttp3.**{*;}
-keep class retrofit2.**{*;}
-keep class io.reactivex.**{*;}
-keep class org.reactivestreams.**{*;}


-keep class com.aspirecn.sports.db.**{*;}
-keep class com.aspirecn.sports.data.**{*;}
-keep class com.aspirecn.library.** {*;}

#滚轮控件
-keep class com.contrarywind.view.**{*;}
-keep class com.itheima.wheelpicker.**{*;}

# Room Database
-keep class android.arch.persistence.room.**{*;}
-keep class android.arch.lifecycle.LiveData {*;}

-keep public class * implements com.bumptech.glide.module.GlideModule

-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}


-dontwarn com.aspire.pinyin.**
-keep class com.aspire.pinyin.**{*;}

# -- duplicate in target 25
-dontnote android.net.http.**
-dontnote org.apache.commons.codec.**
-dontnote org.apache.http.**

-dontnote com.aspirecn.sports.widget.**
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
-dontnote retrofit2.Platform
-dontnote okhttp3.internal.platform.Platform
-dontnote okhttp3.internal.platform.AndroidPlatform
-dontnote okhttp3.internal.platform.AndroidPlatform$CloseGuard
-dontnote com.scwang.smartrefresh.**

# 微信分享sdk
-keep class com.tencent.** {
   *;
}

-keep class com.jzh.parents.utils.Util$Companion{*;}

-keep class com.jzh.parents.viewmodel.bindadapter.TSBindingAdapter$Companion{*;}
-keep class com.jzh.parents.app.JZHApplication$Companion{*;}
-keep class com.jzh.parents.viewmodel.bindadapter.TSDataBindingComponent{*;}
-keep class com.jzh.parents.db.**{*;}
-keep class com.jzh.parents.datamodel.response.**{*;}
-keep class com.jzh.parents.datamodel.data.**{*;}


-dontnote android.arch.persistence.**
-dontnote com.aspirecn.sports.db.**
-dontnote android.arch.lifecycle.LiveData
-dontnote android.view.MiuiWindowManager$LayoutParams
-dontnote android.arch.persistence.db.SupportSQLiteOpenHelper$Callback

-dontnote kotlin.internal.PlatformImplementationsKt
-dontnote kotlin.jvm.internal.Reflection
-dontnote com.google.gson.internal.UnsafeAllocator

-keep class com.tencent.mm.opensdk.** {
    *;
}
-keep class com.tencent.wxop.** {
    *;
}
-keep class com.tencent.mm.sdk.** {
    *;
}

-keep class com.alibaba.sdk.android.oss.** { *; }
-dontwarn okio.**
-dontwarn org.apache.commons.codec.binary.**

-keepclasseswithmembernames class ** {
    native <methods>;
}
-keepattributes Signature
-keep class sun.misc.Unsafe { *; }
-keep class com.taobao.** {*;}
-keep class com.alibaba.** {*;}
-keep class com.alipay.** {*;}
-dontwarn com.taobao.**
-dontwarn com.alibaba.**
-dontwarn com.alipay.**
-keep class com.ut.** {*;}
-dontwarn com.ut.**
-keep class com.ta.** {*;}
-dontwarn com.ta.**
-keep class anet.**{*;}
-keep class org.android.spdy.**{*;}
-keep class org.android.agoo.**{*;}
-dontwarn anet.**
-dontwarn org.android.spdy.**
-dontwarn org.android.agoo.**

-dontnote anet.channel.**
-dontnote com.alibaba.**
-dontnote com.jzh.parents.widget.**
-dontnote com.taobao.**
-dontnote com.ut.mini.**
-dontnote com.jzh.parents.viewmodel.bindadapter.TSBindingAdapter$Companion
-dontnote com.jzh.parents.app.JZHApplication$Companion
-dontnote com.jzh.parents.viewmodel.bindadapter.TSDataBindingComponent
-dontnote com.bumptech.glide.Glide
-dontnote com.ta.utdid2.**
-dontnote com.jzh.parents.datamodel.data.BannerData
-dontnote sun.misc.Unsafe
-dontnote com.jzh.parents.datamodel.data.CategoryData
-dontnote com.jzh.parents.datamodel.response.BaseRes
-dontnote com.jzh.parents.utils.Util$Companion
-dontnote com.jzh.parents.datamodel.data.RecommendData
-dontnote com.jzh.parents.db.entry.MessageEntry