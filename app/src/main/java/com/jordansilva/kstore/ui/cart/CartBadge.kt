package com.jordansilva.kstore.ui.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jordansilva.kstore.R
import org.koin.androidx.compose.getViewModel

@Composable
fun CartBadge(onClick: () -> Unit) {
    val viewModel = getViewModel<CartViewModel>()
    val viewState by viewModel.viewState.observeAsState(CartViewState.EmptyCart)
    val quantity = when (val state = viewState) {
        is CartViewState.Updated -> state.cart.quantityItems
        else -> 0
    }

    Box(
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_shopping_cart),
            contentDescription = stringResource(R.string.cart),
            modifier = Modifier.align(Alignment.Center)
        )
        Text(
            text = quantity.toString(),
            color = colorResource(R.color.black),
            fontSize = 10.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.height(16.dp)
                .width(16.dp)
                .padding(1.dp)
                .align(Alignment.TopEnd)
                .background(color = colorResource(R.color.colorPrimary), shape = CircleShape)
        )
    }
}

@Preview
@Composable
fun CartBadgePreview() {
    CartBadge {}
}
