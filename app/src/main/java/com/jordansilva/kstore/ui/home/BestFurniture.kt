package com.jordansilva.kstore.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jordansilva.kstore.R
import com.jordansilva.kstore.ui.compose.LazyGrid
import com.jordansilva.kstore.ui.compose.LazyGridCells
import com.jordansilva.kstore.ui.product.ProductGridItem
import com.jordansilva.kstore.ui.product.ProductViewData
import com.jordansilva.kstore.util.PreviewData

@Composable
fun FurnitureHeader() {
    Column {
        Text(text = stringResource(R.string.best_furniture), fontSize = 26.sp, fontWeight = FontWeight.Bold)
        Text(text = stringResource(R.string.perfect_choice), fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FurnitureList(products: List<ProductViewData>, header: @Composable () -> Unit = {}) {
    LazyGrid(
        cells = LazyGridCells.Adaptive(minSize = 150.dp),
        header = { FurnitureHeader() },
        items = products
    ) { item, _ ->
        ProductGridItem(item)
    }
}

@Preview(name = "Best furniture", showSystemUi = true, showBackground = true)
@Composable
fun BestFurniturePreview() {
    MaterialTheme {
        FurnitureList(PreviewData.PreviewProducts)
    }
}

@Preview(name = "Best furniture empty", showSystemUi = true)
@Composable
fun BestFurniturePreviewEmpty() {
    MaterialTheme {
        FurnitureList(emptyList())
    }
}
