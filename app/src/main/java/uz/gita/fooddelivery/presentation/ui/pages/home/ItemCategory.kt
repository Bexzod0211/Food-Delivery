package uz.gita.fooddelivery.presentation.ui.pages.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.gita.fooddelivery.data.model.CategoryData
import uz.gita.fooddelivery.data.model.ProductData
import uz.gita.fooddelivery.utils.myLog

@Composable
fun ItemCategory(
    category: CategoryData,
    onItemClicked: (ProductData) -> Unit
) {
    val height = if (category.items.size % 2 == 1) {
        category.items.size / 2 + 1
    } else {
        category.items.size / 2
    }
    Column(
        modifier = Modifier
            .padding(top = 16.dp)
            .background(Color.White)
    ) {
        Text(
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    top = 16.dp
                ),
            text = category.title,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )
        LazyVerticalGrid(
            modifier = Modifier
                .height((230 * height + 8).dp)
                .background(Color.White)
                .padding(bottom = 8.dp),
            columns = GridCells.Fixed(2)
        ) {
            itemsIndexed(category.items) { index, item ->
                ItemProduct(
                    product = item,
                    span = index,
                    onItemClicked = {
                        onItemClicked.invoke(it)
                    }
                )
            }
        }
    }
}


@Preview
@Composable
fun ItemCategoryPreview() {
    ItemCategory(
        CategoryData(
            1, "\uD83C\uDF71Donar Kebab",
            mutableListOf(
                ProductData(
                    1,
                    "Donar Kebab",
                    "https://maxway.uz/_next/image?url=https%3A%2F%2Fcdn.delever.uz%2Fdelever%2F9674dd2d-6e3e-47ce-9689-572472a81968&w=1200&q=75",
                    "38 000",
                    ""
                ),
                ProductData(
                    2,
                    "Donar box",
                    "https://maxway.uz/_next/image?url=https%3A%2F%2Fcdn.delever.uz%2Fdelever%2F7cbef93f-0ef9-4628-a3d3-eeaeed748b38&w=1200&q=75",
                    "35 000",
                    ""
                )
            )

        )
    ) {

    }
}
