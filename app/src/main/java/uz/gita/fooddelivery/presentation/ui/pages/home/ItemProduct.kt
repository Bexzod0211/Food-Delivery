package uz.gita.fooddelivery.presentation.ui.pages.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.RequestBuilderTransform
import uz.gita.fooddelivery.R
import uz.gita.fooddelivery.data.model.ProductData
import uz.gita.fooddelivery.utils.myLog

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ItemProduct(
    product: ProductData,
    span: Int,
    onItemClicked: (ProductData) -> Unit
) {

    val painter = rememberImagePainter(
        data = product.imageUrl,
        builder = {
            placeholder(R.drawable.placeholder_product_image)
        }
    )
    var modifier = if (span % 2 == 0) {
        Modifier
            .padding(
                start = 16.dp,
                end = 16.dp
            )
            .fillMaxWidth()
    } else {
        Modifier
            .padding(start = 8.dp, end = 16.dp)
            .fillMaxWidth()
    }
    Column(
        modifier = modifier
            .height(230.dp)
            .clickable {
                onItemClicked.invoke(product)
            }
    ) {
        Column(
            modifier = Modifier
                .height(130.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            /*GlideImage(
                model = product.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()

            )*/
            Image(
                painter = painter,
                contentDescription = "image",
                modifier = Modifier
                    .fillMaxSize()
            )
        }
        Box(
            modifier = Modifier
                .background(
                    color = Color(0xFFEFEFEF),
                    shape = RoundedCornerShape(16.dp)
                )
                .fillMaxWidth()
                .height(100.dp)
        )
        {
            Column(
                modifier = Modifier
                    .matchParentSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = product.title,
                    color = Color(0xFF505050),
                    fontSize = 17.sp,
                    modifier = Modifier
                        .padding(
                            start = 16.dp,
                            end = 16.dp
                        ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
                Box(
                    modifier = Modifier
                        .background(
                            color = Color(0xFFFECE00),
                            shape = CircleShape
                        )
                ) {
                    Text(
                        text = product.price,
                        color = Color(0xFF6A4094),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(
                                vertical = 4.dp,
                                horizontal = 8.dp
                            )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemProductPreview() {
    ItemProduct(
        ProductData(
            id = 1,
            title = "Shawarma Standard",
            imageUrl = "https://maxway.uz/_next/image?url=https%3A%2F%2Fcdn.delever.uz%2Fdelever%2F457713b8-d6d5-4a83-b59e-b85a5b7641cf&w=1920&q=75",
            price = "25 000",
            info = ""
        ),
        0
    ) {

    }
}