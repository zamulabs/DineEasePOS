/*
 * Copyright 2025 Zamulabs.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zamulabs.dineeasepos.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.splitpane.ExperimentalSplitPaneApi
import org.jetbrains.compose.splitpane.HorizontalSplitPane
import org.jetbrains.compose.splitpane.rememberSplitPaneState
import java.awt.Cursor

private fun Modifier.cursorForHorizontalResize(): Modifier =
    pointerHoverIcon(PointerIcon(Cursor(Cursor.E_RESIZE_CURSOR)))

/**
 * Reusable 3/4 : 1/4 pane layout using Compose SplitPane.
 * Divider/handle styled to match the provided design.
 */
@OptIn(ExperimentalSplitPaneApi::class)
@Composable
fun PaneLayout(
    modifier: Modifier = Modifier,
    main: @Composable () -> Unit,
    side: @Composable () -> Unit,
) {
    // Compute safe bounds for the splitter based on actual available width
    BoxWithConstraints(modifier.fillMaxSize()) {
        val density = androidx.compose.ui.platform.LocalDensity.current
        val minFirst = 240.dp
        val minSecond = 320.dp
        val splitterWidth = 8.dp // visible 1dp + handle area ~7dp

        val totalPx = with(density) { maxWidth.toPx() }
        val minFirstPx = with(density) { minFirst.toPx() }
        val minSecondPx = with(density) { minSecond.toPx() }
        val splitterPx = with(density) { splitterWidth.toPx() }
        val availablePx = (totalPx - splitterPx).coerceAtLeast(0f)

        val minPerc = if (availablePx > 0f) (minFirstPx / availablePx).coerceIn(0f, 1f) else 0.5f
        val maxPerc =
            if (availablePx > 0f) (1f - (minSecondPx / availablePx)).coerceIn(0f, 1f) else 0.5f
        val desired = 0.75f
        val invalidRange = minPerc > maxPerc
        val safeTarget = if (invalidRange) 0.5f else desired.coerceIn(minPerc, maxPerc)

        if (invalidRange) {
            // Fallback rendering when width is too small for both min panes: avoid SplitPane entirely
            // Additionally, guard against SplitPane measuring during animations by short-circuiting composition
            Box(Modifier.fillMaxSize()) { main() }
            return@BoxWithConstraints
        }

        val splitState = rememberSplitPaneState(initialPositionPercentage = safeTarget)

        HorizontalSplitPane(
            modifier = Modifier.fillMaxSize(),
            splitPaneState = splitState
        ) {
            // Rounded corners and spacing around both panes
            val paneRadius = 12.dp
            val gap = 8.dp
            first(minSize = minFirst) {
                androidx.compose.foundation.layout.Box(
                    Modifier
                        .fillMaxSize()
                        .padding(end = gap)
                        .clip(androidx.compose.foundation.shape.RoundedCornerShape(paneRadius))
                        .background(Color(0xFF122118))
                ) { main() }
            }
            second(minSize = minSecond) {
                androidx.compose.foundation.layout.Box(
                    Modifier
                        .fillMaxSize()
                        .padding(start = gap)
                        .clip(androidx.compose.foundation.shape.RoundedCornerShape(paneRadius))
                        .background(Color(0xFF122118))
                ) { side() }
            }

            splitter {
                // Visible divider line: 1dp, matches #1B3124 from design
                visiblePart {
                    Box(
                        Modifier
                            .width(1.dp)
                            .fillMaxHeight()
                            .background(Color(0xFF1B3124))
                    )
                }
                // Handle area with resize cursor and subtle overlay
                handle {
                    Box(
                        Modifier
                            .markAsHandle()
                            .cursorForHorizontalResize()
                            .background(SolidColor(Color.Gray), alpha = 0.50f)
                            .width(splitterWidth - 1.dp)
                            .fillMaxHeight()
                    )
                }
            }
        }
    }
}


@Composable
fun SplitScreenScaffold(
    modifier: Modifier = Modifier,
    navRail: (@Composable () -> Unit)? = null,
    topBar: (@Composable () -> Unit)? = null,
    main: @Composable () -> Unit,
    side: @Composable (() -> Unit)? = null,
) {
    Column(modifier.fillMaxSize()) {
        if (topBar != null) {
            topBar()
        }
        Row(Modifier.weight(1f)) {
            if (navRail != null) {
                navRail()
            }
            if (side != null) {
                PaneLayout(
                    modifier = Modifier.weight(1f),
                    main = main,
                    side = side
                )
            } else {
                Box(Modifier.weight(1f)) { main() }
            }
        }
    }
}
