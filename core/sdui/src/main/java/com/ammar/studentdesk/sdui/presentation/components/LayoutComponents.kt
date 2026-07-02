package com.ammar.studentdesk.sdui.presentation.components

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ammar.studentdesk.sdui.domain.model.LogoutAction
import com.ammar.studentdesk.sdui.domain.model.SduiAction
import com.ammar.studentdesk.sdui.domain.model.SduiColumn
import com.ammar.studentdesk.sdui.domain.model.SduiDialog
import com.ammar.studentdesk.sdui.domain.model.SduiFormContainer
import com.ammar.studentdesk.sdui.domain.model.SduiHorizontalList
import com.ammar.studentdesk.sdui.domain.model.SduiScreen
import com.ammar.studentdesk.sdui.domain.model.ShowDialogAction
import com.ammar.studentdesk.sdui.domain.model.SubmitFormAction
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
        Log.d("SDUI_ACTION", "Drawing SDUI Screen for: ${dialog.title}")
        SduiDialogComponent(
            model = dialog,
            onDismissRequest = { 
                Log.d("SDUI_ACTION", "Dialog dismissed")
                currentDialog = null 
            },
            onAction = { action ->
                Log.d("SDUI_ACTION", "Dialog action fired")
                currentDialog = null
                onAction(action)
            }
        )
    }

    val screenActionHandler: (SduiAction) -> Unit = { action ->
        if (action is ShowDialogAction) {
            Log.d("SDUI_ACTION", "Setting currentDialog state")
            currentDialog = action.dialog
        } else {
            onAction(action)
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            model.toolbar?.let { toolbarComponent ->
                TopAppBar(
                    expandedHeight = 30.dp,
                    title = { Text(text = toolbarComponent.title, style = typography.titleLarge, fontWeight = FontWeight.Bold,)},
                    actions = {
                        if (toolbarComponent.showProfileIcon) {
                            IconButton(onClick = {
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Profile"
                                )
                            }
                        }
                        if (toolbarComponent.showNotificationIcon) {
                            IconButton(onClick = {
                                Log.d("SDUI_ACTION", "Notification icon clicked")
                                screenActionHandler(com.ammar.studentdesk.sdui.domain.model.NavigationAction("pengumuman"))
                            }) {
                                if (toolbarComponent.notificationCount > 0) {
                                    BadgedBox(
                                        badge = {
                                            Badge {
                                                Text(toolbarComponent.notificationCount.toString())
                                            }
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Notifications,
                                            contentDescription = "Notifications"
                                        )
                                    }
                                } else {
                                    Icon(
                                        imageVector = Icons.Default.Notifications,
                                        contentDescription = "Notifications"
                                    )
                                }
                            }
                        }
                        if (toolbarComponent.showLogoutIcon) {
                            IconButton(onClick = {
                                Log.d("SDUI_ACTION", "Logout icon clicked, firing ShowDialogAction")
                                screenActionHandler(
                                    ShowDialogAction(
                                        dialog = SduiDialog(
                                            title = "Keluar",
                                            message = "Anda yakin ingin keluar ?",
                                            confirmText = "Ya",
                                            confirmAction = LogoutAction,
                                            cancelText = "Tidak",
                                            cancelAction = null
                                        )
                                    )
                                )
                            }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                    contentDescription = "Logout"
                                )
                            }
                        }
                    }
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
fun SduiColumnComponent(
    model: SduiColumn,
    modifier: Modifier = Modifier,
    onAction: (SduiAction) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
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
    LazyRow(
        modifier = modifier.fillMaxWidth()
    ) {
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
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            model.children.forEach { child ->
                UiComponentRenderer(component = child, onAction = { action ->
                    if (action is SubmitFormAction) {
                        onAction(action)
                    }
                })
            }
        }
    }
}

