package uz.gita.fooddelivery.presentation.ui.pages.myorders

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
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