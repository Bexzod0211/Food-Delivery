package uz.gita.fooddelivery.presentation.ui.pages.myorders

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import uz.gita.fooddelivery.data.model.MyOrderData
import uz.gita.fooddelivery.presentation.ui.theme.MainColor

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ItemMyOrders(order: MyOrderData) {
    Row(
        modifier = Modifier
            .padding(
                top = 8.dp,
                bottom = 8.dp
            )
            .height(100.dp)
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                end = 16.dp
            )
            .background(
                color = Color.White
            )
    ) {
        Box(
            modifier = Modifier
                .width(100.dp)
                .fillMaxHeight()
                .clip(RoundedCornerShape(16.dp))
        ) {

            GlideImage(
                model = order.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .matchParentSize(),
                contentScale = ContentScale.Crop
            )
        }

        Column(
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    top = 8.dp,
                    bottom = 8.dp
                )
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = order.title,
                fontSize = 20.sp,
                color = MainColor
            )
            Text(
                text = "${order.totalPrice} sum",
                fontSize = 18.sp,
                color = Color.Black
            )

            Text(
                text = "count : ${order.count}",
                fontSize = 18.sp,
                color = Color.Black
            )
        }
    }

}

@Composable
@Preview
fun ItemMyOrderPreview() {
    ItemMyOrders(
        order = MyOrderData(
            id = 1,
            title = "Shawarma Standard",
            imageUrl = "https://maxway.uz/_next/image?url=https%3A%2F%2Fcdn.delever.uz%2Fdelever%2F457713b8-d6d5-4a83-b59e-b85a5b7641cf&w=1920&q=75",
            totalPrice = "25 000",
            count = 12,
            uid = ""
        )
    )
}
