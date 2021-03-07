package com.jordansilva.kstore.ui.compose

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp


sealed class LazyGridCells {
    class Fixed(val count: Int) : LazyGridCells()
    class Adaptive(val minSize: Dp) : LazyGridCells()
}

@Composable
fun <T> LazyGrid(
    items: List<T>,
    cells: LazyGridCells,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    header: @Composable () -> Unit = {},
    content: @Composable LazyItemScope.(T, Int) -> Unit,
) {
    when (cells) {
        is LazyGridCells.Fixed -> LazyGrid(items, cells.count, modifier, state, header, content)
        is LazyGridCells.Adaptive -> {
            BoxWithConstraints(modifier = modifier) {
                val numPerRow = maxOf((maxWidth / cells.minSize).toInt(), 1)
                LazyGrid(items, numPerRow, modifier, state, header, content)
            }
        }
    }
}

@Composable
private fun <T> LazyGrid(
    items: List<T>,
    numPerRow: Int,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    header: @Composable () -> Unit = {},
    content: @Composable LazyItemScope.(T, Int) -> Unit,
) {
    val chunkedList = items.chunked(numPerRow)
    LazyColumn(modifier = modifier, state = state) {
        item { header() }
        itemsIndexed(chunkedList) { index, item ->
            Row { item.forEachIndexed { rowIndex, item -> content(item, index * numPerRow + rowIndex) } }
        }
    }
}