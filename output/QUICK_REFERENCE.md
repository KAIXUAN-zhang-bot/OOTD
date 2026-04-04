# OOTD Android APK 构建快速参考

## 目录
1. [5分钟快速开始](#5分钟快速开始)
2. [常见命令](#常见命令)
3. [环境设置](#环境设置)
4. [故障排除](#故障排除)
5. [文件位置](#文件位置)

---

## 5分钟快速开始

### 前置条件
```bash
# 1. 检查 Java
java -version              # 需要 JDK 17+

# 2. 检查 Android SDK
echo $ANDROID_HOME         # 需要设置

# 3. 检查 Gradle
gradle --version           # 或使用项目 ./gradlew
```

### 最快构建方式
```bash
# 使用提供的构建脚本
cd /Users/zhangkaixuan/claude_code/OOTD/output
chmod +x build.sh
./build.sh release         # 构建 Release APK
./build.sh debug           # 构建 Debug APK
```

### 手动构建
```bash
cd /Users/zhangkaixuan/claude_code/OOTD/android

# 方式 1: 使用 Gradle Wrapper (推荐)
./gradlew assembleRelease  # 生成 Release APK
./gradlew assembleDebug    # 生成 Debug APK

# 方式 2: 使用系统 Gradle
gradle assembleRelease

# 方式 3: 使用 Android Studio
# 打开项目后: Build > Build Bundle(s) / APK(s) > Build APK(s)
```

---

## 常见命令

### 构建命令
```bash
cd /Users/zhangkaixuan/claude_code/OOTD/android

# 清理
./gradlew clean

# 检查依赖
./gradlew dependencies

# 编译
./gradlew assembleDebug
./gradlew assembleRelease

# 快速构建 (不生成 APK)
./gradlew build

# 增量构建 (更快)
./gradlew assemble --parallel
```

### 测试命令
```bash
# 单元测试
./gradlew testDebugUnitTest

# 集成测试 (需要连接设备/模拟器)
./gradlew connectedDebugAndroidTest

# 所有测试
./gradlew test
```

### 分析命令
```bash
# 查看依赖树
./gradlew app:dependencies

# 构建报告
./gradlew build --profile
# 查看: build/reports/profile

# Lint 分析
./gradlew lint

# 打印 Gradle 属性
./gradlew properties
```

### 安装和运行
```bash
# 安装 Debug APK 到设备
adb install app/build/outputs/apk/debug/app-debug.apk

# 卸载应用
adb uninstall com.ootd.app

# 启动应用
adb shell am start -n com.ootd.app/.ui.screen.MainActivity

# 查看日志
adb logcat | grep OOTD
```

---

## 环境设置

### macOS 一键配置脚本

```bash
#!/bin/bash

# 1. 安装 JDK 17
echo "安装 JDK 17..."
brew install openjdk@17

# 2. 设置 JAVA_HOME
echo "配置 JAVA_HOME..."
echo 'export JAVA_HOME=$(/usr/libexec/java_home -v 17)' >> ~/.zshrc

# 3. 下载 Android SDK (手动下载)
echo "请从 https://developer.android.com/studio 下载 Command Line Tools"
read -p "按 Enter 继续..."

# 4. 解压 Android SDK
echo "解压 Android SDK..."
mkdir -p ~/Library/Android/sdk
unzip ~/Downloads/commandlinetools-mac-*.zip -d ~/Library/Android/sdk/

# 5. 配置 Android SDK
echo "配置 Android SDK..."
echo 'export ANDROID_HOME=~/Library/Android/sdk' >> ~/.zshrc
echo 'export PATH=$ANDROID_HOME/cmdline-tools/latest/bin:$PATH' >> ~/.zshrc
source ~/.zshrc

# 6. 安装 Android SDK 组件
echo "安装 Android SDK..."
sdkmanager "platforms;android-26" "platforms;android-34"
sdkmanager "build-tools;34.0.0"
sdkmanager "platform-tools"

# 7. 创建 Gradle Wrapper
echo "创建 Gradle Wrapper..."
cd /Users/zhangkaixuan/claude_code/OOTD/android
gradle wrapper --gradle-version=8.3

echo "配置完成！"
```

### 手动配置步骤

#### 1. 安装 JDK 17
```bash
# 使用 Homebrew
brew install openjdk@17

# 验证安装
java -version

# 配置 JAVA_HOME (添加到 ~/.zshrc)
export JAVA_HOME=$(/usr/libexec/java_home -v 17)
```

#### 2. 下载 Android SDK
- 访问: https://developer.android.com/studio
- 下载: Command Line Tools
- 解压到: ~/Library/Android/sdk

#### 3. 配置环境变量 (添加到 ~/.zshrc)
```bash
export ANDROID_HOME=~/Library/Android/sdk
export PATH=$ANDROID_HOME/cmdline-tools/latest/bin:$PATH
export PATH=$ANDROID_HOME/platform-tools:$PATH
export PATH=$ANDROID_HOME/emulator:$PATH
```

#### 4. 安装 SDK 组件
```bash
source ~/.zshrc
sdkmanager "platforms;android-26" "platforms;android-34"
sdkmanager "build-tools;34.0.0"
sdkmanager "platform-tools"
sdkmanager "emulator"
sdkmanager --licenses  # 接受许可
```

#### 5. 创建 Gradle Wrapper
```bash
cd /Users/zhangkaixuan/claude_code/OOTD/android
gradle wrapper --gradle-version=8.3
```

---

## 故障排除

### 错误 1: Command not found: java
```
问题: Java 未安装或未在 PATH 中

解决方案:
1. 安装 JDK 17:
   brew install openjdk@17

2. 配置 JAVA_HOME:
   export JAVA_HOME=$(/usr/libexec/java_home -v 17)
   echo $JAVA_HOME

3. 验证:
   java -version
```

### 错误 2: ANDROID_HOME not set
```
问题: Android SDK 环境变量未配置

解决方案:
1. 检查 Android SDK 位置:
   ls ~/Library/Android/sdk

2. 设置环境变量:
   export ANDROID_HOME=~/Library/Android/sdk
   echo $ANDROID_HOME

3. 安装 SDK 组件:
   sdkmanager "platforms;android-34"
```

### 错误 3: Gradle not found
```
问题: 未找到 Gradle 或 Gradle Wrapper

解决方案:
1. 生成 Gradle Wrapper:
   cd /Users/zhangkaixuan/claude_code/OOTD/android
   gradle wrapper --gradle-version=8.3

2. 验证:
   ./gradlew --version

3. 使用:
   ./gradlew assembleRelease
```

### 错误 4: Out of memory during build
```
问题: 构建过程中内存不足

解决方案:
1. 编辑 gradle.properties:
   org.gradle.jvmargs=-Xmx4096m

2. 或在命令行指定:
   ./gradlew build -Dorg.gradle.jvmargs=-Xmx4096m

3. 清理缓存:
   ./gradlew clean --refresh-dependencies
```

### 错误 5: Build failed: Unresolved reference
```
问题: 编译错误，通常是依赖问题

解决方案:
1. 清理和刷新:
   ./gradlew clean
   ./gradlew build --refresh-dependencies

2. 查看完整错误:
   ./gradlew build --stacktrace

3. 检查网络:
   确保能访问 Maven Central 和 Google()
```

### 错误 6: "Cannot find module org.gradle.kotlin.dsl"
```
问题: Kotlin DSL 不可用

解决方案:
1. 更新 Gradle:
   gradle wrapper --gradle-version=8.3

2. 或在系统中安装新版本:
   brew upgrade gradle
```

### 错误 7: Gradle daemon crashed
```
问题: Gradle 守护进程崩溃

解决方案:
1. 停止所有 Gradle 守护进程:
   ./gradlew --stop

2. 清理 Gradle 缓存:
   rm -rf ~/.gradle/caches
   rm -rf ~/.gradle/daemon

3. 重新构建:
   ./gradlew build --no-daemon
```

### 错误 8: APK not found after build
```
问题: 构建成功但找不到 APK

检查路径:
1. Debug APK:
   app/build/outputs/apk/debug/app-debug.apk

2. Release APK:
   app/build/outputs/apk/release/app-release-unsigned.apk

如果找不到:
1. 查看完整构建输出:
   ./gradlew assembleRelease --info

2. 检查构建目录:
   ls -la app/build/outputs/

3. 重新清理构建:
   ./gradlew clean assembleRelease
```

---

## 文件位置

### 项目结构
```
/Users/zhangkaixuan/claude_code/OOTD/
├── android/                           # Android 项目
│   ├── app/
│   │   ├── src/main/
│   │   │   ├── kotlin/                # Kotlin 源代码
│   │   │   ├── res/                   # 资源文件
│   │   │   └── AndroidManifest.xml
│   │   ├── build/                     # 构建输出
│   │   │   └── outputs/
│   │   │       └── apk/
│   │   │           ├── debug/         # Debug APK
│   │   │           └── release/       # Release APK
│   │   └── build.gradle.kts
│   ├── build.gradle.kts
│   ├── settings.gradle.kts
│   ├── gradle.properties
│   ├── gradlew                        # Gradle Wrapper (脚本)
│   ├── gradlew.bat                    # Windows 版本
│   └── gradle/wrapper/                # Wrapper 配置
│
├── output/                            # 输出目录
│   ├── OOTD.apk                       # 最终 APK
│   ├── BUILD_GUIDE.md                 # 构建指南
│   ├── BUILD_ENVIRONMENT_STATUS.md    # 环境报告
│   ├── QUICK_REFERENCE.md             # 本文档
│   └── build.sh                       # 构建脚本
│
├── DEVELOPMENT.md                     # 开发文档
├── PRD.md                             # 产品需求文档
└── README.md                          # 项目说明
```

### APK 输出位置

#### Debug 版本
```
/Users/zhangkaixuan/claude_code/OOTD/android/app/build/outputs/apk/debug/app-debug.apk
```

#### Release 版本
```
/Users/zhangkaixuan/claude_code/OOTD/android/app/build/outputs/apk/release/app-release-unsigned.apk
```

#### 复制到输出目录
```
/Users/zhangkaixuan/claude_code/OOTD/output/OOTD.apk
```

### 重要文件

| 文件 | 位置 | 说明 |
|------|------|------|
| build.gradle.kts | android/ | Root 构建配置 |
| build.gradle.kts | android/app/ | 应用构建配置 |
| gradle.properties | android/ | Gradle 属性配置 |
| settings.gradle.kts | android/ | 项目设置 |
| gradlew | android/ | Linux/Mac 的 Gradle Wrapper |
| gradlew.bat | android/ | Windows 的 Gradle Wrapper |
| AndroidManifest.xml | android/app/src/main/ | 应用清单 |
| MainActivity.kt | android/app/src/main/kotlin/com/ootd/app/ui/screen/ | 主活动 |

---

## 高级技巧

### 并行构建
```bash
./gradlew build --parallel
```

### 离线构建 (不下载依赖)
```bash
./gradlew build --offline
```

### 后台构建
```bash
./gradlew build &
# 或使用 nohup
nohup ./gradlew build > build.log 2>&1 &
```

### 指定输出目录
```bash
./gradlew build -Dbuild.dir=/custom/build/path
```

### 构建变体
```bash
# 仅构建特定变体
./gradlew assembleDebug
./gradlew assembleRelease

# 构建所有变体
./gradlew assemble
```

### 性能优化
```bash
# gradle.properties 中
org.gradle.parallel=true
org.gradle.caching=true
org.gradle.jvmargs=-Xmx4096m
android.enableJetifier=false  # 如果不需要 Jetifier
```

---

## 版本信息

| 组件 | 版本 |
|------|------|
| Gradle | 8.3.0 |
| Android Gradle Plugin | 8.3.0 |
| Kotlin | 2.0.0 |
| Compose | 1.7.0 |
| Target SDK | 34 |
| Min SDK | 26 |
| Java | 17 |

---

## 支持的构建类型

### Debug
- 用于开发和调试
- 包含所有调试符号
- 未签名或使用调试密钥签名
- 文件较大 (~5-10 MB)

### Release
- 用于发布和分发
- 代码混淆（如启用 ProGuard/R8）
- 未签名（需要手动签名）
- 文件较小 (~2-5 MB)

---

## 安装到设备/模拟器

### 连接真机
```bash
# 启用开发者选项和 USB 调试
# 连接 USB

# 验证连接
adb devices

# 安装 APK
adb install /Users/zhangkaixuan/claude_code/OOTD/output/OOTD.apk

# 启动应用
adb shell am start -n com.ootd.app/.ui.screen.MainActivity
```

### 启动模拟器
```bash
# 列出可用模拟器
emulator -list-avds

# 启动模拟器
emulator -avd <emulator_name>

# 安装 APK
adb install app-debug.apk
```

---

## 清除缓存

```bash
# 清除 Gradle 缓存
rm -rf ~/.gradle/caches

# 清除项目构建
./gradlew clean

# 清除 Gradle daemon
./gradlew --stop

# 完全清除 (谨慎)
./gradlew clean
rm -rf app/build
rm -rf build
```

---

## 获取帮助

```bash
# Gradle 帮助
./gradlew help

# 显示所有任务
./gradlew tasks

# 显示特定任务的信息
./gradlew help --task assembleRelease

# 查看项目属性
./gradlew properties

# 查看依赖信息
./gradlew app:dependencies
```

---

## 快速检查清单

- [ ] Java 17 已安装: `java -version`
- [ ] JAVA_HOME 已设置: `echo $JAVA_HOME`
- [ ] Android SDK 已安装: `ls $ANDROID_HOME`
- [ ] Gradle Wrapper 存在: `ls gradlew`
- [ ] 网络连接正常
- [ ] 磁盘空间充足 (>2GB)
- [ ] 没有运行其他 Gradle 任务

---

## 更多资源

- [Gradle 官方文档](https://gradle.org/docs/)
- [Android Gradle Plugin 文档](https://developer.android.com/studio/build)
- [Kotlin Gradle DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html)
- [OOTD 项目文档](../README.md)

---

**最后更新**: 2026-04-04
**文档版本**: 1.0
