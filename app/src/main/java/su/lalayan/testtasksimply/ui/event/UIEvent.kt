package su.lalayan.testtasksimply.ui.event

sealed class UIEvent {
    object LockedClick : UIEvent()
    object UnLockedClick : UIEvent()
    object DialogDismiss : UIEvent()
    object ProgressDone : UIEvent()
    object SnackBarDismiss : UIEvent()
    data class DialogPositiveClick(val currentDoorsState: Boolean) : UIEvent()
}