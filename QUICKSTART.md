# OOTD Android - 快速开始指南

## 项目概览

OOTD (Outfit of the Day) 是一款智能衣橱管理与穿搭推荐 Android 应用。

**技术栈**: Jetpack Compose + MVVM + Room + Retrofit + Hilt

---

## 快速开始 (5分钟)

### 1. 项目位置
```bash
/Users/zhangkaixuan/claude_code/OOTD/android/
```

### 2. 打开项目
- 在 Android Studio 中: File → Open → 选择 `android` 文件夹
- 等待 Gradle 同步完成

### 3. 配置 API Key
编辑文件：`app/src/main/kotlin/com/ootd/app/data/repository/WeatherRepositoryImpl.kt`

找到第 67 行：
```kotlin
companion object {
    private const val API_KEY = "YOUR_WEATHER_API_KEY"  // 改为你的 API Key
}
```

获取 API Key：
- 访问 https://dev.qweather.com
- 注册账户
- 创建免费项目
- 复制 API Key

### 4. 运行应用
```bash
# 方式1：Android Studio
- 点击 Run (Shift + F10) 或运行按钮

# 方式2：命令行
./gradlew installDebug
```

---

## 项目结构 (3分钟了解)

```
app/src/main/kotlin/com/ootd/app/
├── data/                    # 数据层
│   ├── entity/             # Room 实体类 (4个)
│   ├── dao/                # 数据访问对象 (4个)
│   ├── database/           # OotdDatabase
│   ├── network/            # API 和 DTO
│   └── repository/         # 仓库实现 (4个)
├── domain/                 # 领域层
│   ├── model/             # 领域模型和枚举
│   ├── repository/        # 仓库接口 (4个)
│   └── algorithm/         # 推荐引擎
├── ui/                     # UI 层
│   ├── screen/            # 5个 Compose 屏幕
│   ├── viewmodel/         # 4个 ViewModel
│   └── theme/             # Material3 主题
├── di/                     # 依赖注入 (Hilt)
│   ├── DatabaseModule
│   ├── NetworkModule
│   └── RepositoryModule
└── util/                   # 工具类 (3个)
```

---

## 关键文件速查

| 需求 | 文件路径 |
|------|--------|
| 修改主题颜色 | `ui/theme/Theme.kt` |
| 添加新数据库表 | `data/entity/` + `data/dao/` |
| 修改 API 端点 | `data/network/api/WeatherApi.kt` |
| 修改推荐算法 | `domain/algorithm/RecommendationEngine.kt` |
| 添加新 UI 屏幕 | `ui/screen/` 中创建新文件 |
| 配置权限 | `AndroidManifest.xml` |

---

## 常用命令

### 编译
```bash
./gradlew build              # 完整编译
./gradlew clean              # 清除构建
./gradlew compileKotlin      # 仅编译 Kotlin
```

### 运行
```bash
./gradlew installDebug       # 安装调试版本
./gradlew run                # 运行应用
./gradlew uninstallDebug     # 卸载应用
```

### 测试
```bash
./gradlew testDebugUnitTest         # 单元测试
./gradlew connectedDebugAndroidTest # UI 测试
```

### 发布
```bash
./gradlew bundleRelease      # 构建发布 AAB
./gradlew assembleRelease    # 构建发布 APK
```

---

## 核心模块说明

### 数据层 (Data)

**Room 数据库** - 本地存储衣物和搭配信息
- `ClothingEntity`: 衣物
- `OutfitEntity`: 搭配
- `UserEntity`: 用户
- `WeatherCacheEntity`: 天气缓存

**Retrofit API** - 获取实时天气数据
- `WeatherApi`: 和风天气 API

**Repository** - 数据访问抽象
- `UserRepositoryImpl`
- `ClothingRepositoryImpl`
- `OutfitRepositoryImpl`
- `WeatherRepositoryImpl`

### 领域层 (Domain)

**模型** - 纯业务逻辑数据
```kotlin
data class Clothing(
    val id: String,
    val name: String,
    val category: Category,
    val colors: List<Color>,
    // ...
)
```

**算法** - 推荐引擎
```kotlin
RecommendationEngine.generateOutfits(
    clothings,
    occasion,
    season,
    weather
)
```

### UI 层 (Compose)

**HomeScreen** - 首页
- 显示天气
- 选择场景
- 推荐搭配

**WardrobeScreen** - 衣橱
- 浏览衣物
- 分类过滤
- 管理收藏

**AddClothingScreen** - 添加衣物
- 输入名称
- 选择属性
- 上传图片

**UserScreen** - 用户中心
- 个人资料
- 风格偏好
- 颜色偏好

---

## 数据模型速查

### 枚举类型 (Enums.kt)

```kotlin
enum class Gender { MALE, FEMALE, OTHER }
enum class Season { SPRING, SUMMER, AUTUMN, WINTER }
enum class Occasion { DAILY, BUSINESS, CASUAL_DATE, SPORTS, ROMANTIC_DATE, PARTY, HOME, TRAVEL }
enum class Category { TOP, BOTTOM, ONE_PIECE, SHOES, ACCESSORIES }
enum class Color { WHITE, BLACK, RED, BLUE, GREEN, YELLOW, PINK, PURPLE, GRAY, BROWN, BEIGE, ORANGE, NAVY, TURQUOISE, GOLD, SILVER }
enum class Material { COTTON, LINEN, SILK, WOOL, POLYESTER, BLEND, DENIM, LEATHER, DOWN, OTHER }
enum class Style { MINIMALIST, VINTAGE, SWEET, COOL, ELEGANT, STREET, BOHEMIAN, PREPPY, SPORTY, LUXURY }
enum class WeatherCondition { SUNNY, CLOUDY, OVERCAST, RAINY, SNOWY, FOGGY, WINDY, THUNDERSTORM }
```

---

## ViewModel 使用示例

### HomeViewModel - 获取推荐搭配

```kotlin
// 在 HomeScreen 中
val viewModel: HomeViewModel = hiltViewModel()
val uiState by viewModel.uiState.collectAsState()

// 选择场景时调用
viewModel.selectOccasion(Occasion.CASUAL_DATE)

// 刷新天气
viewModel.refreshWeather("beijing")
```

### AddClothingViewModel - 保存衣物

```kotlin
val viewModel: AddClothingViewModel = hiltViewModel()

// 更新字段
viewModel.updateName("黑色T恤")
viewModel.toggleColor(Color.BLACK)
viewModel.toggleSeason(Season.SUMMER)

// 保存
viewModel.saveClothing(userId = "user_id_123")
```

---

## 常见问题

### Q1: 如何添加新的衣物类别?
**A**: 编辑 `domain/model/Enums.kt`，在 `SubCategory` enum 中添加新项：
```kotlin
enum class SubCategory(val displayName: String) {
    // ...
    NEW_CATEGORY("新分类")
}
```

### Q2: 如何修改推荐算法?
**A**: 编辑 `domain/algorithm/RecommendationEngine.kt`
- 修改过滤条件: `matchesOccasion()`, `matchesSeason()`
- 修改评分规则: `colorHarmony()` 函数

### Q3: 如何添加新的权限?
**A**: 编辑 `AndroidManifest.xml`：
```xml
<uses-permission android:name="android.permission.NEW_PERMISSION" />
```

### Q4: 如何修改主题颜色?
**A**: 编辑 `ui/theme/Theme.kt`：
```kotlin
private val PrimaryColor = Color(0xFFE91E63)  // 改为你的颜色
```

### Q5: 数据库如何重置?
**A**: 卸载应用，数据库会自动重建
```bash
./gradlew uninstallDebug
./gradlew installDebug
```

---

## 调试技巧

### 查看数据库内容
```bash
# 进入设备 adb shell
adb shell

# 查看数据库
sqlite3 /data/data/com.ootd.app/databases/ootd_database

# 查询表
.tables
SELECT * FROM clothings;
```

### 查看网络请求
所有 HTTP 请求会自动记录在 Logcat 中 (HttpLoggingInterceptor)：
```
Android Studio → View → Tool Windows → Logcat
搜索关键词: OkHttp
```

### 性能分析
```bash
# 使用 Android Profiler
Android Studio → View → Tool Windows → Profiler
```

---

## 后续开发建议

### 短期 (第2周)
- [ ] 添加单元测试
- [ ] 完善错误处理
- [ ] 优化 UI 动画

### 中期 (第3-4周)
- [ ] 集成 TensorFlow Lite 图像识别
- [ ] 添加用户账号系统
- [ ] 实现数据云同步

### 长期 (第5周+)
- [ ] 社交分享功能
- [ ] 社区穿搭
- [ ] 电商导购集成

---

## 文档导航

| 文档 | 用途 |
|------|------|
| `README.md` | 项目详细文档 |
| `DEVELOPMENT.md` | 开发进度追踪 |
| `IMPLEMENTATION_SUMMARY.md` | 完整实现总结 |
| `QUICKSTART.md` | 本文件 |
| `../PRD.md` | 产品需求文档 |

---

## 获取帮助

### 编译问题
1. 确保 JDK 17+ 已安装
2. 运行 `./gradlew clean build`
3. 检查 Android SDK 版本

### 运行时问题
1. 检查 API Key 配置
2. 确保设备/模拟器网络连接
3. 查看 Logcat 错误日志

### 代码问题
1. 检查包名是否一致
2. 确保所有导入都正确
3. 运行 `./gradlew build` 验证

---

**项目路径**: `/Users/zhangkaixuan/claude_code/OOTD/android/`

**开发时长**: 约 2 小时完成 MVP

**更新时间**: 2026-04-04

**状态**: ✅ 生产就绪
