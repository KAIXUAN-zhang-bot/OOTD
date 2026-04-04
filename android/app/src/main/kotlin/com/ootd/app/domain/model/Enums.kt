package com.ootd.app.domain.model

enum class Gender {
    MALE, FEMALE, OTHER
}

enum class Season {
    SPRING, SUMMER, AUTUMN, WINTER
}

enum class Occasion {
    DAILY, BUSINESS, CASUAL_DATE, SPORTS,
    ROMANTIC_DATE, PARTY, HOME, TRAVEL
}

enum class Style {
    MINIMALIST, VINTAGE, SWEET, COOL, ELEGANT,
    STREET, BOHEMIAN, PREPPY, SPORTY, LUXURY
}

enum class WeatherCondition {
    SUNNY, CLOUDY, OVERCAST, RAINY, SNOWY,
    FOGGY, WINDY, THUNDERSTORM
}

enum class Material {
    COTTON, LINEN, SILK, WOOL, POLYESTER,
    BLEND, DENIM, LEATHER, DOWN, OTHER
}

enum class Category {
    TOP, BOTTOM, ONE_PIECE, SHOES, ACCESSORIES
}

enum class SubCategory(val displayName: String) {
    // Top
    T_SHIRT("T恤/背心"),
    SHIRT("衬衫"),
    SWEATER("卫衣/毛衣"),
    JACKET("夹克/外套"),

    // Bottom
    LONG_PANTS("长裤"),
    SHORT_PANTS("短裤"),
    SKIRT("裙子"),

    // One Piece
    DRESS("连衣裙"),
    JUMPSUIT("连体裤"),

    // Shoes
    SNEAKERS("运动鞋"),
    CASUAL_SHOES("休闲鞋"),
    FORMAL_SHOES("正装鞋"),
    BOOTS("靴子"),

    // Accessories
    BAG("包袋"),
    HAT("帽子"),
    SCARF("围巾"),
    JEWELRY("首饰")
}

enum class Color(val displayName: String, val hexCode: String) {
    WHITE("白色", "#FFFFFF"),
    BLACK("黑色", "#000000"),
    RED("红色", "#FF0000"),
    BLUE("蓝色", "#0000FF"),
    GREEN("绿色", "#008000"),
    YELLOW("黄色", "#FFFF00"),
    PINK("粉红色", "#FFC0CB"),
    PURPLE("紫色", "#800080"),
    GRAY("灰色", "#808080"),
    BROWN("棕色", "#A52A2A"),
    BEIGE("米色", "#F5F5DC"),
    ORANGE("橙色", "#FFA500"),
    NAVY("海军蓝", "#000080"),
    TURQUOISE("青松石绿", "#40E0D0"),
    GOLD("金色", "#FFD700"),
    SILVER("银色", "#C0C0C0")
}

enum class ColorHarmony(val weight: Float) {
    COMPLEMENTARY(1.0f),
    ANALOGOUS(0.8f),
    MONOCHROMATIC(0.7f),
    TRIADIC(0.9f),
    NEUTRAL_BASE(0.6f)
}
