package su.lalayan.testtasksimply.ui.bottom_navigation.screens.home

import su.lalayan.testtasksimply.ui.event.UIEvent
import su.lalayan.testtasksimply.ui.event.UIEvent.DialogDismiss

class HomeEventEmitter(private val viewModel: HomeViewModel) {

    fun onDialogDismiss() = viewModel.onEvent(DialogDismiss)

    fun onDialogPositiveClicked(currentDoorsState: Boolean) =
        viewModel.onEvent(UIEvent.DialogPositiveClick(currentDoorsState))

    fun onLockedClicked() = viewModel.onEvent(UIEvent.LockedClick)

    fun onUnLockedClicked() = viewModel.onEvent(UIEvent.UnLockedClick)

    fun onProgressDone() = viewModel.onEvent(UIEvent.ProgressDone)

    fun onSnackBarDismiss() = viewModel.onEvent(UIEvent.SnackBarDismiss)
}