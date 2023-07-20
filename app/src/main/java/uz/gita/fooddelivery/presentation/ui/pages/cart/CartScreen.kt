package uz.gita.fooddelivery.presentation.ui.pages.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.orbitmvi.orbit.compose.collectAsState
import uz.gita.fooddelivery.R
import uz.gita.fooddelivery.data.source.local.entity.ProductEntity
import uz.gita.fooddelivery.presentation.ui.theme.ContainerColor
import uz.gita.fooddelivery.presentation.ui.theme.FoodDeliveryTheme
import uz.gita.fooddelivery.presentation.ui.theme.MainColor

class CartScreen : Tab {
    override val options: TabOptions
        @Composable
        get() = TabOptions(
            0u,
            "Cart",
            icon = painterResource(id = R.drawable.ic_cart)
        )

    @Composable
    override fun Content() {
        val viewModel: CartContract.ViewModel = getViewModel<CartViewModel>()
        ScreenContent(uiState = viewModel.collectAsState(), onEventDispatcher = viewModel::onEventDispatcher)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenContent(
    uiState: State<CartContract.UiState>,
    onEventDispatcher: (CartContract.Intent) -> Unit
) {
    onEventDispatcher.invoke(CartContract.Intent.LoadAllData)

    var openDialog by remember {
        mutableStateOf(false)
    }

    var deleteOne by remember {
        mutableStateOf(false)
    }

    var removeAll by remember {
        mutableStateOf(false)
    }
    var productForDelete:ProductEntity? by remember {
        mutableStateOf(null)
    }

    if (openDialog){
        AlertDialog(
            onDismissRequest = {
                openDialog = false
            },
            confirmButton = {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Row(
                        modifier = Modifier
                            .size(150.dp,56.dp)
                        ,
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            text = "Cancel",
                            fontSize = 16.sp,
                            color = Color(0xFF673AB7),
                            modifier = Modifier
                                .clickable {
                                    openDialog = false
                                }
                        )
                    }
                    Box(
                        modifier = Modifier
                            .size(150.dp, 56.dp)
                            .background(
                                MainColor,
                                shape = RoundedCornerShape(
                                    8.dp
                                )
                            )
                            .clickable {
                                if (deleteOne) {
                                    onEventDispatcher.invoke(CartContract.Intent.DeleteProduct(productForDelete!!))
                                } else if (removeAll) {
                                    onEventDispatcher.invoke(CartContract.Intent.DeleteAllProductsInCart)
                                }
                                deleteOne = false
                                removeAll = false
                                openDialog = false
                            }
                        ,
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = "Delete",
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
                }
            },
            title = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = if (deleteOne) "Delete product"
                        else if(removeAll) "Delete all products"
                        else "",
                        color = Color.Black,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    /*Divider(
                        color = Color.Black,
                        thickness = 1.dp,
                        modifier = Modifier
                            .padding(
                                start = 16.dp,
                                end = 16.dp,
                                top = 8.dp
                            )
                            .fillMaxWidth()
                    )*/
                }
            },
            text = {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(
                            top = 8.dp
                        )
                        .fillMaxWidth()
                ) {
                    Text(
                        text = if (deleteOne) "Are you sure want to delete product?"
                        else if (removeAll) "Are you sure want to delete all products?"
                        else "",
                        color = Color.Black,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            },
            dismissButton = {
            }
        )
    }

    FoodDeliveryTheme {
        when (uiState.value) {
            is CartContract.UiState.AllData -> {
                val allData = uiState.value as CartContract.UiState.AllData
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = Color(0xFFF5F5F5)
                        )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        TopAppBar(
                            modifier = Modifier
                                .height(60.dp)
                                .fillMaxWidth(), title = {
                                Box(
                                    modifier = Modifier
                                        .height(60.dp)
                                        .fillMaxWidth()
                                        .background(
                                            Color.White
                                        )
                                ) {
                                    Text(
                                        text = "Cart",
                                        fontSize = 20.sp,
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                    )

                                    Image(
                                        painter = painterResource(id = R.drawable.ic_delete_all),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .padding(end = 16.dp)
                                            .align(Alignment.CenterEnd)
                                            .clickable {
                                                removeAll = true
                                                deleteOne = false
                                                openDialog = true
                                            }
                                    )
                                }
                            })
                        if (allData.list.isNotEmpty()) {
                            LazyColumn(
                                contentPadding = PaddingValues(
                                    bottom = 130.dp
                                )
                            ) {
                                itemsIndexed(allData.list) { index, item ->
                                    if (index == 0){
                                        ItemCart(
                                            product = item,
                                            onChangeCount = { count, id ->
                                                onEventDispatcher.invoke(CartContract.Intent.UpdateCount(count, id))
                                            },
                                            onDelete = {
                                                productForDelete = it
                                                deleteOne = true
                                                removeAll = false
                                                openDialog = true
                                            },
                                            modifier = Modifier
                                                .padding(top = 16.dp)
                                        )
                                    }else {
                                    ItemCart(
                                        product = item,
                                        onChangeCount = { count, id ->
                                            onEventDispatcher.invoke(CartContract.Intent.UpdateCount(count, id))
                                        },
                                        onDelete = {
                                            productForDelete = it
                                            deleteOne = true
                                            removeAll = false
                                            openDialog = true
                                        }
                                    )
                                    }
                                    if (index != allData.list.size - 1) {
                                        Divider(
                                            modifier = Modifier
                                                .padding(horizontal = 16.dp)
                                                .height(1.dp)
                                                .fillMaxWidth()
                                                .background(Color(0xFFAFAFAF))
                                        )
                                    }
                                }
                            }
                        } else {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.placeholder_cart),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(150.dp),
                                    contentScale = ContentScale.Crop
                                )
                                Text(
                                    text = "There are no products in the cart yet",
                                    color = Color.Black,
                                    fontSize = 15.sp,
                                    modifier = Modifier
                                        .padding(top = 32.dp)
                                )
                            }
                        }
                    }
                    if (allData.list.isNotEmpty()){
                        Column(
                            modifier = Modifier
                                .height(130.dp)
                                .fillMaxWidth()
                                .background(
                                    color = Color.White
                                )
                                .align(Alignment.BottomCenter)
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .weight(1f)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Order price",
                                    fontSize = 20.sp,
                                    color = Color.Black
                                )
                                Text(
                                    text = "${allData.totalPrice} sum",
                                    color = Color.Black
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .padding(
                                        horizontal = 16.dp,
                                        vertical = 8.dp
                                    )
                                    .weight(1f)
                                    .fillMaxWidth()
                                    .background(
                                        color = MainColor,
                                        shape = RoundedCornerShape(8.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Checkout order",
                                    color = Color.White,
                                    fontSize = 18.sp
                                )
                            }
                        }
                    }
                }
            }
            CartContract.UiState.Init -> {

            }
        }
    }
}