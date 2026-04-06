package com.ootd.app.data.mapper

import com.ootd.app.data.local.ClothingEntity
import com.ootd.app.domain.model.WardrobeItem

fun ClothingEntity.toWardrobeItem(): WardrobeItem {
    return WardrobeItem(
        id = id,
        category = category,
        subcategory = subcategory,
        seasons = seasons,
        colors = colors,
        fabric = fabric,
        formality = formality,
        warmthIndex = warmthIndex,
        styleTags = styleTags,
        imageUri = imageUri,
        lastWorn = lastWorn
    )
}

fun WardrobeItem.toClothingEntity(): ClothingEntity {
    return ClothingEntity(
        id = id,
        category = category,
        subcategory = subcategory,
        seasons = seasons,
        colors = colors,
        fabric = fabric,
        formality = formality,
        warmthIndex = warmthIndex,
        styleTags = styleTags,
        imageUri = imageUri,
        lastWorn = lastWorn
    )
}
