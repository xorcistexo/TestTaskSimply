package su.lalayan.testtasksimply.ui.elements

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import su.lalayan.testtasksimply.R
import su.lalayan.testtasksimply.ui.theme.White

@Composable
fun CustomDialog(
    myCar: String,
    isDoorsLocked: Boolean,
    onDismiss: () -> Unit,
    onNegativeClick: () -> Unit,
    onPositiveClick: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        val context = LocalContext.current
        Card(
            elevation = 8.dp,
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = if (isDoorsLocked) {
                        "${context.getString(R.string.label_dialog_unlock_body)} \"$myCar\""
                    } else {
                        "${context.getString(R.string.label_dialog_lock_body)} \"$myCar\""
                    },
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(8.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = onNegativeClick) {
                        Text(text = context.getString(R.string.action_cancel))
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Button(
                        onClick = {
                            onPositiveClick()
                        }
                    ) {
                        Text(
                            text = if (isDoorsLocked) {
                                context.getString(R.string.action_unlock)
                            } else {
                                context.getString(R.string.action_lock)
                            },
                            color = White
                        )
                    }
                }
            }
        }
    }
}