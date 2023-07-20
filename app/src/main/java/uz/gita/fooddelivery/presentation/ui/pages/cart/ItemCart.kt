package uz.gita.fooddelivery.presentation.ui.pages.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import uz.gita.fooddelivery.R
import uz.gita.fooddelivery.data.model.ProductData
import uz.gita.fooddelivery.data.source.local.entity.ProductEntity

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ItemCart(
    product: ProductEntity,
    onChangeCount:(count:Int,id:Int)->Unit,
    onDelete:(ProductEntity)->Unit,
    modifier: Modifier = Modifier

) {
    Column(
        modifier = modifier
            .height(140.dp)
            .fillMaxWidth()
            .background(
                color = Color.White
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 16.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            GlideImage(
                model = product.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(110.dp),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .padding(
                        start = 8.dp,
                        top = 24.dp,
                        bottom = 24.dp
                    )
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = product.title,
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_remove),
                        contentDescription = null,
                        modifier = Modifier
                            .clickable {
                                onDelete.invoke(product)
                            }
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${priceToString(product.totalPrice)} sum",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Box(
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = Color(0xFFAFAFAF),
                                shape = RoundedCornerShape(8.dp)
                            )
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(
                                    vertical = 4.dp,
                                    horizontal = 4.dp
                                )
                                .wrapContentSize(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_subtract),
                                contentDescription =null,
                                modifier = Modifier
                                    .clickable {
                                        if (product.count != 1){
                                            onChangeCount.invoke(product.count-1,product.id)
                                        }else {
                                            onDelete.invoke(product)
                                        }
                                    }
                            )
                            Text(
                                text = "${product.count}",
                                color = Color.Black,
                                fontSize = 14.sp,
                                modifier = Modifier
                                    .padding(horizontal = 12.dp)
                            )

                            Image(
                                painter = painterResource(id = R.drawable.ic_add),
                                contentDescription =null,
                                modifier = Modifier
                                    .clickable {
                                        onChangeCount.invoke(product.count+1,product.id)
                                    }
                            )
                        }
                    }

                }
            }
        }
    }
}


@Composable
@Preview
fun ItemCartPreview() {
    ItemCart(
        product = ProductEntity(
            id = 1,
            title = "Shawarma Standard",
            imageUrl = "https://maxway.uz/_next/image?url=https%3A%2F%2Fcdn.delever.uz%2Fdelever%2F457713b8-d6d5-4a83-b59e-b85a5b7641cf&w=1920&q=75",
            price = 25_000,
            info = "",
            totalPrice = 25_000,
            count = 1
        ),
        onDelete = {},
        onChangeCount = {count, id ->

        }
    )
}