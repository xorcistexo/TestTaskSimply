package su.lalayan.testtasksimply.ui.bottom_navigation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import su.lalayan.testtasksimply.R
import su.lalayan.testtasksimply.ui.elements.CustomDialog
import su.lalayan.testtasksimply.ui.elements.DoorButton
import su.lalayan.testtasksimply.ui.state.UIState
import su.lalayan.testtasksimply.ui.theme.Gray300
import su.lalayan.testtasksimply.ui.theme.Orange900
import su.lalayan.testtasksimply.ui.theme.White

@Composable
fun HomeView(viewModel: HomeViewModel = viewModel(), scaffoldState: ScaffoldState) {
    val eventEmitter = HomeEventEmitter(viewModel)
    val state = viewModel.uiState.value

    val carModel = state.carName
    val millage = state.millage
    val isDoorsLocked = state.isDoorsLocked
    val isDialogShown = state.showDialog
    val isSnackBarShown = state.isSnackBarShown

    Scaffold(modifier = Modifier, scaffoldState = scaffoldState) {
        ShowDialog(
            isDialogShown = isDialogShown,
            carModel = carModel,
            isDoorsLocked = isDoorsLocked,
            eventEmitter = eventEmitter
        )

        ShowSnackBar(
            scaffoldState = scaffoldState,
            isSnackBarShown = isSnackBarShown,
            doorsCurrentState = isDoorsLocked
        )

        Headline(millage = millage, carModel = carModel)
        MyCarView(state = state, eventEmitter = eventEmitter)
    }
}

@Composable
fun Headline(modifier: Modifier = Modifier, millage: Int, carModel: String) {
    Column(
        modifier = modifier.padding(top = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeadlineRow(carModel = carModel, millage = millage)
    }
}

@Composable
fun HeadlineRow(modifier: Modifier = Modifier, carModel: String, millage: Int) {
    Row(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        HeadlineCarModel(carModel = carModel)
        DividerView()
        HeadlineMillage(millage = millage)
    }
}

@Composable
fun HeadlineCarModel(modifier: Modifier = Modifier, carModel: String) {
    Text(
        modifier = modifier.padding(horizontal = 4.dp),
        text = "${LocalContext.current.getString(R.string.label_my)} $carModel",
        fontSize = 16.sp
    )
}

@Composable
fun HeadlineMillage(millage: Int) {
    Image(
        painter = painterResource(id = R.drawable.ic_gas),
        contentDescription = R.drawable.ic_gas.toString()
    )
    Text(
        text = "$millage ${LocalContext.current.getString(R.string.label_miles)}",
        fontSize = 16.sp
    )
}

@Composable
fun DividerView(modifier: Modifier = Modifier) {
    Divider(
        color = Color.Gray,
        modifier = modifier
            .fillMaxHeight()
            .width(1.dp)
    )
}

@Composable
fun MyCarView(modifier: Modifier = Modifier, eventEmitter: HomeEventEmitter, state: UIState) {
    Column(
        modifier = modifier
            .padding(top = 64.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Gray300, White, Gray300
                    )
                )
            )
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        MyCarViewBox(updatedTime = state.updatedTime)
        DoorsAndEngineRow(eventEmitter = eventEmitter, state = state)
    }
}

@Composable
fun MyCarViewBox(modifier: Modifier = Modifier, updatedTime: Int) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        MyCarImage()
        UpdatedText(modifier.align(Alignment.TopCenter), updatedTime = updatedTime)
    }
}

@Composable
fun MyCarImage() {
    Image(
        painter = painterResource(id = R.drawable.infinity_qx55),
        contentDescription = R.drawable.infinity_qx55.toString()
    )
}

@Composable
fun UpdatedText(modifier: Modifier = Modifier, updatedTime: Int) {
    Row(
        modifier = modifier.padding(vertical = 10.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_refresh),
            contentDescription = R.drawable.ic_refresh.toString()
        )
        Text(
            text = String.format(
                LocalContext.current.getString(
                    R.string.label_updated_time,
                    updatedTime.toString()
                )
            )
        )
    }
}

@Composable
fun DoorsAndEngineRow(
    modifier: Modifier = Modifier,
    eventEmitter: HomeEventEmitter,
    state: UIState
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Doors(eventEmitter = eventEmitter, state = state)
        Engine()
    }
}

@Composable
fun Doors(modifier: Modifier = Modifier, state: UIState, eventEmitter: HomeEventEmitter) {
    Column(modifier = modifier) {
        DoorsHeadline(state = state)
        DoorsLockedUnlockedRow(state = state, eventEmitter = eventEmitter)
    }
}

@Composable
fun DoorsHeadline(modifier: Modifier = Modifier, state: UIState) {
    Row(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .width(IntrinsicSize.Min)
            .padding(bottom = 4.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .align(Alignment.CenterVertically),
            text = LocalContext.current.getString(R.string.label_doors),
            fontWeight = FontWeight.Bold
        )
        Divider(
            color = Color.Gray,
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
        )
        Text(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .align(Alignment.CenterVertically),
            text = when {
                state.isLockedProgressShown || state.isUnlockedProgressShown -> {
                    LocalContext.current.getString(R.string.label_wait)
                }
                else -> when {
                    state.isDoorsLocked -> LocalContext.current.getString(R.string.label_locked)
                    else -> LocalContext.current.getString(R.string.label_unlocked)
                }
            },
            color = Color.Gray,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun DoorsLockedUnlockedRow(
    modifier: Modifier = Modifier,
    state: UIState,
    eventEmitter: HomeEventEmitter
) {
    Row(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .width(IntrinsicSize.Min)
            .background(Color.White)
            .padding(top = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        DoorsLocked(state = state, eventEmitter = eventEmitter)
        DoorsUnLocked(state = state, eventEmitter = eventEmitter)
    }
}

@Composable
fun DoorsLocked(
    state: UIState,
    eventEmitter: HomeEventEmitter
) {
    if (state.isUnlockedProgressShown) {
        ProgressView(eventEmitter = eventEmitter)
    } else {
        DoorButton(
            icon = R.drawable.ic_lock,
            backgroundColor = if (state.isDoorsLocked) Orange900 else Color.Black,
            onClick = {
                eventEmitter.onLockedClicked()
            }
        )
    }
}

@Composable
fun DoorsUnLocked(
    state: UIState,
    eventEmitter: HomeEventEmitter
) {
    if (state.isLockedProgressShown) {
        ProgressView(eventEmitter = eventEmitter)
    } else {
        DoorButton(
            icon = R.drawable.ic_unlock,
            backgroundColor = if (!state.isDoorsLocked) Orange900 else Color.Black,
            onClick = {
                eventEmitter.onUnLockedClicked()
            }
        )
    }
}

@Composable
fun ProgressView(modifier: Modifier = Modifier, eventEmitter: HomeEventEmitter) {
    CircularProgressIndicator(
        modifier = modifier
            .fillMaxHeight()
            .size(64.dp)
            .wrapContentSize(Alignment.Center),
        color = Color.LightGray,
        strokeWidth = 1.dp
    )
    LaunchedEffect(Unit) {
        delay(5000L)
        eventEmitter.onProgressDone()
    }
}

@Composable
fun ShowSnackBar(
    scaffoldState: ScaffoldState,
    isSnackBarShown: Boolean,
    doorsCurrentState: Boolean
) {
    if (isSnackBarShown) {
        val uiMessage =
            if (doorsCurrentState) {
                "${LocalContext.current.getString(R.string.label_doors)} ${
                    LocalContext.current.getString(
                        R.string.label_unlocked
                    )
                }"
            } else {
                "${LocalContext.current.getString(R.string.label_doors)} ${
                    LocalContext.current.getString(
                        R.string.label_locked
                    )
                }"
            }
        LaunchedEffect(uiMessage) {
            scaffoldState.snackbarHostState.showSnackbar(uiMessage)
        }
    }
}

@Composable
fun ShowDialog(
    isDialogShown: Boolean,
    carModel: String,
    isDoorsLocked: Boolean,
    eventEmitter: HomeEventEmitter
) {
    if (isDialogShown) {
        CustomDialog(
            myCar = carModel,
            isDoorsLocked = isDoorsLocked,
            onDismiss = {
                eventEmitter.onDialogDismiss()
            },
            onNegativeClick = {
                eventEmitter.onDialogDismiss()
            },
            onPositiveClick = {
                eventEmitter.onDialogPositiveClicked(isDoorsLocked)
            }
        )
    }
}

//TODO separate views
@Composable
fun Engine(modifier: Modifier = Modifier) {
    Column(modifier.padding(top = 4.dp)) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .width(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .align(Alignment.CenterVertically),
                text = LocalContext.current.getString(R.string.label_engine),
                fontWeight = FontWeight.Bold
            )
        }
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .width(IntrinsicSize.Min)
                .background(Color.White),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .width(IntrinsicSize.Min)
                    .wrapContentSize(Alignment.Center)
                    .padding(9.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(Color.Black),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier.padding(4.dp),
                        text = LocalContext.current.getString(R.string.action_engine_start)
                            .toUpperCase(Locale.current),
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
            }
            Column(
                modifier = Modifier
                    .width(IntrinsicSize.Min)
                    .wrapContentSize(Alignment.Center)
                    .padding(9.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(Color.Black),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier.padding(4.dp),
                        text = LocalContext.current.getString(R.string.action_engine_stop)
                            .toUpperCase(Locale.current),
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}
