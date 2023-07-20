package uz.gita.fooddelivery.presentation.ui.pages.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import uz.gita.fooddelivery.R
import uz.gita.fooddelivery.presentation.ui.screens.sign.SignContract
import uz.gita.fooddelivery.presentation.ui.theme.ContainerColor
import uz.gita.fooddelivery.presentation.ui.theme.FoodDeliveryTheme
import uz.gita.fooddelivery.presentation.ui.theme.MainColor
import uz.gita.fooddelivery.presentation.ui.theme.MainRedColor
import uz.gita.fooddelivery.utils.myLog

class HomeScreen : Tab {
    override val options: TabOptions
        @Composable
        get() {
            return TabOptions(
                0u,
                "Home",
                icon = painterResource(id = R.drawable.ic_home)
            )
        }

    @Composable
    override fun Content() {
        val context = LocalContext.current
        val viewModel: HomeContract.ViewModel = getViewModel<HomeViewModel>()

        viewModel.collectSideEffect { sideEffect ->
            when (sideEffect) {
                is HomeContract.SideEffect.Toast -> {
                    Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
        ScreenContent(viewModel.collectAsState(), viewModel::onEventDispatcher)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenContent(
    uiState: State<HomeContract.UiState>,
    onEventDispatcher: (HomeContract.Intent) -> Unit
) {
    onEventDispatcher.invoke(HomeContract.Intent.LoadAllData)

    var searchQuery by remember {
        mutableStateOf("")
    }
    val focusManager: FocusManager = LocalFocusManager.current

    FoodDeliveryTheme {
        Box {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = ContainerColor
                    )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .height(56.dp)
                        .fillMaxWidth()
                        .background(
                            color = Color.White
                        )
                ) {
                    Text(
                        text = "Max way",
                        fontSize = 24.sp,
                        textAlign = TextAlign
                            .Start,
                        modifier = Modifier
                            .padding(start = 16.dp)
                    )
                }
                Column(
                    modifier = Modifier
                        .height(72.dp)
                        .fillMaxWidth()
                        .background(
                            color = Color.White
                        )
                ) {

                    TextField(
                        modifier = Modifier
                            .padding(
                                start = 16.dp,
                                end = 16.dp,
                                top = 8.dp,
                                bottom = 8.dp
                            )
                            .fillMaxWidth()
                            .border(
                                width = 0.dp,
                                shape = RoundedCornerShape(16.dp),
                                color = Color(0xFFECECEC)
                            ),
                        value = searchQuery,
                        onValueChange = { query ->
                            searchQuery = query
                            // Perform your search operation here
                        },
                        singleLine = true,
                        leadingIcon = {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = "Search"
                            )
                        },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Clear",
                                    modifier = Modifier.clickable {
                                        searchQuery = ""
                                        // Perform your clear operation here
                                    }
                                )
                            }
                        },
                        keyboardActions = KeyboardActions(
                            onDone = {
                                focusManager.clearFocus()
                                // Perform any further actions when the keyboard is done
                            }
                        ),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = ContainerColor,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        placeholder =  {
                            Text(
                                text = "Search",
                                modifier = Modifier.fillMaxSize(),
                                color = Color(0xFF9C9C9C),
                                fontSize = 18.sp
                            )
                        },
                        textStyle = TextStyle(fontSize = 18.sp)
                    )
                }

                when (uiState.value) {
                    is HomeContract.UiState.InitData -> {
                        val data = uiState.value as HomeContract.UiState.InitData
                        Column {
                            LazyRow(
                                modifier = Modifier
                                    .background(Color.White)
                                    .height(56.dp),
                                verticalAlignment = Alignment.CenterVertically) {
                                itemsIndexed(data.categoryTitleList!!){index, item ->
                                    ItemTopCategory(
                                        category = item,
                                        order = index,
                                        size = data.categoryTitleList.size,
                                        selectedCategories = data.selectedList!!,
                                        onClicked = {category, selected ->
                                            onEventDispatcher.invoke(HomeContract.Intent.SelectOrUnselectCategory(category.title,selected))
                                        })
                                }
                            }
                            LazyColumn {
                                data.categoryList?.forEach {
                                    item {
                                        ItemCategory(
                                            category = it,
                                            onItemClicked = {
                                                onEventDispatcher.invoke(HomeContract.Intent.ItemClicked(it))
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                    HomeContract.UiState.Init ->{

                    }
                    is HomeContract.UiState.Progressbar->{

                    }
                }
            }
            if (uiState.value is HomeContract.UiState.Progressbar){
                val progressBar = uiState.value as HomeContract.UiState.Progressbar
                if (progressBar.loading) {
                    CircularProgressIndicator(
                        color = MainColor,
                        modifier = Modifier
                            .size(56.dp)
                            .align(Alignment.Center)
                    )
                }
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
private fun ScreenPreview() {
    ScreenContent(
        remember {
            mutableStateOf(HomeContract.UiState.InitData())
        }
    ) {

    }
}