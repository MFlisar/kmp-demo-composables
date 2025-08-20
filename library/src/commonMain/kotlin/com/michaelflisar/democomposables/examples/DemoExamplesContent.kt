package com.michaelflisar.democomposables.examples

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.unit.dp

@Composable
internal expect fun RootSubPageHeader(demo: Demo.Example, selectedDemoPage: MutableIntState)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DemoExamplesContent(
    selectedDemo: MutableIntState,
    demos: List<Demo>,
    modifier: Modifier = Modifier.Companion
) {
    BackHandler(selectedDemo.intValue != -1) {
        selectedDemo.intValue = -1
    }
    Column(modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        val demo = demos[selectedDemo.intValue] as Demo.Example
        RootSubPageHeader(demo, selectedDemo)
        demo.page(this)
    }
}