package uz.gita.fooddelivery.presentation.ui.screens.details

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import uz.gita.fooddelivery.R
import uz.gita.fooddelivery.data.model.ProductData
import uz.gita.fooddelivery.data.source.local.entity.ProductEntity
import uz.gita.fooddelivery.presentation.ui.theme.MainColor

class FoodDetailsScreen(val product: ProductData) : AndroidScreen() {
    @Composable
    override fun Content() {
        val context = LocalContext.current

        val viewModel: FoodDetailsContract.ViewModel = getViewModel<FoodDetailsViewModel>()
        viewModel.collectSideEffect { sideEffect ->
            when (sideEffect) {
                is FoodDetailsContract.SideEffect.Toast -> {
                    Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
        ScreenContent(product = product, uiState = viewModel.collectAsState(), onEventDispatcher = viewModel::onEventDispatcher)
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
private fun ScreenContent(
    product: ProductData,
    uiState: State<FoodDetailsContract.UiState>,
    onEventDispatcher: (FoodDetailsContract.Intent) -> Unit
) {

    onEventDispatcher.invoke(FoodDetailsContract.Intent.CheckCart(product))

    var clickCount by remember {
        mutableStateOf(1)
    }

    when (uiState.value) {
        FoodDetailsContract.UiState.Init -> {

        }

        is FoodDetailsContract.UiState.IsInCart -> {
            val isInCart = uiState.value as FoodDetailsContract.UiState.IsInCart
            if (isInCart.value) {
                clickCount = 2
            }
        }
    }
    var textBtnSubmit by remember {
        mutableStateOf("")
    }

    textBtnSubmit = if (clickCount == 1) {
        "ADD TO CART"
    } else "TO CART"


    Column(
        modifier = Modifier
            .background(Color.White)
    ) {
        TopAppBar(title = {
            Row(
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth()
                    .background(Color.White),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(
                        id = R.drawable.ic_back
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            onEventDispatcher.invoke(FoodDetailsContract.Intent.BtnBackClicked)
                        }
                    )
                Text(
                    text = "Food details",
                    fontSize = 20.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        })

        LazyColumn {
            item {
                Box(
                    modifier = Modifier
                        .height(250.dp)
                        .padding(16.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                ) {
                    GlideImage(
                        model = product.imageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .matchParentSize(),
                    )
                }
            }
            item {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = product.title,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Text(
                        text = product.price,
                        color = MainColor,
                        fontSize = 20.sp
                    )
                }
            }
            item {
                Text(
                    text = "Product Descriptions",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(
                            start = 16.dp,
                            top = 16.dp
                        )
                )
            }
            item {
                Text(
                    text = product.info,
                    modifier = Modifier
                        .padding(
                            horizontal = 16.dp,
                            vertical = 6.dp
                        ),
                    fontSize = 20.sp,
                    color = Color(0xFF9B9A9A),
                    textAlign = TextAlign.Justify
                )
            }
        }
        Spacer(
            modifier = Modifier
                .weight(1f)
        )
        Box(
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                    vertical = 16.dp
                )
                .height(60.dp)
                .background(
                    color = MainColor,
                    shape = RoundedCornerShape(12.dp)
                )
                .fillMaxWidth()
                .clickable {
                    if (clickCount == 1) {
                        var price: StringBuilder = java.lang.StringBuilder("")
                        product.price.forEach {
                            if (it.isDigit())
                                price.append(it)
                        }
                        onEventDispatcher.invoke(
                            FoodDetailsContract.Intent.AddToCartClicked(
                                ProductEntity(
                                    id = 0,
                                    title = product.title,
                                    imageUrl = product.imageUrl,
                                    price = price
                                        .toString()
                                        .toInt(),
                                    totalPrice = price
                                        .toString()
                                        .toInt(),
                                    info = product.info,
                                    count = 1
                                )
                            )
                        )
                        ++clickCount
                    } else if (clickCount == 2) {
                        onEventDispatcher.invoke(FoodDetailsContract.Intent.OpenCartScreenClicked)
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = textBtnSubmit,
                color = Color.White
            )
        }
    }
}

@Preview
@Composable
private fun ScreenPreview() {
    ScreenContent(
        product = ProductData(
            2,
            "Lavash chicken",
            "https://maxway.uz/_next/image?url=https%3A%2F%2Fcdn.delever.uz%2Fdelever%2F4b0ae161-ac01-4531-81dc-e4e819502667&w=1920&q=75",
            "24 000",
            "lavash, donar beef meat, tomato, chips,tomato sauce, mayonnaise"
        ),
        remember {
            mutableStateOf(FoodDetailsContract.UiState.Init)
        },
    )
    {

    }
}