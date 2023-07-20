package uz.gita.fooddelivery.presentation.ui.screens.sign

import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import uz.gita.fooddelivery.R
import uz.gita.fooddelivery.presentation.ui.theme.FoodDeliveryTheme
import uz.gita.fooddelivery.presentation.ui.theme.MainColor
import uz.gita.fooddelivery.presentation.ui.theme.MainRedColor

class SignScreen : AndroidScreen() {
    @Composable
    override fun Content() {
        val context = LocalContext.current
        val viewModel: SignContract.ViewModel = getViewModel<SignViewModel>()

        viewModel.collectSideEffect { sideEffect ->
            when (sideEffect) {
                is SignContract.SideEffect.Toast -> {
                    Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
        ScreenContent(viewModel.collectAsState(), viewModel::onEventDispatcher)
    }
}

@Composable
private fun ScreenContent(
    uiState: State<SignContract.UiState>,
    onEventDispatcher: (SignContract.Intent) -> Unit
) {
    var selectedTabIndex by remember {
        mutableStateOf(0)
    }
    FoodDeliveryTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = MainColor
                )
        ) {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                modifier = Modifier
                    .padding(
                        horizontal = 24.dp,
                        vertical = 24.dp
                    )
                    .fillMaxWidth(),
                /*divider = {
                            Divider(
                                thickness = 2.dp,
                                color = MainRedColor // set your desired color here
                            )
                        }*/

            ) {
                tabs.forEachIndexed { index, item ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = {
                            selectedTabIndex = index
                        },
                        text = {
                            Text(
                                text = item,
                                fontSize = 28.sp,
                                color = Color.White
                            )
                        },
                        modifier = Modifier
                            .background(MainColor)
                    )
                }
            }

            when (selectedTabIndex) {
                0 -> {
                    TabRow(
                        pos = 0,
                        onLoginClicked = { email, password ->
                            onEventDispatcher.invoke(SignContract.Intent.Login(email, password))
                        },
                        onRegisterClicked = { firstname, lastName, email, password, confirmPassword ->

                        }
                    )
                }

                1 -> {
                    TabRow(
                        pos = 1,
                        onLoginClicked = { email, password ->
                            onEventDispatcher.invoke(SignContract.Intent.Login(email, password))
                        },
                        onRegisterClicked = { firstname, lastName, email, password, confirmPassword ->
                            onEventDispatcher.invoke(SignContract.Intent.SignUp(firstname, lastName, email, password, confirmPassword))
                        }
                    )
                }
            }

            when (uiState.value) {
                is SignContract.UiState.Progressbar -> {
                    val progressBar = uiState.value as SignContract.UiState.Progressbar
                    if (progressBar.loading) {
                        CircularProgressIndicator(
                            color = MainRedColor,
                            modifier = Modifier
                                .padding(top = 40.dp)
                                .size(56.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                    }
                }

                SignContract.UiState.Init -> {

                }
            }
        }
    }
}

/*

*/
@Composable
private fun TabRow(
    pos: Int,
    onLoginClicked: (email: String, password: String) -> Unit,
    onRegisterClicked: (firstname: String, lastName: String, email: String, password: String, confirmPassword: String) -> Unit
) {
    var firstName = ""
    var lastName = ""
    var email = ""
    var password = ""
    var confirmPassword = ""
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (pos == 0) "Log in to your account"
            else "Create an account",
            color = Color.White,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(
                    top = 12.dp,
                    bottom = 24.dp
                )
        )
        if (pos == 0) {
            InputsContent(
                iconId = R.drawable.ic_mail,
                placeHolder = "E-mail"
            ) {
                email = it
            }
            InputsContent(
                iconId = R.drawable.ic_lock,
                placeHolder = "Password"
            ) {
                password = it
            }
        } else {
            InputsContent(
                iconId = R.drawable.ic_person,
                placeHolder = "Firstname"
            ) {
                firstName = it
            }

            InputsContent(
                iconId = R.drawable.ic_person,
                placeHolder = "Lastname"
            ) {
                lastName = it
            }

            InputsContent(
                iconId = R.drawable.ic_mail,
                placeHolder = "E-mail"
            ) {
                email = it
            }

            InputsContent(
                iconId = R.drawable.ic_lock,
                placeHolder = "Password"
            ) {
                password = it
            }

            InputsContent(
                iconId = R.drawable.ic_lock,
                placeHolder = "Confirm password"
            ) {
                confirmPassword = it
            }
        }
        Box(
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 32.dp
                )
                .height(55.dp)
                .fillMaxWidth()
                .background(
                    color = MainRedColor,
                    shape = RoundedCornerShape(8.dp),
                )
                .clickable {
                    if (pos == 0) {
                        onLoginClicked.invoke(email, password)
                    } else if (pos == 1) {
                        onRegisterClicked.invoke(firstName, lastName, email, password, confirmPassword)
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (pos == 0) "LOGIN"
                else "REGISTER",
                color = Color.White,
                fontSize = 18.sp
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputsContent(
    @DrawableRes
    iconId: Int,
    placeHolder: String,
    onValueChanged: (String) -> Unit
) {
    var inputValue by remember {
        mutableStateOf("")
    }

    Box(
        modifier = Modifier
            .padding(
                top = 16.dp,
                start = 16.dp,
                end = 16.dp
            )
            .height(55.dp)
            .fillMaxWidth()
            .background(
//                color = Color(0xFF141B3F),
                color = Color(0xFF330166),
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .matchParentSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = iconId),
                contentDescription = null,
                modifier = Modifier
                    .padding(
                        start = 8.dp
                    )
            )
            TextField(
                value = inputValue,
                onValueChange = {
                    inputValue = it
                    onValueChanged.invoke(it)
                },
                maxLines = 1,
                modifier = Modifier
                    .weight(1f),
                colors = TextFieldDefaults
                    .textFieldColors(
                        textColor = Color.White,
                        containerColor = Color.Transparent,
                        cursorColor = MainRedColor,
                        focusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                placeholder = {
                    Text(
                        text = placeHolder,
                        color = Color(0xFFA2A2B5),
                        fontSize = 18.sp
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = if (placeHolder == "E-mail") KeyboardType.Email
                    else KeyboardType.Text
                ),
                textStyle = TextStyle(
                    fontSize = 18.sp
                )
            )
        }
    }
}


private val tabs = listOf(
    "Sign In",
    "Register"
)

@Preview
@Composable
private fun ScreenPreview() {
    ScreenContent(
        remember {
            mutableStateOf(SignContract.UiState.Init)
        }
    ) {

    }
}