package su.lalayan.testtasksimply.ui.bottom_navigation.screens.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import su.lalayan.testtasksimply.ui.event.UIEvent
import su.lalayan.testtasksimply.ui.state.UIState

class HomeViewModel : ViewModel() {

    private var _uiState = mutableStateOf(
        UIState(
            carName = "QX55",
            millage = 150,
            updatedTime = 1,
            isDoorsLocked = true
        )
    )
    val uiState: State<UIState> = _uiState

    fun onEvent(event: UIEvent) = when (event) {
        is UIEvent.LockedClick -> {
            _uiState.value = _uiState.value.copy(
                showDialog = !_uiState.value.showDialog
            )
        }
        is UIEvent.UnLockedClick -> {
            _uiState.value = _uiState.value.copy(
                showDialog = !_uiState.value.showDialog
            )
        }
        is UIEvent.DialogPositiveClick -> {
            when {
                event.currentDoorsState -> {
                    _uiState.value = _uiState.value.copy(
                        isLockedProgressShown = true,
                        showDialog = !_uiState.value.showDialog
                    )
                }
                else -> _uiState.value = _uiState.value.copy(
                    isUnlockedProgressShown = true,
                    showDialog = !_uiState.value.showDialog
                )
            }
        }
        is UIEvent.DialogDismiss -> {
            _uiState.value = _uiState.value.copy(
                showDialog = !_uiState.value.showDialog
            )
        }
        is UIEvent.ProgressDone -> {
            _uiState.value = _uiState.value.copy(
                isLockedProgressShown = false,
                isUnlockedProgressShown = false,
                isSnackBarShown = true,
                isDoorsLocked = !_uiState.value.isDoorsLocked,
            )
        }

        is UIEvent.SnackBarDismiss -> {
            _uiState.value = _uiState.value.copy(
                isSnackBarShown = !_uiState.value.isSnackBarShown
            )
        }
    }
}