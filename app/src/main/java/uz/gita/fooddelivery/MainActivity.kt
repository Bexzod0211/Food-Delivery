package uz.gita.fooddelivery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.fooddelivery.navigation.NavigationHandler
import uz.gita.fooddelivery.presentation.ui.screens.main.MainScreen
import uz.gita.fooddelivery.presentation.ui.screens.splash.SplashScreen
import uz.gita.fooddelivery.presentation.ui.theme.FoodDeliveryTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var navigationHandler: NavigationHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Navigator(screen = SplashScreen()){navigator ->
                LaunchedEffect(key1 = navigator){
                    navigationHandler.navigatorState.onEach {
                        it.invoke(navigator)
                    }.launchIn(lifecycleScope)
                }
                CurrentScreen()
            }
        }
    }
}
