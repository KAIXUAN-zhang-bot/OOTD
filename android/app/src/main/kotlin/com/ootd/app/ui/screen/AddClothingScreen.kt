package com.ootd.app.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ootd.app.domain.model.Color
import com.ootd.app.domain.model.Material
import com.ootd.app.domain.model.Occasion
import com.ootd.app.domain.model.Season
import com.ootd.app.domain.model.Style
import com.ootd.app.domain.model.SubCategory
import com.ootd.app.ui.viewmodel.AddClothingViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddClothingScreen(viewModel: AddClothingViewModel, userId: String) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("添加衣物")

        OutlinedTextField(
            value = uiState.name,
            onValueChange = { viewModel.updateName(it) },
            label = { Text("衣物名称 *") },
            modifier = Modifier.fillMaxWidth()
        )

        SubCategorySelector(
            selected = uiState.subCategory,
            onSelected = { viewModel.updateSubCategory(it) }
        )

        Text("选择颜色 *")
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Color.entries.forEach { color ->
                FilterChip(
                    selected = uiState.selectedColors.contains(color),
                    onClick = { viewModel.toggleColor(color) },
                    label = { Text(color.displayName) }
                )
            }
        }

        Text("材质")
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Material.entries.forEach { material ->
                FilterChip(
                    selected = uiState.material == material,
                    onClick = { viewModel.updateMaterial(material) },
                    label = { Text(material.name) }
                )
            }
        }

        Text("适用季节 *")
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Season.entries.forEach { season ->
                FilterChip(
                    selected = uiState.selectedSeasons.contains(season),
                    onClick = { viewModel.toggleSeason(season) },
                    label = { Text(season.name) }
                )
            }
        }

        Text("适用场合")
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Occasion.entries.forEach { occasion ->
                FilterChip(
                    selected = uiState.selectedOccasions.contains(occasion),
                    onClick = { viewModel.toggleOccasion(occasion) },
                    label = { Text(occasion.name) }
                )
            }
        }

        Text("风格标签")
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Style.entries.forEach { style ->
                FilterChip(
                    selected = uiState.selectedStyles.contains(style),
                    onClick = { viewModel.toggleStyle(style) },
                    label = { Text(style.name) }
                )
            }
        }

        OutlinedTextField(
            value = uiState.brand,
            onValueChange = { viewModel.updateBrand(it) },
            label = { Text("品牌") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = uiState.price,
            onValueChange = { viewModel.updatePrice(it) },
            label = { Text("价格") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = uiState.imageUrl,
            onValueChange = { viewModel.updateImageUrl(it) },
            label = { Text("图片URL") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = uiState.notes,
            onValueChange = { viewModel.updateNotes(it) },
            label = { Text("备注") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )

        if (uiState.error != null) {
            Text("Error: ${uiState.error}")
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { viewModel.saveClothing(userId) },
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            ) {
                if (uiState.loading) {
                    CircularProgressIndicator()
                } else {
                    Text("保存")
                }
            }
        }
    }
}

@Composable
fun SubCategorySelector(
    selected: SubCategory?,
    onSelected: (SubCategory) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text("分类 *")
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            SubCategory.entries.forEach { subCat ->
                FilterChip(
                    selected = selected == subCat,
                    onClick = { onSelected(subCat) },
                    label = { Text(subCat.displayName) }
                )
            }
        }
    }
}
