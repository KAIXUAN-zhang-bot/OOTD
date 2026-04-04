package com.ootd.app.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ootd.app.domain.model.Clothing
import com.ootd.app.ui.viewmodel.WardrobeViewModel

@Composable
fun WardrobeScreen(viewModel: WardrobeViewModel, userId: String) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("我的衣橱", modifier = Modifier.padding(8.dp))

        CategoryFilterRow(
            selectedCategory = uiState.selectedCategory,
            onCategorySelected = { category ->
                viewModel.loadClothings(userId, category)
            }
        )

        if (uiState.loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            ClothingGrid(
                clothings = uiState.clothings,
                onFavoriteToggle = { clothingId, currentState ->
                    viewModel.toggleFavorite(clothingId, currentState)
                },
                onDelete = { clothingId ->
                    viewModel.deleteClothing(clothingId)
                }
            )
        }

        if (uiState.error != null) {
            Text("Error: ${uiState.error}", modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun CategoryFilterRow(
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    val categories = listOf("ALL", "TOP", "BOTTOM", "ONE_PIECE", "SHOES", "ACCESSORIES")

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) { category ->
            Button(
                onClick = { onCategorySelected(category) },
                modifier = Modifier.padding(4.dp)
            ) {
                Text(category)
            }
        }
    }
}

@Composable
fun ClothingGrid(
    clothings: List<Clothing>,
    onFavoriteToggle: (String, Boolean) -> Unit,
    onDelete: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(clothings) { clothing ->
            ClothingCard(
                clothing = clothing,
                onFavoriteToggle = { onFavoriteToggle(clothing.id, clothing.isFavorite) },
                onDelete = { onDelete(clothing.id) }
            )
        }
    }
}

@Composable
fun ClothingCard(
    clothing: Clothing,
    onFavoriteToggle: () -> Unit,
    onDelete: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(clothing.name)
            Text("${clothing.subCategory.displayName}", modifier = Modifier.padding(4.dp))
            Text("${clothing.colors.size}色 | 穿${clothing.wearCount}次")

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Button(onClick = onFavoriteToggle, modifier = Modifier.weight(1f)) {
                    Text(if (clothing.isFavorite) "❤" else "♡")
                }
                Button(onClick = onDelete, modifier = Modifier.weight(1f)) {
                    Text("删除")
                }
            }
        }
    }
}
