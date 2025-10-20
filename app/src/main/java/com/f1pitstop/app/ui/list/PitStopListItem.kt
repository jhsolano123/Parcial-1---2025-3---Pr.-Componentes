package com.f1pitstop.app.ui.pitstoplist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.f1pitstop.app.R
import com.f1pitstop.app.data.model.PitStop

@Composable
fun PitStopListItem(
    pitStop: PitStop,
    modifier: Modifier = Modifier,
    onDeleteClick: (PitStop) -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        ListItem(
            headlineContent = {
                Text(
                    text = pitStop.piloto,
                    style = MaterialTheme.typography.titleMedium
                )
            },
            supportingContent = {
                Column {
                    Text(
                        text = pitStop.escuderia.displayName,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = pitStop.getFechaFormateada(),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            },
            trailingContent = {
                IconButton(onClick = { onDeleteClick(pitStop) }) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = stringResource(id = R.string.pit_stop_delete_action)
                    )
                }
            },
            colors = ListItemDefaults.colors(
                containerColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}