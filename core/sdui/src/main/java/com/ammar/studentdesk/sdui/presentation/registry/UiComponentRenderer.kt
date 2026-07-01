package com.ammar.studentdesk.sdui.presentation.registry

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import com.ammar.studentdesk.sdui.domain.model.SduiAction
import com.ammar.studentdesk.sdui.domain.model.SduiColumn
import com.ammar.studentdesk.sdui.domain.model.SduiComponent
import com.ammar.studentdesk.sdui.domain.model.SduiEmptyStateCard
import com.ammar.studentdesk.sdui.domain.model.SduiHorizontalList
import com.ammar.studentdesk.sdui.domain.model.SduiInfoCard
import com.ammar.studentdesk.sdui.domain.model.SduiCarousel
import com.ammar.studentdesk.sdui.domain.model.SduiScheduleCard
import com.ammar.studentdesk.sdui.domain.model.SduiScreen
import com.ammar.studentdesk.sdui.domain.model.SduiSectionHeader
import com.ammar.studentdesk.sdui.domain.model.SduiWarningBanner
import com.ammar.studentdesk.sdui.domain.model.SduiFormContainer
import com.ammar.studentdesk.sdui.domain.model.SduiTextInput
import com.ammar.studentdesk.sdui.domain.model.SduiDropdown
import com.ammar.studentdesk.sdui.domain.model.SduiButton
import com.ammar.studentdesk.sdui.domain.model.SduiCardProfileDetailed
import com.ammar.studentdesk.sdui.domain.model.SduiInfoCardProfile
import com.ammar.studentdesk.sdui.domain.model.SduiInfoCardProfileCircularRound
import com.ammar.studentdesk.sdui.domain.model.SduiSpacer
import com.ammar.studentdesk.sdui.presentation.components.SduiColumnComponent
import com.ammar.studentdesk.sdui.presentation.components.SduiEmptyStateCardComponent
import com.ammar.studentdesk.sdui.presentation.components.SduiHorizontalListComponent
import com.ammar.studentdesk.sdui.presentation.components.SduiInfoCardComponent
import com.ammar.studentdesk.sdui.presentation.components.SduiScheduleCardComponent
import com.ammar.studentdesk.sdui.presentation.components.SduiScreenComponent
import com.ammar.studentdesk.sdui.presentation.components.SduiSectionHeaderComponent
import com.ammar.studentdesk.sdui.presentation.components.SduiWarningBannerComponent
import com.ammar.studentdesk.sdui.presentation.components.SduiCarouselComponent
import com.ammar.studentdesk.sdui.presentation.components.SduiFormContainerComponent
import com.ammar.studentdesk.sdui.presentation.components.SduiTextInputComponent
import com.ammar.studentdesk.sdui.presentation.components.SduiDropdownComponent
import com.ammar.studentdesk.sdui.presentation.components.SduiButtonComponent
import com.ammar.studentdesk.sdui.presentation.components.SduiCardProfileDetailedComponent
import com.ammar.studentdesk.sdui.presentation.components.SduiInfoCardProfileCircularRoundComponent
import com.ammar.studentdesk.sdui.presentation.components.SduiInfoCardProfileComponent
import com.ammar.studentdesk.sdui.presentation.components.SduiSpacerComponent

@Composable
fun UiComponentRenderer(
    component: SduiComponent,
    modifier: Modifier = Modifier,
    onAction: (SduiAction) -> Unit = {}
) {
    when (component) {
        is SduiScreen -> SduiScreenComponent(component, modifier, onAction)
        is SduiColumn -> SduiColumnComponent(component, modifier, onAction)
        is SduiHorizontalList -> SduiHorizontalListComponent(component, modifier, onAction)

        is SduiSectionHeader -> SduiSectionHeaderComponent(component, modifier)
        is SduiInfoCard -> SduiInfoCardComponent(component, modifier, onAction)
        is SduiWarningBanner -> SduiWarningBannerComponent(component, modifier, onAction)
        is SduiScheduleCard -> SduiScheduleCardComponent(component, modifier, onAction)
        is SduiEmptyStateCard -> SduiEmptyStateCardComponent(component, modifier)
        is SduiCarousel -> SduiCarouselComponent(component, onAction)

        is SduiInfoCardProfileCircularRound -> SduiInfoCardProfileCircularRoundComponent(component)
        is SduiInfoCardProfile -> SduiInfoCardProfileComponent(component, modifier, onAction)
        is SduiCardProfileDetailed -> SduiCardProfileDetailedComponent(component, modifier)

        is SduiFormContainer -> SduiFormContainerComponent(component, modifier, onAction)
        is SduiTextInput -> SduiTextInputComponent(component, modifier)
        is SduiDropdown -> SduiDropdownComponent(component, modifier)
        is SduiButton -> SduiButtonComponent(component, modifier, onAction)
        is SduiSpacer -> SduiSpacerComponent(component)

        else -> {
            Box(
                modifier = modifier
                    .padding(8.dp)
                    .background(Blue.copy(alpha = 0.1f))
            ) {
                Text(
                    text = "Unhandled Component: ${component::class.simpleName}",
                    color = White,
                    style = typography.bodySmall
                )
            }
        }
    }
}