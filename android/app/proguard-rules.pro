# Retrofit
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

# Kotlin Serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.SerializationKt
-keep,includedescriptorclasses class com.ootd.app.**$$serializer { *; }
-keepclasseswithmembers class com.ootd.app.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# Room
-keep class androidx.room.** { *; }
-keep @androidx.room.Entity class * { *; }
-keepattributes *Annotation*

# Hilt
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }

# Compose
-keep class androidx.compose.** { *; }

# OkHttp
-dontwarn okhttp3.**
-dontwarn okio.**

# General
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile
