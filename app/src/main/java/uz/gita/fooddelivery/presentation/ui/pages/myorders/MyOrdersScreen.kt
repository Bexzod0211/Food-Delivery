package uz.gita.fooddelivery.presentation.ui.pages.myorders

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import uz.gita.fooddelivery.R
import uz.gita.fooddelivery.presentation.ui.pages.home.HomeContract
import uz.gita.fooddelivery.presentation.ui.theme.MainColor
import uz.gita.fooddelivery.utils.myLog

class MyOrdersScreen : Tab {

    override val options: TabOptions
        @Composable
        get() = TabOptions(
            0u,
            "My orders",
            icon = painterResource(id = R.drawable.ic_my_orders)
        )

    @Composable
    override fun Content() {
        val context = LocalContext.current

        val viewModel: MyOrdersContract.ViewModel = getViewModel<MyOrdersViewModel>()

        viewModel.collectSideEffect { sideEffect ->
            when (sideEffect) {
                is MyOrdersContract.SideEffect.Toast -> {
                    Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        ScreenContent(uiState = viewModel.collectAsState(), onEventDispatcher = viewModel::onEventDispatcher)

    }
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun ScreenContent(
        uiState: State<MyOrdersContract.UiState>,
        onEventDispatcher: (MyOrdersContract.Intent) -> Unit
    ) {

//    myLog("onEventDispatcher.invoke(MyOrdersContract.Intent.LoadAllData)")
        var count by remember {
            mutableStateOf(0)
        }

        if (count == 0){
            myLog("count: $count")
            count++
            onEventDispatcher.invoke(MyOrdersContract.Intent.LoadAllData)
        }


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
                            text = "My Orders",
                            fontSize = 20.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .align(
                                    Alignment.Center
                                )
                        )
                    }
                }
            )

            when (uiState.value) {
                is MyOrdersContract.UiState.AllData -> {
                    val value = uiState.value as MyOrdersContract.UiState.AllData

                    if (value.myOrders.isNotEmpty()) {
                        LazyColumn {
                            items(value.myOrders) {
                                ItemMyOrders(order = it)
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
                                painter = painterResource(id = R.drawable.placeholder_my_orders),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(150.dp),
                                contentScale = ContentScale.Crop
                            )
                            Text(
                                text = "You have not any orders yet!",
                                color = Color.Black,
                                fontSize = 15.sp,
                                modifier = Modifier
                                    .padding(top = 32.dp)
                            )
                        }
                    }


                }

                MyOrdersContract.UiState.Init -> {

                }

                is MyOrdersContract.UiState.Progressbar -> {

                }
            }
            if (uiState.value is MyOrdersContract.UiState.Progressbar) {
                val progressBar = uiState.value as MyOrdersContract.UiState.Progressbar
                if (progressBar.isLoadIng) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            color = MainColor,
                            modifier = Modifier
                                .size(56.dp)
                        )
                    }
                }
            }
        }
    }


}



@Preview
@Composable
private fun ScreenPreview() {
//    ScreenContent()
}