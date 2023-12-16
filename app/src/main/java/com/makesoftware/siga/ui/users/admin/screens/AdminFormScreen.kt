package com.makesoftware.siga.ui.users.admin.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makesoftware.siga.ui.commons.components.DefaultElevatedButton

@Composable
fun AdminFormScreen(
    modifier: Modifier = Modifier,
    commitButtonText: String,
    onCommitRequest: () -> Unit,
    content: @Composable () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp)
            .padding(top = 40.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            content()
        }

        DefaultElevatedButton(
            onClick = onCommitRequest,
            text = commitButtonText,
            modifier = Modifier.padding(bottom = 15.dp)
        )
    }
}