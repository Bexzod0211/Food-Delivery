package uz.gita.fooddelivery.presentation.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import org.orbitmvi.orbit.compose.collectAsState
import uz.gita.fooddelivery.MainActivity
import uz.gita.fooddelivery.domain.repository.AppRepository
import uz.gita.fooddelivery.presentation.ui.pages.cart.CartScreen
import uz.gita.fooddelivery.presentation.ui.pages.home.HomeScreen
import uz.gita.fooddelivery.presentation.ui.pages.myorders.MyOrdersScreen
import uz.gita.fooddelivery.presentation.ui.pages.profile.ProfileScreen
import uz.gita.fooddelivery.presentation.ui.theme.MainColor
import javax.inject.Inject

class MainScreen(private val screen:Tab = HomeScreen()) : AndroidScreen() {


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val window = (LocalContext.current as MainActivity).window
        window.statusBarColor = Color.White.toArgb()
        val viewModel:MainContract.ViewModel = getViewModel<MainViewModel>()
        TabNavigator(tab = screen){
            Scaffold(content = {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(it)){
                    CurrentTab()
                }
            },
                bottomBar = {
                    NavigationBar(modifier = Modifier) {
                        TabNavigationItem(tab = HomeScreen())
                        TabNavigationItem(tab = CartScreen(),viewModel.collectAsState())
                        TabNavigationItem(tab = MyOrdersScreen())
                        TabNavigationItem(tab = ProfileScreen())
                    }
                })
        }
    }
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun RowScope.TabNavigationItem(tab: Tab,uiState:State<MainContract.UiState> = remember {
        mutableStateOf(MainContract.UiState.Init)
    }) {
        val tabNavigator = LocalTabNavigator.current
        var badgeCount = 0
        when(uiState.value){
            is MainContract.UiState.BadgeCount->{
                 badgeCount = (uiState.value as MainContract.UiState.BadgeCount).count
            }
            MainContract.UiState.Init->{

            }
        }

            NavigationBarItem(
                selected = tabNavigator.current == tab,
                onClick = {
                    tabNavigator.current = tab
                },
                icon = {
                    if (tab is CartScreen) {
                        if (badgeCount > 0) {
                            BadgedBox(badge = {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(Color.Red), contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = badgeCount.toString(),
                                        color = Color.White,
                                        modifier = Modifier
                                            .background(Color.Red)
                                            .padding(horizontal = 8.dp, vertical = 4.dp),
                                        fontSize = 10.sp
                                    )
                                }
                            }) {
                                Icon(
                                    painter = tab.options.icon!!,
                                    contentDescription = null,
                                    tint = if (tabNavigator.current == tab) MainColor else Color(0xFF6D6D6D)
                                )
                            }
                        } else {
                            Icon(
                                painter = tab.options.icon!!,
                                contentDescription = null,
                                tint = if (tabNavigator.current == tab) MainColor else Color(0xFF6D6D6D)
                            )
                        }
                    }
                    else {
                        Icon(
                            painter = tab.options.icon!!,
                            contentDescription = null,
                            tint = if (tabNavigator.current == tab) MainColor else Color(0xFF6D6D6D)
                        )
                    }
                },
                label = {

                    Text(
                        text = tab.options.title,
                        modifier = Modifier.padding(top = 10.dp),
                        fontSize = 12.sp,
                        color = if (tabNavigator.current == tab) MainColor else Color(0xFF6D6D6D)
                    )
                },

                )
    }
}

