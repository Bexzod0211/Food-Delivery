package uz.gita.fooddelivery.presentation.ui.screens.splash

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import org.orbitmvi.orbit.compose.collectAsState
import uz.gita.fooddelivery.MainActivity
import uz.gita.fooddelivery.R
import uz.gita.fooddelivery.presentation.ui.theme.FoodDeliveryTheme
import uz.gita.fooddelivery.presentation.ui.theme.MainColor

class SplashScreen : AndroidScreen() {
    @Composable
    override fun Content() {
        val window = (LocalContext.current as MainActivity).window
        window.statusBarColor = MainColor.toArgb()
        val viewModel:SplashContract.ViewModel = getViewModel<SplashViewModel>()
        ScreenContent(uiState = viewModel.collectAsState())
    }
}

@Composable
private fun ScreenContent(uiState:State<SplashContract.UiState>){
    FoodDeliveryTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = MainColor
                )
            ,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_fastfood),
                contentDescription = null,
                modifier = Modifier
                    .size(130.dp)
            )
        }
    }

}

@Preview
@Composable
private fun ScreenPreview() {
    ScreenContent(remember {
        mutableStateOf(SplashContract.UiState.Init)
    })
}