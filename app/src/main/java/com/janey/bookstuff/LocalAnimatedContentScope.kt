package com.janey.bookstuff

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf

val LocalAnimatedContentScope = compositionLocalOf<AnimatedContentScope> {
    error("Must be provided first")
}

@Composable
fun AnimatedContentScope.ProvideAnimatedContentScope(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalAnimatedContentScope provides this,
        content = content
    )
}

@Composable
fun WithAnimatedContentScope(block: @Composable AnimatedContentScope.() -> Unit) {
    with(LocalAnimatedContentScope.current) {
        block()
    }
}