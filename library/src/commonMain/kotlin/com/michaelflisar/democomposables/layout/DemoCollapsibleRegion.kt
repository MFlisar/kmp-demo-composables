package com.michaelflisar.democomposables.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.michaelflisar.democomposables.internal.MyExpandableTitle
import com.michaelflisar.democomposables.internal.rememberMyExpandableTitleStyle

object DemoCollapsibleRegion {
    class State(
        private val expandedIds: SnapshotStateList<Int>,
        private val single: Boolean
    ) {
        fun isExpanded(id: Int) = expandedIds.contains(id)
        fun toggle(id: Int) {
            if (isExpanded(id))
                expandedIds.remove(id)
            else {
                if (single) {
                    expandedIds.clear()
                }
                expandedIds.add(id)
            }

        }
    }
}

@Composable
fun rememberDemoExpandedRegions(ids: List<Int> = emptyList(), single: Boolean = false) = DemoCollapsibleRegion.State(
    expandedIds = remember { ids.toMutableStateList() },
    single = single
)

@Composable
fun DemoCollapsibleRegion(
    title: String,
    regionId: Int,
    state: DemoCollapsibleRegion.State,
    modifier: Modifier = Modifier,
    info: String = "",
    content: @Composable () -> Unit
) {
    MyExpandableTitle(
        modifier = modifier,
        title = { Text(title) },
        style = rememberMyExpandableTitleStyle(
            contentColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = MaterialTheme.colorScheme.primary,
            iconPlacement = MyExpandableTitle.IconPlacement.Right,
            applyColorsToTitle = true,
            applyColorsToContent = false
        ),
        expanded = state.isExpanded(regionId),
        onToggle = {
            state.toggle(regionId)
        }
    ) {
        Column {
            Info(info)
            content()
        }
    }
}

@Composable
private fun Info(info: String) {
    if (info.isNotEmpty()) {
        Card(
            modifier = Modifier
                .padding(bottom = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(Icons.Default.Info, null)
                Text(
                    text = info,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}