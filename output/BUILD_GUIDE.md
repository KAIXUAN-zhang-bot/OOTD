# OOTD Android APK 构建指南

## 项目概述

OOTD (今日穿搭) 是一个基于 Jetpack Compose 和现代 Android 架构的 AI 驱动的穿搭推荐应用。

## 系统要求

### 必需软件
- **JDK 17+** - Java Development Kit
- **Android SDK** - API Level 26 (Android 8.0) 及以上
- **Android Build Tools** - 最新版本推荐
- **Gradle Wrapper** - 项目内已配置

### 可选工具
- **Android Studio** - 官方集成开发环境
- **Android Emulator** - 用于测试
- **ADB** - Android Debug Bridge（用于真机调试）

## 快速开始

### 1. 环境检查

```bash
# 检查 Java 版本
java -version

# 检查 Android SDK 
echo $ANDROID_HOME
```

### 2. 项目准备

```bash
# 进入 Android 项目目录
cd /Users/zhangkaixuan/claude_code/OOTD/android

# 查看项目结构
ls -la
```

## 构建配置

### build.gradle.kts (Root)
- Android Gradle Plugin: 8.3.0
- Kotlin: 2.0.0
- Kotlin Serialization Plugin 支持

### gradle.properties
```properties
org.gradle.jvmargs=-Xmx2048m
org.gradle.parallel=true
org.gradle.caching=true
android.useAndroidX=true
android.nonTransitiveRClass=true
appVersionCode=1
appVersionName=1.0.0
minSdkVersion=26
targetSdkVersion=34
compileSdkVersion=34
composeVersion=1.7.0
kotlinVersion=2.0.0
```

### app/build.gradle.kts
- Application ID: `com.ootd.app`
- Min SDK: 26
- Target SDK: 34
- Version: 1.0.0 (Code: 1)
- Compile Options: Java 17
- Kotlin JVM Target: 17
- Jetpack Compose: Enabled

## 关键依赖

### UI & Compose
- Jetpack Compose 2024.03.00
- Material3 UI Components
- Compose Navigation

### 数据层
- Room Database 2.6.1
- Retrofit 2.10.0
- OkHttp 4.11.0
- Kotlin Serialization 1.6.2

### 其他
- Hilt 2.50（依赖注入）
- Coroutines 1.7.3
- Coil 2.5.0（图片加载）
- Lifecycle 2.7.0

## 构建步骤

### 步骤 1: 清理构建

```bash
cd /Users/zhangkaixuan/claude_code/OOTD/android
./gradlew clean
```

### 步骤 2: 下载依赖

```bash
./gradlew build --offline false
```
*注意：首次构建会下载所有依赖，可能需要 5-10 分钟*

### 步骤 3: 构建 Debug APK

```bash
./gradlew assembleDebug
```

**输出位置**: `app/build/outputs/apk/debug/app-debug.apk`

### 步骤 4: 构建 Release APK

```bash
./gradlew assembleRelease
```

**输出位置**: `app/build/outputs/apk/release/app-release-unsigned.apk`

### 步骤 5: 签名 Release APK（可选）

```bash
# 创建签名密钥（首次）
keytool -genkey -v -keystore ootd-release-key.jks \
  -keyalg RSA -keysize 2048 -validity 10000 \
  -alias ootd-release-key

# 签名 APK
jarsigner -verbose -sigalg SHA1withRSA -digestalg SHA1 \
  -keystore ootd-release-key.jks \
  app/build/outputs/apk/release/app-release-unsigned.apk \
  ootd-release-key

# 优化 APK（可选）
zipalign -v 4 app/build/outputs/apk/release/app-release-unsigned.apk \
  app/build/outputs/apk/release/app-release.apk
```

## 安装到设备

### 连接真机设备

```bash
# 列出已连接的设备
adb devices

# 安装 Debug APK
adb install app/build/outputs/apk/debug/app-debug.apk

# 启动应用
adb shell am start -n com.ootd.app/.ui.screen.MainActivity
```

### 使用模拟器

```bash
# 列出可用模拟器
emulator -list-avds

# 启动模拟器
emulator -avd <emulator_name>

# 安装 APK
adb install app/build/outputs/apk/debug/app-debug.apk
```

## 测试命令

### 单元测试

```bash
./gradlew testDebugUnitTest
```

### 集成测试

```bash
./gradlew connectedDebugAndroidTest
```

### 静态分析

```bash
./gradlew lint
```

## 构建常见问题

### 问题 1: Java 版本不匹配
```
Error: Invalid Java version. Expected version 17 or later
```

**解决**: 确保 JAVA_HOME 指向 JDK 17+
```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 17)
```

### 问题 2: Android SDK 未找到
```
Error: Unable to locate android SDK or ndk
```

**解决**: 设置 ANDROID_HOME
```bash
export ANDROID_HOME=$HOME/Library/Android/sdk
```

### 问题 3: Gradle 下载失败
```
Failed to download Gradle
```

**解决**: 使用离线模式或代理
```bash
# 使用缓存的 Gradle
./gradlew --offline build

# 或配置 gradle.properties
org.gradle.internal.http.socketTimeout=60000
```

### 问题 4: 内存不足
```
java.lang.OutOfMemoryError: GC overhead limit exceeded
```

**解决**: 增加堆内存
```bash
# 在 gradle.properties 中
org.gradle.jvmargs=-Xmx4096m -XX:MaxPermSize=1024m
```

### 问题 5: Kotlin 编译错误
```
error: unresolved reference
```

**解决**: 清理并重新构建
```bash
./gradlew clean build --refresh-dependencies
```

## 项目结构详解

```
android/
├── app/
│   ├── src/main/kotlin/com/ootd/app/
│   │   ├── data/                    # 数据层
│   │   │   ├── dao/                 # Room DAOs
│   │   │   ├── database/            # Room Database
│   │   │   ├── entity/              # 数据库实体
│   │   │   ├── network/             # 网络层
│   │   │   └── repository/          # 仓库实现
│   │   ├── di/                      # 依赖注入 (Hilt)
│   │   ├── domain/                  # 域层
│   │   │   ├── algorithm/           # 推荐引擎
│   │   │   ├── model/               # 域模型
│   │   │   └── repository/          # 仓库接口
│   │   ├── ui/                      # UI 层
│   │   │   ├── screen/              # Compose 屏幕
│   │   │   ├── theme/               # Material3 主题
│   │   │   └── viewmodel/           # ViewModel
│   │   └── util/                    # 工具类
│   ├── src/main/res/                # 资源文件
│   └── build.gradle.kts
├── build.gradle.kts                 # Root build 配置
├── settings.gradle.kts              # Settings 配置
├── gradle.properties                # Gradle 属性
└── gradle/wrapper/                  # Gradle Wrapper
```

## 关键文件说明

### 数据层
- `entity/` - Room 数据库实体类（User, Clothing, Outfit 等）
- `dao/` - 数据访问对象（CRUD 操作）
- `database/OotdDatabase.kt` - Room 数据库定义
- `network/api/WeatherApi.kt` - 天气 API 接口
- `repository/` - 仓库实现类

### 域层
- `model/` - 域模型和枚举类
- `algorithm/RecommendationEngine.kt` - 穿搭推荐算法
- `repository/` - 仓库接口定义

### UI 层
- `screen/MainActivity.kt` - 应用入口
- `screen/HomeScreen.kt` - 首页（天气 + 推荐）
- `screen/WardrobeScreen.kt` - 衣柜管理
- `screen/AddClothingScreen.kt` - 添加衣服
- `screen/UserScreen.kt` - 用户资料
- `viewmodel/` - ViewModel 状态管理
- `theme/` - Material3 主题配置

### 工具类
- `util/DateUtils.kt` - 日期格式化
- `util/ColorUtils.kt` - 色彩和谐算法
- `util/ImageUtils.kt` - 图片缓存

## 打包并分发

### 生成可供分发的 APK

```bash
# 1. 构建 Release APK
./gradlew assembleRelease

# 2. 复制到输出目录
cp app/build/outputs/apk/release/app-release-unsigned.apk \
   /Users/zhangkaixuan/claude_code/OOTD/output/OOTD.apk

# 3. 验证 APK
aapt dump badging /Users/zhangkaixuan/claude_code/OOTD/output/OOTD.apk
```

## 版本管理

当需要更新版本时，编辑 `gradle.properties`:

```properties
appVersionCode=2          # 增加构建版本号
appVersionName=1.0.1      # 更新版本名称
```

## 性能优化建议

1. **启用 ProGuard/R8**
   - 在 release 构建类型中启用代码混淆
   - 减少 APK 大小

2. **启用 Compose 离线编译**
   ```bash
   ./gradlew assembleRelease -Dorg.gradle.parallel=true
   ```

3. **并行编译**
   - gradle.properties 中已启用：`org.gradle.parallel=true`

4. **构建缓存**
   - gradle.properties 中已启用：`org.gradle.caching=true`

## CI/CD 集成示例

### GitHub Actions

```yaml
name: Build OOTD APK

on: [push, pull_request]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - uses: actions/setup-java@v2
        with:
          java-version: '17'
      - run: ./gradlew assembleRelease
      - uses: actions/upload-artifact@v2
        with:
          name: OOTD.apk
          path: app/build/outputs/apk/release/
```

## 调试技巧

### 启用详细日志
```bash
./gradlew assembleDebug --info --debug
```

### 查看依赖树
```bash
./gradlew app:dependencies
```

### 性能分析
```bash
./gradlew build --profile
# 查看 build/reports/profile 下的报告
```

## 参考资源

- [Jetpack Compose 官方文档](https://developer.android.com/jetpack/compose)
- [Material Design 3](https://m3.material.io/)
- [Android 架构指南](https://developer.android.com/guide/architecture)
- [Room 数据库指南](https://developer.android.com/training/data-storage/room)
- [Gradle 官方文档](https://gradle.org/docs/)

## 支持与反馈

如遇到构建问题，请检查以下内容：
1. Java 版本是否为 17 或更高
2. Android SDK API Level 是否在 26-34 之间
3. Gradle 版本是否兼容（使用项目提供的 Wrapper）
4. 网络连接是否正常（下载依赖时需要）
5. 磁盘空间是否充足（至少需要 2GB）

---

**最后更新**: 2026-04-04
**项目版本**: 1.0.0
**构建工具**: Gradle 8.3.0 + Kotlin 2.0.0
