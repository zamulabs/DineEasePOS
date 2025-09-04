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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.splitpane.ExperimentalSplitPaneApi
import org.jetbrains.compose.splitpane.HorizontalSplitPane
import org.jetbrains.compose.splitpane.SplitPaneScope
import org.jetbrains.compose.splitpane.rememberSplitPaneState
import java.awt.Cursor

/**
 * Utility: show horizontal resize cursor when hovering the splitter handle.
 */
private fun Modifier.cursorForHorizontalResize(): Modifier =
    pointerHoverIcon(PointerIcon(Cursor(Cursor.E_RESIZE_CURSOR)))

@OptIn(ExperimentalSplitPaneApi::class)
@Composable
fun PaneLayout(
    modifier: Modifier = Modifier,
    navRail: (@Composable () -> Unit)? = null,
    mainRatio: Float = 0.6f,
    sideRatio: Float = 0.25f,
    extraRatio: Float = 0.15f,
    minNavRail: Dp = 100.dp,   // ⬅️ resizable nav rail min width
    minMain: Dp = 240.dp,
    minSide: Dp = 320.dp,
    minExtra: Dp = 240.dp,
    main: (@Composable () -> Unit)? = null,
    side: (@Composable () -> Unit)? = null,
    extra: (@Composable () -> Unit)? = null
) {
    if (navRail == null) {
        // fallback to original logic (no rail)
        BoxWithConstraints(modifier.fillMaxSize()) {
            when {
                side == null -> {
                    PaneBox { main?.invoke() }
                }

                extra == null -> {
                    val splitState = rememberSplitPaneState(initialPositionPercentage = mainRatio)
                    HorizontalSplitPane(
                        modifier = Modifier.fillMaxSize(),
                        splitPaneState = splitState
                    ) {
                        first(minSize = minMain) { PaneBox { main?.invoke() } }
                        second(minSize = minSide) { PaneBox { side.invoke() } }
                        styledSplitter()
                    }
                }

                else -> {
                    val splitState1 = rememberSplitPaneState(initialPositionPercentage = mainRatio)
                    val splitState2 = rememberSplitPaneState(
                        initialPositionPercentage = sideRatio / (sideRatio + extraRatio)
                    )
                    HorizontalSplitPane(
                        modifier = Modifier.fillMaxSize(),
                        splitPaneState = splitState1
                    ) {
                        first(minSize = minMain) { PaneBox { main?.invoke() } }
                        second(minSize = minSide + minExtra) {
                            HorizontalSplitPane(
                                modifier = Modifier.fillMaxSize(),
                                splitPaneState = splitState2
                            ) {
                                first(minSize = minSide) { PaneBox { side.invoke() } }
                                second(minSize = minExtra) { PaneBox { extra.invoke() } }
                                styledSplitter()
                            }
                        }
                        styledSplitter()
                    }
                }
            }
        }
    } else {
        // --- Layout WITH NavRail ---
        val splitStateNav =
            rememberSplitPaneState(initialPositionPercentage = 0.15f) // rail ~15% by default
        HorizontalSplitPane(
            modifier = modifier.fillMaxSize(),
            splitPaneState = splitStateNav
        ) {
            // Left: NavRail (resizable)
            first(minSize = minNavRail) {
                PaneBox { navRail() }
            }

            // Right: normal PaneLayout (main + side + extra)
            second(minSize = minMain + minSide) {
                BoxWithConstraints(Modifier.fillMaxSize()) {
                    when {
                        side == null -> PaneBox { main?.invoke() }
                        extra == null -> {
                            val splitState =
                                rememberSplitPaneState(initialPositionPercentage = mainRatio)
                            HorizontalSplitPane(
                                modifier = Modifier.fillMaxSize(),
                                splitPaneState = splitState
                            ) {
                                first(minSize = minMain) { PaneBox { main?.invoke() } }
                                second(minSize = minSide) { PaneBox { side.invoke() } }
                                styledSplitter()
                            }
                        }

                        else -> {
                            val splitState1 =
                                rememberSplitPaneState(initialPositionPercentage = mainRatio)
                            val splitState2 = rememberSplitPaneState(
                                initialPositionPercentage = sideRatio / (sideRatio + extraRatio)
                            )
                            HorizontalSplitPane(
                                modifier = Modifier.fillMaxSize(),
                                splitPaneState = splitState1
                            ) {
                                first(minSize = minMain) { PaneBox { main?.invoke() } }
                                second(minSize = minSide + minExtra) {
                                    HorizontalSplitPane(
                                        modifier = Modifier.fillMaxSize(),
                                        splitPaneState = splitState2
                                    ) {
                                        first(minSize = minSide) { PaneBox { side.invoke() } }
                                        second(minSize = minExtra) { PaneBox { extra.invoke() } }
                                        styledSplitter()
                                    }
                                }
                                styledSplitter()
                            }
                        }
                    }
                }
            }

            styledSplitter()
        }
    }
}


@Composable
private fun PaneBox(content: @Composable () -> Unit) {
    val paneRadius = 12.dp
    Box(
        Modifier
            .fillMaxSize()
            .padding(4.dp)
            .clip(RoundedCornerShape(paneRadius))
            .background(Color(0xFF122118))
    ) { content() }
}

@OptIn(ExperimentalSplitPaneApi::class, ExperimentalComposeUiApi::class)
private fun SplitPaneScope.styledSplitter() {
    splitter {
        visiblePart {
            Box(
                Modifier
                    .width(1.dp)
                    .fillMaxHeight()
                    .background(Color(0xFF1B3124))
            )
        }
        handle {
            var isHovered by remember { mutableStateOf(false) }
            Box(
                Modifier
                    .markAsHandle()
                    .cursorForHorizontalResize()
                    .background(
                        SolidColor(if (isHovered) Color.LightGray else Color.Gray),
                        alpha = if (isHovered) 0.7f else 0.5f
                    )
                    .width(7.dp)
                    .fillMaxHeight()
                    .pointerMoveFilter(
                        onEnter = { isHovered = true; false },
                        onExit = { isHovered = false; false }
                    )
            )
        }
    }
}

/**
 * High-level scaffold for desktop apps. This variant accepts pane sizing
 * parameters and an optional third pane so it can forward them into PaneLayout.
 *
 * - mainRatio / sideRatio / extraRatio are fractions (0f..1f). They are normalized
 *   internally so callers don't need to sum to 1.0 exactly.
 * - If `extra` is null, PaneLayout renders a two-pane layout.
 */
@Composable
fun SplitScreenScaffold(
    modifier: Modifier = Modifier,
    navRail: (@Composable () -> Unit)? = null,
    topBar: (@Composable () -> Unit)? = null,
    bottomBar: (@Composable () -> Unit)? = null,
    mainRatio: Float = 0.6f,
    sideRatio: Float = 0.4f,
    extraRatio: Float = 0f,
    minMain: Dp = 240.dp,
    minSide: Dp = 320.dp,
    minExtra: Dp = 240.dp,
    minNavRail: Dp = 100.dp,   // ⬅️ resizable nav rail min width
    main: (@Composable () -> Unit)? = null,
    side: (@Composable () -> Unit)? = null,
    extra: (@Composable () -> Unit)? = null,
) {
    Column(modifier = modifier.fillMaxSize()) {
        topBar?.invoke()

        PaneLayout(
            modifier = Modifier.weight(1f),
            navRail = navRail,
            mainRatio = mainRatio,
            sideRatio = sideRatio,
            extraRatio = extraRatio,
            minMain = minMain,
            minSide = minSide,
            minExtra = minExtra,
            minNavRail = minNavRail,
            main = main,
            side = side,
            extra = extra
        )

        bottomBar?.invoke()
    }
}
