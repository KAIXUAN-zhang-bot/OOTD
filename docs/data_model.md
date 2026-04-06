# OOTD App 数据建模方案 (Data Model Design)

## 1. 核心数据模型 (Core Data Models)

### 1.1 衣物项 (ClothingItem)
| 字段 | 类型 | 说明 |
| :--- | :--- | :--- |
| `id` | `String (UUID)` | 唯一标识符 |
| `category` | `Enum` | 大类 (Top, Bottom, Dress, Outerwear, Shoes, Accessory) |
| `subcategory` | `String` | 子类 (T-shirt, Jeans, Trench Coat, etc.) |
| `seasons` | `List<Enum>` | 适用季节 (Spring, Summer, Autumn, Winter) |
| `colors` | `List<String>` | 颜色 (Hex Code 或标准名) |
| `fabric` | `Enum` | 面料 (Cotton, Linen, Silk, Wool, Polyester, Leather) |
| `formality` | `Integer (1-10)` | 正式程度 (1: 极度休闲, 10: 商务正装) |
| `warmth_index` | `Integer (1-10)` | 保暖指数 (1: 极薄, 10: 极厚) |
| `style_tags` | `List<String>` | 风格标签 (Minimalist, Vintage, Streetwear, Elegant) |
| `image_uri` | `String` | 本地存储路径或云端 URL |
| `last_worn` | `DateTime` | 上次穿着时间 |

### 1.2 用户偏好 (UserPreference)
| 字段 | 类型 | 说明 |
| :--- | :--- | :--- |
| `preferred_styles` | `List<String>` | 常用风格标签权重 |
| `disliked_colors` | `List<String>` | 避雷颜色 |
| `body_type` | `Enum` | 身材类型 (Hourglass, Pear, Rectangle, etc.) |
| `color_palette` | `Enum` | 个人色彩体系 (Spring, Summer, Autumn, Winter Palette) |

---

## 2. 推荐算法度量模型 (Recommendation Scoring Logic)

### 2.1 综合评分公式 (Total Score)
$$Score_{total} = w_w \cdot S_{weather} + w_o \cdot S_{occasion} + w_m \cdot S_{mood} + w_h \cdot S_{history}$$

*   $w_w, w_o, w_m, w_h$: 权重因子（可根据用户手动反馈动态调整）。
*   $S_{weather}$: 天气匹配度。
*   $S_{occasion}$: 场合匹配度。
*   $S_{mood}$: 心情匹配度。
*   $S_{history}$: 历史平衡分数（避免频繁重复穿着）。

### 2.2 子项计算逻辑 (Sub-Logic)
1.  **天气匹配 ($S_{weather}$)**: 
    *   根据实时温度 $T$ 筛选 $warmth\_index$。
    *   下雨/雪天降低 $fabric \in \{Silk, Suede\}$ 的得分。
2.  **场合匹配 ($S_{occasion}$)**:
    *   `Casual`: $formality \in [1, 4]$
    *   `Office`: $formality \in [5, 8]$
    *   `Party`: $formality \in [3, 7]$ + 特定风格标签
3.  **心情匹配 ($S_{mood}$)**:
    *   `Happy/Vibrant`: 增加高饱和度颜色权重。
    *   `Low-key`: 增加中性色 ($Neutral$) 权重。

---

## 3. 资源配比建议
*   **存储**: 本地 SQLite (Room) 存储元数据，相册图片采用 URI 引用以减少内存占用。
*   **计算**: 推荐引擎在移动端离线运行，确保响应速度。
