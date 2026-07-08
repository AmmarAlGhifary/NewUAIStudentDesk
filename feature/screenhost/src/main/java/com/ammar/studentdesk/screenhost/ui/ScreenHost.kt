package com.ammar.studentdesk.screenhost.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ammar.studentdesk.screenhost.state.ScreenHostUiState
import com.ammar.studentdesk.screenhost.viewmodel.ScreenHostViewModel
import com.ammar.studentdesk.sdui.domain.model.LogoutAction
import com.ammar.studentdesk.sdui.domain.model.NavigationAction
import com.ammar.studentdesk.sdui.domain.model.SduiEmptyStateCard
import com.ammar.studentdesk.sdui.domain.model.SubmitFormAction
import com.ammar.studentdesk.sdui.presentation.components.SduiEmptyStateCardComponent
import com.ammar.studentdesk.sdui.presentation.registry.UiComponentRenderer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenHost(
    screenId: String,
    modifier: Modifier = Modifier,
    viewModel: ScreenHostViewModel = hiltViewModel(),
    onNavigate : (String) -> Unit,
    onLogout: () -> Unit = {}
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    LaunchedEffect(screenId) {
        viewModel.fetchScreen(screenId)
    }

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        modifier = modifier.fillMaxSize(),
        onRefresh = { viewModel.refreshScreen()},
    ) {
        when (val state = uiState) {
            is ScreenHostUiState.Loading -> CircularProgressIndicator(
                modifier = Modifier.align(Center)
            )
            is ScreenHostUiState.Error -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Center
            ) {
                SduiEmptyStateCardComponent(
                    model = SduiEmptyStateCard(
                        message = "Terjadi Kesalahan:\n${state.message}"
                    )
                )
            }
            is ScreenHostUiState.Success -> UiComponentRenderer(
                component = state.screen,
                modifier = Modifier.fillMaxSize(),
                onAction = { action ->
                    Log.d("SDUI_ACTION", "Action triggered: $action")
                    when (action) {
                        is NavigationAction -> onNavigate(action.destination)
                        is LogoutAction ->
                            onLogout()
                        is SubmitFormAction -> {
                            Log.d("SDUI_ACTION", "SubmitFormAction triggered for formId: ${action.formId}")
                        }
                        else -> {}
                    }
                }
            )
        }
    }
}