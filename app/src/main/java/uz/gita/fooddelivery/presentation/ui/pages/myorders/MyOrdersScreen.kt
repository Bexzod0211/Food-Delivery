package uz.gita.fooddelivery.presentation.ui.pages.myorders

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import uz.gita.fooddelivery.R

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

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenContent(){
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
    }
}

@Preview
@Composable
private fun ScreenPreview(){
    ScreenContent()
}