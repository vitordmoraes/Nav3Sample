package com.example.nav3example.presentation.animation

import androidx.compose.animation.core.EaseInOutQuart
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.ui.NavDisplay
import com.example.nav3example.presentation.navigation.SplashRoute

object NavAnimations {
    fun longFadeAnimation(): Map<String, Any> {
        return NavDisplay.transitionSpec {
            fadeIn(
                animationSpec = tween(
                    durationMillis = 2000,
                    easing = EaseInOutQuart
                )
            ) togetherWith fadeOut(
                animationSpec = tween(2000)
            )
        }
    }

    fun loginFadeOrSlideAnimation(backStack: SnapshotStateList<Any>): Map<String, Any> {
        val lastRoute = backStack.firstOrNull()
        return NavDisplay.transitionSpec {
            if (lastRoute is SplashRoute) {
                fadeIn(
                    animationSpec = tween(600, easing = EaseInOutQuart)
                ) togetherWith fadeOut(
                    animationSpec = tween(600, easing = EaseInOutQuart)
                )
            } else {
                slideInVertically(
                    initialOffsetY = { -it },
                    animationSpec = tween(800, easing = EaseInOutQuart)
                ) togetherWith slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(800, easing = EaseInOutQuart)
                )
            }
        } + NavDisplay.popTransitionSpec {
            slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(800, easing = EaseInOutQuart)
            ) togetherWith slideOutVertically(
                targetOffsetY = { -it },
                animationSpec = tween(800, easing = EaseInOutQuart)
            )
        }
    }

    fun homeFadeOrSlideAnimation(backStack: SnapshotStateList<Any>): Map<String, Any> {
        val lastRoute = backStack.firstOrNull()

        return NavDisplay.transitionSpec {
            if (lastRoute is SplashRoute) {
                fadeIn(
                    animationSpec = tween(600, easing = EaseInOutQuart)
                ) togetherWith fadeOut(
                    animationSpec = tween(600, easing = EaseInOutQuart)
                )
            } else {
                slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(800, easing = EaseInOutQuart)
                ) togetherWith slideOutVertically(
                    targetOffsetY = { -it },
                    animationSpec = tween(800, easing = EaseInOutQuart)
                )
            }
        }
    }
}