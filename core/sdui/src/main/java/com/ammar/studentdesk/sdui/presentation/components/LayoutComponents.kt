package com.ammar.studentdesk.sdui.presentation.components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ammar.studentdesk.sdui.domain.model.*
import com.ammar.studentdesk.sdui.presentation.registry.UiComponentRenderer

val LocalFormState = compositionLocalOf<MutableMap<String, String>> { error("No form state provided") }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SduiScreenComponent(
    model: SduiScreen,
    modifier: Modifier = Modifier,
    onAction: (SduiAction) -> Unit
) {
    var currentDialog by remember { mutableStateOf<SduiDialog?>(null) }

    currentDialog?.let { dialog ->
        SduiActiveDialog(
            dialog = dialog,
            onDismiss = { },
            onAction = {
                onAction(it)
            }
        )
    }

    val screenActionHandler: (SduiAction) -> Unit = { action ->
        if (action is ShowDialogAction) {
            action.dialog
        } else {
            onAction(action)
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            model.toolbar?.let { toolbarComponent ->
                SduiTopAppBar(
                    toolbarComponent = toolbarComponent,
                    onAction = screenActionHandler
                )
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            UiComponentRenderer(component = model.body, onAction = screenActionHandler)
        }
    }
}


@Composable
private fun SduiActiveDialog(
    dialog: SduiDialog,
    onDismiss: () -> Unit,
    onAction: (SduiAction) -> Unit
) {
    Log.d("SDUI_ACTION", "Drawing SDUI Screen for: ${dialog.title}")
    SduiDialogComponent(
        model = dialog,
        onDismissRequest = {
            Log.d("SDUI_ACTION", "Dialog dismissed")
            onDismiss()
        },
        onAction = { action ->
            Log.d("SDUI_ACTION", "Dialog action fired")
            onAction(action)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SduiTopAppBar(
    toolbarComponent: SduiAppBar,
    onAction: (SduiAction) -> Unit
) {
    TopAppBar(
        expandedHeight = 30.dp,
        title = { Text(text = toolbarComponent.title, style = typography.titleLarge, fontWeight = FontWeight.Bold) },
        actions = {
            if (toolbarComponent.showProfileIcon) {
                IconButton(onClick = {}) { Icon(imageVector = Icons.Default.Person, contentDescription = "Profile") }
            }
            if (toolbarComponent.showNotificationIcon) {
                IconButton(onClick = { onAction(NavigationAction("pengumuman")) }) {
                    if (toolbarComponent.notificationCount > 0) {
                        BadgedBox(badge = { Badge { Text(toolbarComponent.notificationCount.toString()) } }) {
                            Icon(imageVector = Icons.Default.Notifications, contentDescription = "Notifications")
                        }
                    } else {
                        Icon(imageVector = Icons.Default.Notifications, contentDescription = "Notifications")
                    }
                }
            }
            if (toolbarComponent.showLogoutIcon) {
                IconButton(onClick = {
                    onAction(
                        ShowDialogAction(
                            dialog = SduiDialog(
                                title = "Keluar",
                                message = "Anda yakin ingin keluar ?",
                                confirmText = "Ya",
                                confirmAction = LogoutAction,
                                cancelText = "Tidak"
                            )
                        )
                    )
                }) { Icon(imageVector = Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Logout") }
            }
        }
    )
}

@Composable
fun SduiColumnComponent(
    model: SduiColumn,
    modifier: Modifier = Modifier,
    onAction: (SduiAction) -> Unit
) {
    val verticalArrangement = when (model.arrangement) {
        "center" -> Arrangement.Center
        "space_between" -> Arrangement.SpaceBetween
        "space_around" -> Arrangement.SpaceAround
        "space_evenly" -> Arrangement.SpaceEvenly
        "bottom" -> Arrangement.Bottom
        else -> Arrangement.Top
    }

    val horizontalAlignment = when (model.alignment) {
        "center" -> Alignment.CenterHorizontally
        "end" -> Alignment.End
        else -> Alignment.Start
    }

    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment
    ) {
        model.children.forEach { child ->
            UiComponentRenderer(component = child, onAction = onAction)
        }
    }
}

@Composable
fun SduiHorizontalListComponent(
    model: SduiHorizontalList,
    modifier: Modifier = Modifier,
    onAction: (SduiAction) -> Unit
) {
    LazyRow(modifier = modifier) {
        items(model.children) { child ->
            UiComponentRenderer(component = child, onAction = onAction)
        }
    }
}

@Composable
fun SduiFormContainerComponent(
    model: SduiFormContainer,
    modifier: Modifier = Modifier,
    onAction: (SduiAction) -> Unit
) {
    val formState = remember { mutableStateMapOf<String, String>() }
    
    CompositionLocalProvider(LocalFormState provides formState) {
        Column(modifier = modifier) {
            model.children.forEach { child ->
                UiComponentRenderer(component = child, onAction = onAction)
            }
        }
    }
}