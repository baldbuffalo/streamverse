package com.streamverse.app.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

/**
 * True on phone-sized windows (any orientation), false on TV/tablet-sized
 * windows. 600dp is the standard Material breakpoint between "compact" and
 * "medium" window width size classes.
 */
@Composable
fun isCompactWidth(): Boolean = LocalConfiguration.current.screenWidthDp < 600
