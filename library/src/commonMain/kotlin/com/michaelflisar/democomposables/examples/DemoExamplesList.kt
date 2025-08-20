package com.michaelflisar.democomposables.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.unit.dp

@Composable
fun rememberSelectedDemo() = rememberSaveable { mutableIntStateOf(-1) }

sealed class Demo {

    class Example(
        val name: String,
        val icon: @Composable (() -> Unit)? = null,
        val page: @Composable ColumnScope.() -> Unit
    ) : Demo()

    object Divider : Demo()

    class Region(
        val label: String
    ) : Demo()
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DemoExamplesList(
    selectedDemo: MutableIntState,
    demos: List<Demo>,
    modifier: Modifier = Modifier
) {
    BackHandler(selectedDemo.intValue != -1) {
        selectedDemo.intValue = -1
    }

    RootContent(
        modifier = modifier,
        selectedDemoPage = selectedDemo,
        demos = demos
    )
}

@Composable
private fun RootContent(
    modifier: Modifier,
    selectedDemoPage: MutableIntState,
    demos: List<Demo>
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        demos.forEachIndexed { index, demo ->
            when (demo) {
                is Demo.Example -> {
                    MainButton(selectedDemoPage, index, demo.name, demo.icon)
                }

                is Demo.Divider -> {
                    HorizontalDivider()
                }

                is Demo.Region -> {
                    Text(demo.label, style = MaterialTheme.typography.titleSmall)
                }
            }
        }
    }
}

@Composable
private fun MainButton(
    selectedDemoPage: MutableState<Int>,
    page: Int,
    text: String,
    icon: @Composable (() -> Unit)?
) {
    OutlinedButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = { selectedDemoPage.value = page }
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            icon?.invoke()
            Text(text)
        }
    }
}