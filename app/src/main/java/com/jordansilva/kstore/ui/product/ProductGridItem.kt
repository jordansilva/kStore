package com.jordansilva.kstore.ui.product

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.jordansilva.kstore.R
import com.jordansilva.kstore.util.PreviewData
import dev.chrisbanes.accompanist.glide.GlideImage

@Composable
fun ProductGridItem(item: ProductViewData) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.padding(8.dp)
    ) {
        Card(
            elevation = 6.dp,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.width(180.dp),
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                GlideImage(
                    data = item.image ?: "",
                    contentDescription = null,
                    requestBuilder = { apply(RequestOptions().centerCrop().transform(RoundedCorners(25))) },
                    loading = {
                        Box(Modifier.matchParentSize()) {
                            CircularProgressIndicator(Modifier.align(Alignment.Center))
                        }
                    },
                    modifier = Modifier.width(150.dp)
                        .height(150.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = colorResource(R.color.colorPrimary50))
                        .padding(dimensionResource(R.dimen.card_content_padding))
                ) {
                    Text(text = item.price, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.activity_vertical_margin)))
                    Text(text = item.name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text(text = item.type, fontSize = 12.sp)
                }
            }
        }
    }
}

@Preview(name = "Product Row", showBackground = true)
@Composable
fun ProductGridItemPreview() {
    ProductGridItem(PreviewData.PreviewProducts.first())
}