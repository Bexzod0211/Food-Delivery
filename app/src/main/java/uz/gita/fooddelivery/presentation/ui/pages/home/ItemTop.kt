package uz.gita.fooddelivery.presentation.ui.pages.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.gita.fooddelivery.data.model.CategoryData
import uz.gita.fooddelivery.presentation.ui.theme.MainColor
import uz.gita.fooddelivery.utils.myLog

@Composable
fun ItemTopCategory(
    category: CategoryData,
    order: Int,
    size: Int,
    selectedCategories: List<String>,
    onClicked: (category: CategoryData, selected: Boolean) -> Unit
) {


    var _selected by remember {
        mutableStateOf(false)
    }
    selectedCategories.forEach {
        if (it == category.title)
            _selected = true
    }

    val modifier = if (order == size - 1) Modifier.padding(
        start = 16.dp,
        end = 16.dp
    ) else Modifier
        .padding(start = 16.dp)
    Box(
        modifier = modifier
            .background(
                color = if (_selected) MainColor
                else Color(0xFFF5F5F5),
                shape = RoundedCornerShape(4.dp)
            )
            .padding(
                horizontal = 12.dp,
                vertical = 4.dp
            )
            .clickable {
                _selected = !_selected
                onClicked.invoke(category, _selected)
            }
    ) {

        Text(
            modifier = Modifier,
            text = category.title,
            color = if (_selected) Color.White
            else Color.Black,
            fontSize = 18.sp
        )
    }
}

@Preview
@Composable
fun ItemTopCategoryPreview() {
    ItemTopCategory(
        category = CategoryData(1, "\uD83C\uDF54Burgers", listOf()),
        order = 0,
        size = 12,
        selectedCategories = listOf(),
        onClicked = { category, selected ->
        }
    )
}