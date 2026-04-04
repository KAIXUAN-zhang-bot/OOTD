# OOTD Android APK 构建输出文档

## 概述

本目录包含 OOTD Android 应用的完整构建指导文档和构建脚本。

**项目**: OOTD (今日穿搭) - AI 驱动的穿搭推荐应用
**构建日期**: 2026-04-04
**构建工具**: Gradle 8.3.0 + Kotlin 2.0.0

## 📂 文件说明

### 构建文档

| 文件 | 说明 | 推荐阅读人群 |
|------|------|-----------|
| **BUILD_GUIDE.md** | 详细的构建指南，包含完整的构建步骤、常见问题和解决方案 | 所有开发者 |
| **BUILD_ENVIRONMENT_STATUS.md** | 当前构建环境的详细检查报告，包含环境配置要求 | 环境配置人员 |
| **QUICK_REFERENCE.md** | 快速参考手册，包含常用命令和快速技巧 | 快速查询 |
| **README.md** | 本文件 | - |

### 构建脚本

| 文件 | 说明 | 执行方式 |
|------|------|---------|
| **build.sh** | 自动化构建脚本，自动检查环境并执行构建 | `./build.sh [release\|debug]` |

### 输出文件 (构建后生成)

| 文件 | 说明 | 生成方式 |
|------|------|---------|
| **OOTD.apk** | 最终的 Release APK 可执行文件 | 运行构建脚本后生成 |
| **OOTD-debug.apk** | Debug 版本 APK | 使用 `./build.sh debug` 生成 |

---

## 🚀 快速开始

### 方式 1: 使用自动化脚本 (最简单)

```bash
# 进入输出目录
cd /Users/zhangkaixuan/claude_code/OOTD/output

# 使脚本可执行
chmod +x build.sh

# 构建 Release APK
./build.sh release

# 或构建 Debug APK
./build.sh debug
```

### 方式 2: 手动构建

```bash
# 进入 Android 项目目录
cd /Users/zhangkaixuan/claude_code/OOTD/android

# 清理旧的构建
./gradlew clean

# 构建 Release APK
./gradlew assembleRelease

# APK 将生成在:
# app/build/outputs/apk/release/app-release-unsigned.apk

# 复制到输出目录
cp app/build/outputs/apk/release/app-release-unsigned.apk \
   /Users/zhangkaixuan/claude_code/OOTD/output/OOTD.apk
```

### 方式 3: 使用 Android Studio

1. 在 Android Studio 中打开项目: `/Users/zhangkaixuan/claude_code/OOTD/android`
2. 菜单: Build > Build Bundle(s) / APK(s) > Build APK(s)
3. APK 将生成在 `app/build/outputs/apk/`

---

## 📋 前置条件

在构建前，请确保系统已安装以下工具:

### 必需
- **JDK 17+** - Java Development Kit
- **Android SDK** - API Level 26-34
- **Gradle** - 8.0+ (通过 wrapper 提供)

### 可选
- **Android Studio** - 官方 IDE
- **ADB** - Android Debug Bridge (用于安装到设备)
- **emulator** - Android 模拟器

### 检查环境
```bash
java -version              # 需要 version 17+
echo $JAVA_HOME            # 应该输出 JDK 路径
echo $ANDROID_HOME         # 应该输出 Android SDK 路径
./gradlew --version        # 应该显示 Gradle 8.3+
```

---

## 📚 文档导航

### 初次使用?
推荐按以下顺序阅读:
1. 本 README.md (5分钟)
2. QUICK_REFERENCE.md (快速查询)
3. BUILD_GUIDE.md (详细学习)

### 需要配置环境?
阅读: BUILD_ENVIRONMENT_STATUS.md
- 包含完整的环境配置步骤
- 包含遇到的常见问题和解决方案

### 需要快速构建?
- 使用 `./build.sh` 脚本
- 或查看 QUICK_REFERENCE.md 中的常用命令

### 遇到问题?
- 查看 BUILD_GUIDE.md 的 "构建常见问题" 部分
- 查看 BUILD_ENVIRONMENT_STATUS.md 的 "故障排除" 部分

---

## 🏗️ 构建过程

### 完整构建流程 (5-15 分钟，取决于网络和硬件)

1. **环境验证** (10 秒)
   - 检查 Java 版本
   - 检查 Android SDK
   - 检查 Gradle

2. **依赖下载** (1-5 分钟)
   - 首次构建会下载所有依赖
   - 包括 Gradle、SDK 库、第三方库

3. **代码编译** (2-5 分钟)
   - Kotlin 源代码编译
   - 资源处理
   - DEX 编译

4. **APK 打包** (1-3 分钟)
   - 打包资源、代码和配置
   - 签名 (Debug 或 Release)
   - 优化和验证

5. **输出** (10 秒)
   - 生成 APK 文件
   - 复制到输出目录

### 构建产物

#### Debug APK
- 位置: `app/build/outputs/apk/debug/app-debug.apk`
- 大小: ~5-10 MB
- 用途: 开发、测试、调试
- 签名: 调试密钥

#### Release APK
- 位置: `app/build/outputs/apk/release/app-release-unsigned.apk`
- 大小: ~2-5 MB
- 用途: 发布、分发、上架
- 签名: 未签名 (需要手动签名)

---

## 🔧 常用命令速查

```bash
cd /Users/zhangkaixuan/claude_code/OOTD/android

# 清理
./gradlew clean

# 构建 Debug APK
./gradlew assembleDebug

# 构建 Release APK
./gradlew assembleRelease

# 运行测试
./gradlew test

# 检查依赖
./gradlew dependencies

# 启用详细日志
./gradlew build --info

# 并行构建 (更快)
./gradlew build --parallel

# 停止 Gradle daemon
./gradlew --stop
```

---

## 📦 安装 APK

### 到真机设备
```bash
# 启用 USB 调试后连接设备
adb devices                    # 列出设备

# 安装 APK
adb install OOTD.apk          # 或完整路径

# 启动应用
adb shell am start -n com.ootd.app/.ui.screen.MainActivity

# 查看日志
adb logcat | grep OOTD
```

### 到模拟器
```bash
# 启动模拟器
emulator -avd <emulator_name>

# 安装
adb install OOTD.apk

# 启动
adb shell am start -n com.ootd.app/.ui.screen.MainActivity
```

---

## 📊 项目信息

### 应用信息
- **应用名称**: OOTD (今日穿搭)
- **包名**: com.ootd.app
- **版本**: 1.0.0
- **版本代码**: 1

### SDK 版本
- **Min SDK**: 26 (Android 8.0)
- **Target SDK**: 34 (Android 14)
- **Compile SDK**: 34

### 技术栈
- **编程语言**: Kotlin 2.0.0
- **UI 框架**: Jetpack Compose 1.7.0
- **UI 组件**: Material3
- **数据库**: Room 2.6.1
- **网络**: Retrofit 2.10.0 + OkHttp 4.11.0
- **依赖注入**: Hilt 2.50
- **异步**: Coroutines 1.7.3

### 关键功能
- 衣柜管理 (添加、分类、搜索衣物)
- 智能推荐 (基于天气、场景、个人偏好)
- 穿搭管理 (创建、编辑、收藏穿搭)
- 用户资料 (个人信息、风格偏好)

---

## 🐛 故障排除

### 常见问题

**Q: "Command not found: java"**
- A: 安装 JDK 17: `brew install openjdk@17`

**Q: "ANDROID_HOME not set"**
- A: 设置环境变量: `export ANDROID_HOME=~/Library/Android/sdk`

**Q: "Build failed: out of memory"**
- A: 增加堆内存: 编辑 gradle.properties, 设置 `org.gradle.jvmargs=-Xmx4096m`

**Q: "Gradle not found"**
- A: 创建 Gradle Wrapper: `gradle wrapper --gradle-version=8.3`

**Q: 构建很慢**
- A: 启用并行构建: `./gradlew build --parallel`

更多问题见各文档的 "故障排除" 或 "常见问题" 部分。

---

## 📖 项目目录结构

```
/Users/zhangkaixuan/claude_code/OOTD/
├── android/                    # Android 项目 (构建源)
│   ├── app/
│   │   ├── src/main/
│   │   │   ├── kotlin/         # Kotlin 源代码
│   │   │   ├── res/            # 资源文件
│   │   │   └── AndroidManifest.xml
│   │   └── build.gradle.kts
│   ├── build.gradle.kts
│   ├── settings.gradle.kts
│   ├── gradle.properties
│   ├── gradlew                 # Gradle Wrapper 脚本
│   └── README.md
│
├── output/                     # 本目录 (构建输出)
│   ├── BUILD_GUIDE.md          # 详细构建指南
│   ├── BUILD_ENVIRONMENT_STATUS.md  # 环境报告
│   ├── QUICK_REFERENCE.md      # 快速参考
│   ├── README.md               # 本文件
│   ├── build.sh                # 构建脚本
│   └── OOTD.apk                # 最终 APK (构建后)
│
├── DEVELOPMENT.md              # 开发文档
├── IMPLEMENTATION_SUMMARY.md   # 实现总结
├── PRD.md                      # 产品需求文档
├── PROJECT_SUMMARY.txt         # 项目总结
├── QUICKSTART.md               # 快速开始
└── INDEX.md                    # 索引
```

---

## 🎯 下一步

### 完成构建后

1. **验证 APK**
   ```bash
   aapt dump badging OOTD.apk   # 查看 APK 信息
   ```

2. **安装到设备**
   ```bash
   adb install OOTD.apk
   adb shell am start -n com.ootd.app/.ui.screen.MainActivity
   ```

3. **签名 Release APK** (如需上架)
   ```bash
   keytool -genkey -v -keystore ootd-key.jks -keyalg RSA \
     -keysize 2048 -validity 10000 -alias ootd
   jarsigner -sigalg SHA1withRSA -digestalg SHA1 \
     -keystore ootd-key.jks OOTD.apk ootd
   ```

4. **上架应用市场**
   - Google Play Store
   - 华为应用市场
   - 小米应用市场
   - 等其他应用市场

---

## 📞 获取帮助

### 文档资源
- Android 开发: https://developer.android.com/
- Gradle: https://gradle.org/
- Kotlin: https://kotlinlang.org/
- Jetpack Compose: https://developer.android.com/jetpack/compose

### 项目文档
- 产品需求: [PRD.md](../PRD.md)
- 开发指南: [DEVELOPMENT.md](../DEVELOPMENT.md)
- 项目总结: [PROJECT_SUMMARY.txt](../PROJECT_SUMMARY.txt)

---

## ✅ 质量检查清单

使用本清单确保构建质量:

- [ ] Java 版本 >= 17
- [ ] Android SDK 已安装
- [ ] Gradle 版本 >= 8.0
- [ ] 网络连接正常
- [ ] 磁盘空间 > 2GB
- [ ] 无其他 Gradle 进程运行
- [ ] 所有依赖已下载
- [ ] 编译无错误
- [ ] APK 文件已生成
- [ ] APK 文件大小正常 (2-10 MB)

---

## 🔄 版本历史

| 版本 | 日期 | 说明 |
|------|------|------|
| 1.0.0 | 2026-04-04 | 初始版本，包含完整的构建指南和脚本 |

---

## 📝 许可证

Proprietary - OOTD Team 2026

---

**文档生成时间**: 2026-04-04
**项目版本**: 1.0.0
**维护者**: QA Test Engineer

如有任何问题或改进建议，请联系项目团队。
