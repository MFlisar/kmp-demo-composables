package com.michaelflisar.democomposables

import androidx.compose.foundation.focusable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@Composable
fun DemoSpinner(
    modifier: Modifier = Modifier,
    label: String = "",
    items: List<String>,
    selected: String,
    enabled: Boolean = true,
    onItemSelected: (index: Int, item: String) -> Unit = { _, _ -> }
) {
    DemoSpinner(
        modifier,
        label,
        items,
        selected,
        { item -> item },
        enabled,
        onItemSelected
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> DemoSpinner(
    modifier: Modifier = Modifier,
    label: String = "",
    items: List<T>,
    selected: MutableState<T>,
    mapper: @Composable (T) -> String,
    enabled: Boolean = true
) {
    DemoSpinner(
        modifier,
        label,
        items,
        selected.value,
        mapper,
        enabled
    ) { index, item ->
        selected.value = item
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> DemoSpinner(
    modifier: Modifier = Modifier,
    label: String = "",
    items: List<T>,
    selected: T,
    mapper: @Composable (T) -> String,
    enabled: Boolean = true,
    onItemSelected: (index: Int, item: T) -> Unit = { _, _ -> }
) {
    var selected by remember { mutableStateOf(selected) }
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        OutlinedTextField(
            modifier = modifier
                .focusable(false)
                .menuAnchor(MenuAnchorType.PrimaryNotEditable),
            readOnly = true,
            enabled = enabled,
            value = mapper(selected),
            onValueChange = { },
            label = { Text(text = label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded)
            },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
            modifier = modifier
        ) {
            items.forEachIndexed { index, item ->
                DropdownMenuItem(
                    onClick = {
                        if (item != selected) {
                            selected = item
                            onItemSelected.invoke(index, item)
                        }
                        expanded = false
                    },
                    text = {
                        Text(text = mapper(item))
                    }
                )
            }
        }
    }
}