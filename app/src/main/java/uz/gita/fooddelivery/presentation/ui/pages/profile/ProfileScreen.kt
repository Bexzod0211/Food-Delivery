package uz.gita.fooddelivery.presentation.ui.pages.profile

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import uz.gita.fooddelivery.R

class ProfileScreen : Tab{
    override val options: TabOptions
    @Composable
        get() = TabOptions(
            0u,
            "Profile",
            icon = painterResource(id = R.drawable.ic_profile)
        )

    @Composable
    override fun Content() {

    }
}