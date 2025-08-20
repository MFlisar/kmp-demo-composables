package com.michaelflisar.democomposables.examples

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState

@Composable
internal actual fun RootSubPageHeader(demo: Demo.Example, selectedDemoPage: MutableIntState) {
    Text(
        text = demo.name,
        style = MaterialTheme.typography.titleLarge
    )
}