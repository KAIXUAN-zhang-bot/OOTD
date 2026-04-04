#!/bin/bash

################################################################################
# OOTD Android APK 构建脚本
# 用途: 自动化构建 OOTD Android 应用的 APK
# 说明: 该脚本需要已配置的 JDK 17+, Android SDK, 和 Gradle
################################################################################

set -e

# 配置
PROJECT_ROOT="/Users/zhangkaixuan/claude_code/OOTD/android"
OUTPUT_DIR="/Users/zhangkaixuan/claude_code/OOTD/output"
BUILD_TYPE="${1:-release}"  # 默认为 release, 可传入 debug

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# 函数定义
print_header() {
    echo -e "${BLUE}=========================================${NC}"
    echo -e "${BLUE}$1${NC}"
    echo -e "${BLUE}=========================================${NC}"
}

print_step() {
    echo -e "\n${YELLOW}[$1]${NC} $2"
}

print_success() {
    echo -e "${GREEN}✓${NC} $1"
}

print_error() {
    echo -e "${RED}✗${NC} $1"
}

print_info() {
    echo -e "${BLUE}ℹ${NC} $1"
}

# 检查依赖
check_java() {
    print_step "1/6" "检查 Java 环境..."

    if ! command -v java &> /dev/null; then
        print_error "未找到 Java"
        echo "请安装 JDK 17 或更高版本"
        echo "macOS: brew install openjdk@17"
        echo "配置: export JAVA_HOME=\$(/usr/libexec/java_home -v 17)"
        exit 1
    fi

    JAVA_VERSION=$(java -version 2>&1 | grep -oE 'version "[0-9.]+"' | head -1)
    print_success "Java 已安装: $JAVA_VERSION"
}

check_android_sdk() {
    print_step "2/6" "检查 Android SDK..."

    if [ -z "$ANDROID_HOME" ]; then
        print_error "ANDROID_HOME 未设置"
        echo "请配置: export ANDROID_HOME=~/Library/Android/sdk"
        exit 1
    fi

    if [ ! -d "$ANDROID_HOME" ]; then
        print_error "ANDROID_HOME 目录不存在: $ANDROID_HOME"
        exit 1
    fi

    print_success "Android SDK 已配置: $ANDROID_HOME"
}

# 进入项目目录
enter_project() {
    print_step "3/6" "进入项目目录..."

    if [ ! -d "$PROJECT_ROOT" ]; then
        print_error "项目目录不存在: $PROJECT_ROOT"
        exit 1
    fi

    cd "$PROJECT_ROOT"
    print_success "进入目录: $(pwd)"
}

# 清理构建
clean_build() {
    print_step "4/6" "清理旧的构建文件..."

    if [ -f "gradlew" ]; then
        ./gradlew clean --quiet
    else
        if command -v gradle &> /dev/null; then
            gradle clean --quiet
        else
            print_info "首次构建, 跳过清理"
            return
        fi
    fi

    print_success "清理完成"
}

# 执行构建
build_apk() {
    print_step "5/6" "构建 ${BUILD_TYPE} APK..."

    BUILD_COMMAND=""
    if [ "$BUILD_TYPE" = "debug" ]; then
        BUILD_COMMAND="assembleDebug"
    else
        BUILD_COMMAND="assembleRelease"
    fi

    if [ -f "gradlew" ]; then
        print_info "使用 Gradle Wrapper..."
        ./gradlew $BUILD_COMMAND
    else
        if command -v gradle &> /dev/null; then
            print_info "使用系统 Gradle..."
            gradle $BUILD_COMMAND
        else
            print_error "未找到 Gradle 或 Gradle Wrapper"
            echo "请运行: gradle wrapper --gradle-version=8.3"
            exit 1
        fi
    fi

    print_success "构建完成"
}

# 复制 APK
copy_apk() {
    print_step "6/6" "复制 APK 到输出目录..."

    mkdir -p "$OUTPUT_DIR"

    if [ "$BUILD_TYPE" = "debug" ]; then
        SOURCE="app/build/outputs/apk/debug/app-debug.apk"
        DEST="$OUTPUT_DIR/OOTD-debug.apk"
    else
        SOURCE="app/build/outputs/apk/release/app-release-unsigned.apk"
        DEST="$OUTPUT_DIR/OOTD.apk"
    fi

    if [ ! -f "$SOURCE" ]; then
        print_error "APK 文件未找到: $SOURCE"
        exit 1
    fi

    cp "$SOURCE" "$DEST"
    print_success "APK 已复制: $DEST"

    # 显示 APK 信息
    if command -v aapt &> /dev/null; then
        echo -e "\n${BLUE}APK 信息:${NC}"
        aapt dump badging "$DEST" | grep -E "package:|versionName:|versionCode:|application-label:|uses-permission:" | head -10
    fi

    # 显示文件大小
    SIZE=$(du -h "$DEST" | cut -f1)
    print_info "文件大小: $SIZE"
}

# 主程序
main() {
    print_header "OOTD Android APK 构建脚本"
    echo "构建类型: $BUILD_TYPE"
    echo "项目目录: $PROJECT_ROOT"
    echo "输出目录: $OUTPUT_DIR"

    check_java
    check_android_sdk
    enter_project
    clean_build
    build_apk
    copy_apk

    print_header "构建成功"
    print_success "APK 文件位置: $OUTPUT_DIR/OOTD${BUILD_TYPE:+-$BUILD_TYPE}.apk"

    echo -e "\n${GREEN}接下来可以执行:${NC}"
    if [ "$BUILD_TYPE" = "debug" ]; then
        echo "1. adb install $DEST"
        echo "2. adb shell am start -n com.ootd.app/.ui.screen.MainActivity"
    else
        echo "1. 对 APK 进行签名"
        echo "2. 通过 adb 安装: adb install $DEST"
        echo "3. 在 Google Play 或应用市场上架"
    fi
}

# 显示使用说明
if [ "$1" = "-h" ] || [ "$1" = "--help" ]; then
    echo "用法: $0 [构建类型]"
    echo ""
    echo "构建类型:"
    echo "  release  - 构建 Release APK (默认)"
    echo "  debug    - 构建 Debug APK"
    echo ""
    echo "示例:"
    echo "  $0              # 构建 Release APK"
    echo "  $0 debug        # 构建 Debug APK"
    echo "  $0 --help       # 显示此帮助信息"
    exit 0
fi

# 运行主程序
main
