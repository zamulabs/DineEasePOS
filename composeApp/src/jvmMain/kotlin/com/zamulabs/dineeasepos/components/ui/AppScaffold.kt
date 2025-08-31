package com.zamulabs.dineeasepos.components.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Standardized Scaffold for DineEasePOS screens.
 * - Uses Material 3 Scaffold and TopAppBar
 * - Provides consistent content paddings and spacing
 * - Encourages LazyColumn-based content for performance and scalability
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScreenTopBar(
    title: String? = null,
    modifier: Modifier = Modifier,
    navigationIcon: ImageVector? = null,
    onNavigationClick: (() -> Unit)? = null,
    actions: @Composable (() -> Unit) = {},
    titleContent: (@Composable (() -> Unit))? = null,
) {
    TopAppBar(
        title = {
            if (titleContent != null) {
                titleContent()
            } else {
                Text(title.orEmpty(), style = MaterialTheme.typography.titleLarge)
            }
        },
        modifier = modifier,
        navigationIcon = {
            if (navigationIcon != null && onNavigationClick != null) {
                IconButton(onClick = onNavigationClick) {
                    Icon(imageVector = navigationIcon, contentDescription = "Back")
                }
            }
        },
        actions = { actions() }
    )
}

/**
 * AppScaffold with a slot for LazyColumn content via [contentList].
 * This ensures consistent paddings across screens.
 */
@Composable
fun AppScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable (() -> Unit)? = null,
    bottomBar: @Composable (() -> Unit)? = null,
    floatingActionButton: @Composable (() -> Unit)? = null,
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    contentHorizontalPadding: Dp = 40.dp,
    contentVerticalSpacing: Dp = 8.dp,
    content: @Composable (PaddingValues) -> Unit = {},
    contentList: (LazyListScope.() -> Unit)? = null,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { topBar?.invoke() },
        bottomBar = { bottomBar?.invoke() },
        floatingActionButton = { floatingActionButton?.invoke() },
        floatingActionButtonPosition = floatingActionButtonPosition,
    ) { innerPadding ->
        if (contentList != null) {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(innerPadding).padding(horizontal = contentHorizontalPadding),
                verticalArrangement = Arrangement.spacedBy(contentVerticalSpacing),
            ) {
                // Provide a small initial spacer similar to existing screens
                item { Spacer(Modifier.height(contentVerticalSpacing)) }
                contentList()
            }
        } else {
            content(innerPadding)
        }
    }
}
