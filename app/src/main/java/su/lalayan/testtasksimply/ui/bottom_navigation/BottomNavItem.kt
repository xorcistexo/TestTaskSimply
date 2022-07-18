package su.lalayan.testtasksimply.ui.bottom_navigation

import su.lalayan.testtasksimply.R

sealed class BottomNavItem(var title: String, var icon: Int, var screen_route: String) {
    object Home : BottomNavItem("Home", R.drawable.ic_home, "home")
    object Vehicle : BottomNavItem("Vehicle", R.drawable.ic_car, "vehicle")
    object Location : BottomNavItem("Location", R.drawable.ic_map_marker, "location")
    object More : BottomNavItem("More", R.drawable.ic_dots_horizontal, "more")
}