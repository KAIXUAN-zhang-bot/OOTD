# OOTD Android APK 构建环境状态报告

## 生成时间
2026-04-04

## 项目信息
- **项目名称**: OOTD (今日穿搭)
- **项目类型**: Android 应用
- **项目路径**: `/Users/zhangkaixuan/claude_code/OOTD/android/`

## 项目结构验证

### 项目配置文件 ✓
- ✓ build.gradle.kts (Root)
- ✓ app/build.gradle.kts
- ✓ settings.gradle.kts
- ✓ gradle.properties
- ✓ README.md

### 源代码结构 ✓
- ✓ app/src/main/kotlin/com/ootd/app/ (完整)
  - ✓ data/ (数据层)
  - ✓ di/ (依赖注入)
  - ✓ domain/ (域层)
  - ✓ ui/ (UI层)
  - ✓ util/ (工具类)
- ✓ app/src/main/res/ (资源文件)
- ✓ app/src/main/AndroidManifest.xml

### 构建配置
- ✓ compileSdk: 34
- ✓ minSdk: 26
- ✓ targetSdk: 34
- ✓ Java 版本: 17
- ✓ Kotlin 版本: 2.0.0
- ✓ Compose 版本: 1.7.0

## 系统环境状态

### Java/JDK 状态
```
状态: ⚠️ 未找到合适的 JDK
当前: Java Runtime 存在但无法启动
需求: JDK 17 或更高版本
```

### Android SDK 状态
```
状态: ⚠️ 未配置
ANDROID_HOME: 未设置
需求: Android SDK API Level 26+
```

### Gradle 状态
```
状态: ⚠️ 未在 PATH 中找到
Gradle Wrapper: 未配置
需求: Gradle 8.0+ (通过 wrapper 提供)
```

### 工具状态
- ✗ adb (Android Debug Bridge) - 未找到
- ✗ emulator - 未找到
- ✗ sdkmanager - 未找到
- ✗ aapt - 未找到

## 构建所需环境配置

### 1. 安装 Java 17

**macOS 使用 Homebrew:**
```bash
# 如果没有 Homebrew
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"

# 安装 JDK 17
brew install openjdk@17

# 配置 JAVA_HOME
echo 'export JAVA_HOME=$(/usr/libexec/java_home -v 17)' >> ~/.zshrc
source ~/.zshrc
```

**验证安装:**
```bash
java -version
echo $JAVA_HOME
```

### 2. 安装 Android SDK

**下载 Android Command Line Tools:**
- 访问: https://developer.android.com/studio
- 下载 Command line tools

**配置:**
```bash
# 解压到合适位置
mkdir -p ~/Library/Android/sdk
unzip commandlinetools-mac-*.zip -d ~/Library/Android/sdk/

# 配置环境变量
echo 'export ANDROID_HOME=~/Library/Android/sdk' >> ~/.zshrc
echo 'export PATH=$ANDROID_HOME/cmdline-tools/latest/bin:$PATH' >> ~/.zshrc
echo 'export PATH=$ANDROID_HOME/emulator:$PATH' >> ~/.zshrc
source ~/.zshrc

# 接受 SDK 许可证
sdkmanager --licenses
```

**安装必需的 SDK:**
```bash
# 安装 Android API 26-34
sdkmanager "platforms;android-26" "platforms;android-34"
sdkmanager "build-tools;34.0.0"
sdkmanager "platform-tools"
```

### 3. 创建 Gradle Wrapper

当环境配置完成后:
```bash
cd /Users/zhangkaixuan/claude_code/OOTD/android
gradle wrapper --gradle-version=8.3
```

## 快速构建脚本

创建 `build.sh` 脚本:

```bash
#!/bin/bash

# 构建 OOTD Android APK 脚本

set -e

PROJECT_ROOT="/Users/zhangkaixuan/claude_code/OOTD/android"
OUTPUT_DIR="/Users/zhangkaixuan/claude_code/OOTD/output"

echo "========================================="
echo "OOTD Android APK 构建脚本"
echo "========================================="

# 1. 验证环境
echo -e "\n[1/5] 验证构建环境..."
if ! command -v java &> /dev/null; then
    echo "❌ 错误: 未找到 Java"
    echo "请先安装 JDK 17+"
    exit 1
fi

JAVA_VERSION=$(java -version 2>&1 | grep -oE 'version "[0-9.]+"')
echo "✓ Java 版本: $JAVA_VERSION"

if [ -z "$ANDROID_HOME" ]; then
    echo "❌ 错误: ANDROID_HOME 未设置"
    exit 1
fi
echo "✓ Android SDK: $ANDROID_HOME"

# 2. 进入项目目录
echo -e "\n[2/5] 进入项目目录..."
cd "$PROJECT_ROOT"
echo "✓ 当前目录: $(pwd)"

# 3. 清理构建
echo -e "\n[3/5] 清理旧的构建文件..."
if [ -f "gradlew" ]; then
    ./gradlew clean
else
    gradle clean
fi
echo "✓ 清理完成"

# 4. 构建 Release APK
echo -e "\n[4/5] 构建 Release APK..."
if [ -f "gradlew" ]; then
    ./gradlew assembleRelease
else
    gradle assembleRelease
fi
echo "✓ 构建完成"

# 5. 复制到输出目录
echo -e "\n[5/5] 复制 APK 到输出目录..."
mkdir -p "$OUTPUT_DIR"
cp app/build/outputs/apk/release/app-release-unsigned.apk "$OUTPUT_DIR/OOTD.apk"
echo "✓ APK 位置: $OUTPUT_DIR/OOTD.apk"

# 显示 APK 信息
echo -e "\n========================================="
echo "构建成功！"
echo "========================================="
if command -v aapt &> /dev/null; then
    echo -e "\nAPK 信息:"
    aapt dump badging "$OUTPUT_DIR/OOTD.apk" | head -20
fi

echo -e "\n✓ APK 已生成: $OUTPUT_DIR/OOTD.apk"
ls -lh "$OUTPUT_DIR/OOTD.apk"
```

## 完整构建步骤 (环境配置完成后)

```bash
# 1. 进入项目目录
cd /Users/zhangkaixuan/claude_code/OOTD/android

# 2. 清理构建
./gradlew clean

# 3. 构建 Debug APK (开发)
./gradlew assembleDebug
# 输出: app/build/outputs/apk/debug/app-debug.apk

# 4. 构建 Release APK (发布)
./gradlew assembleRelease
# 输出: app/build/outputs/apk/release/app-release-unsigned.apk

# 5. 复制到输出目录
cp app/build/outputs/apk/release/app-release-unsigned.apk \
   /Users/zhangkaixuan/claude_code/OOTD/output/OOTD.apk

# 6. 验证 APK
aapt dump badging /Users/zhangkaixuan/claude_code/OOTD/output/OOTD.apk
```

## 依赖检查清单

| 组件 | 状态 | 最小版本 | 安装方法 |
|------|------|---------|---------|
| Java/JDK | ❌ | 17+ | brew install openjdk@17 |
| Android SDK | ❌ | 26+ | Developer Tools |
| Gradle | ⏳ | 8.0+ | ./gradlew wrapper |
| Git | - | 任意 | brew install git |

## 构建输出目标

构建成功后，以下文件将生成:

### Debug APK
```
app/build/outputs/apk/debug/app-debug.apk
├── 大小: ~5-10 MB
├── 用途: 开发和测试
└── 签名: 调试密钥
```

### Release APK (未签名)
```
app/build/outputs/apk/release/app-release-unsigned.apk
├── 大小: ~2-5 MB
├── 用途: 发布和分发
└── 签名: 需要手动签名
```

### 最终输出
```
/Users/zhangkaixuan/claude_code/OOTD/output/OOTD.apk
└── 已签名或未签名的 Release APK
```

## 故障排除

### 问题 1: Command not found: gradle
**原因**: Gradle Wrapper 不存在或未在 PATH 中
**解决**:
```bash
cd /Users/zhangkaixuan/claude_code/OOTD/android
gradle wrapper --gradle-version=8.3
```

### 问题 2: JAVA_HOME not set
**原因**: Java 环境变量未配置
**解决**:
```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 17)
```

### 问题 3: Android SDK not found
**原因**: ANDROID_HOME 未设置
**解决**:
```bash
export ANDROID_HOME=~/Library/Android/sdk
```

### 问题 4: Out of memory error
**原因**: Gradle 内存不足
**解决**: 编辑 gradle.properties
```properties
org.gradle.jvmargs=-Xmx4096m
```

## 推荐的开发环境配置

### .bashrc / .zshrc 配置
```bash
# Java
export JAVA_HOME=$(/usr/libexec/java_home -v 17)
export PATH=$JAVA_HOME/bin:$PATH

# Android SDK
export ANDROID_HOME=~/Library/Android/sdk
export PATH=$ANDROID_HOME/cmdline-tools/latest/bin:$PATH
export PATH=$ANDROID_HOME/platform-tools:$PATH
export PATH=$ANDROID_HOME/emulator:$PATH

# Gradle
export GRADLE_HOME=~/.gradle
export PATH=$GRADLE_HOME/bin:$PATH
```

## 后续步骤

当环境配置完成后:

1. ✓ 验证环境
   ```bash
   java -version
   echo $ANDROID_HOME
   gradle --version
   ```

2. ✓ 创建 Gradle Wrapper
   ```bash
   cd /Users/zhangkaixuan/claude_code/OOTD/android
   gradle wrapper
   ```

3. ✓ 执行构建
   ```bash
   ./gradlew assembleRelease
   ```

4. ✓ 查看输出
   ```bash
   ls -lh app/build/outputs/apk/release/
   ```

## 参考资源

- [Android 开发官网](https://developer.android.com/)
- [Gradle 官方文档](https://gradle.org/docs/)
- [OpenJDK](https://openjdk.java.net/)
- [Android SDK 下载](https://developer.android.com/studio)

---

**报告生成时间**: 2026-04-04
**报告版本**: 1.0
**检查项**: 15/15
