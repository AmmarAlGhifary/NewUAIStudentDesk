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
import com.ammar.studentdesk.sdui.domain.model.*
import com.ammar.studentdesk.sdui.presentation.components.*

@Composable
fun UiComponentRenderer(
    component: SduiComponent,
    modifier: Modifier = Modifier,
    onAction: (SduiAction) -> Unit = {}
) {
    val modifier = component.modifier?.toComposeModifier(modifier) ?: modifier

    when (component) {
        is SduiScreen -> SduiScreenComponent(component, modifier, onAction)
        is SduiColumn -> SduiColumnComponent(component, modifier, onAction)
        is SduiHorizontalList -> SduiHorizontalListComponent(component, modifier, onAction)

        is SduiSectionHeader -> SduiSectionHeaderComponent(component, modifier)
        is SduiInfoCard -> SduiInfoCardComponent(component, modifier, onAction)
        is SduiWarningBanner -> SduiWarningBannerComponent(component, modifier, onAction)
        is SduiScheduleCard -> SduiScheduleCardComponent(component, modifier)
        is SduiEmptyStateCard -> SduiEmptyStateCardComponent(component, modifier)
        is SduiCarousel -> SduiCarouselComponent(component, modifier, onAction)

        is SduiInfoCardProfileCircularRound -> SduiInfoCardProfileCircularRoundComponent(component, modifier)
        is SduiInfoCardProfile -> SduiInfoCardProfileComponent(component, modifier, onAction)
        is SduiCardProfileDetailed -> SduiCardProfileDetailedComponent(component, modifier)

        is SduiTabLayout -> SduiTabLayoutComponent(component, modifier, onAction)
        is SduiHistoryCard -> SduiHistoryCardComponent(component, modifier, onAction)
        is SduiAccordion -> SduiAccordionComponent(component, modifier, onAction)

        is SduiDropdown -> SduiDropdownComponent(component, modifier)
        is SduiDropdownInput -> SduiDropdownInputComponent(component, modifier)

        is SduiTextInput -> SduiTextInputComponent(component, modifier)
        is SduiTextAreaInput -> SduiTextAreaInputComponent(component, modifier)

        is SduiFormContainer -> SduiFormContainerComponent(component, modifier, onAction)
        is SduiButton -> SduiButtonComponent(component, modifier, onAction)
        is SduiSpacer -> SduiSpacerComponent(component)
        is SduiHtmlText -> SduiHtmlTextComponent(component, modifier)
        is SduiImageUploadInput -> SduiImageUploadInputComponent(component, modifier)
        is SduiScoreCard -> SduiScoreCardComponent(component, modifier, onAction)

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
