package su.lalayan.testtasksimply.ui.state

data class UIState(
    val carName: String = "",
    val millage: Int = 0,
    val updatedTime: Int = 0,
    val isDoorsLocked: Boolean = true,
    val showDialog: Boolean = false,
    val isLockedProgressShown: Boolean = false,
    val isUnlockedProgressShown: Boolean = false,
    val isSnackBarShown: Boolean = false
)