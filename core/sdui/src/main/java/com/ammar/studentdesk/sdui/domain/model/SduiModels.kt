package com.ammar.studentdesk.sdui.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.io.Serial

@Serializable
sealed class SduiAction

@Serializable
@SerialName("navigation_action")
data class NavigationAction(
    val destination: String,
    val params: Map<String, String>? = null
) : SduiAction()

@Serializable
@SerialName("api_action")
data class ApiAction(
    val endpoint: String,
    val method: String,
    val body: Map<String, String>? = null
) : SduiAction()

@Serializable
@SerialName("logout_action")
object LogoutAction : SduiAction()

@Serializable
@SerialName("submit_form_action")
data class SubmitFormAction(
    @SerialName("form_id")
    val formId: String
) : SduiAction()

@Serializable
@SerialName("show_dialog_action")
data class ShowDialogAction(
    val dialog: SduiDialog
) : SduiAction()

@Serializable
@SerialName("dialog")
data class SduiDialog(
    val title: String,
    val message: String,
    @SerialName("confirm_text")
    val confirmText: String,
    @SerialName("confirm_action")
    val confirmAction: SduiAction,
    @SerialName("cancel_text")
    val cancelText: String,
    @SerialName("cancel_action")
    val cancelAction: SduiAction? = null
) : SduiComponent()


@Serializable
sealed class SduiComponent

@Serializable
data class SduiAppBar(
    val title: String,
    @SerialName("show_profile_icon")
    val showProfileIcon: Boolean = false,
    @SerialName("show_notification_icon")
    val showNotificationIcon: Boolean = false,
    @SerialName("notification_count")
    val notificationCount: Int = 0,
    @SerialName("show_logout_icon")
    val showLogoutIcon: Boolean = false
)

@Serializable
@SerialName("screen")
data class SduiScreen(
    @SerialName("screen_id")
    val screenId: String,
    @SerialName("app_bar")
    val toolbar: SduiAppBar? = null,
    val body: SduiComponent
) : SduiComponent()


@Serializable
@SerialName("column")
data class SduiColumn(
    val children: List<SduiComponent>
) : SduiComponent()

@Serializable
@SerialName("horizontal_list")
data class SduiHorizontalList(
    val children: List<SduiComponent>
) : SduiComponent()


@Serializable
@SerialName("section_header")
data class SduiSectionHeader(
    val title: String,
) : SduiComponent()

@Serializable
@SerialName("info_card")
data class SduiInfoCard(
    val title: String,
    val description: String,
    @SerialName("backgroundColor")
    val backgroundColor: String? = null,
    val action: SduiAction? = null
) : SduiComponent()

@Serializable
@SerialName("info_card_profile")
data class SduiInfoCardProfile(
    val description: String,
    val action: SduiAction? = null
) : SduiComponent()

@Serializable
@SerialName("info_card_profile_circular_round_image")
data class SduiInfoCardProfileCircularRound(
    @SerialName("image_url")
    val imageUrl : String? = null,
    val title: String
) : SduiComponent()

@Serializable
@SerialName("info_card_profile_detailed")
data class SduiCardProfileDetailed(
    val title: String,
    val subtitle: String
) : SduiComponent()

@Serializable
@SerialName("schedule_card")
data class SduiScheduleCard(
    @SerialName("course_name")
    val courseName: String,
    val time: String,
    val room: String,
    val lecturer: String? = null,
    val action: SduiAction? = null
) : SduiComponent()

@Serializable
@SerialName("warning_banner")
data class SduiWarningBanner(
    val title: String,
    val description: String,
    val id: String? = null,
    val action: SduiAction? = null
) : SduiComponent()

@Serializable
@SerialName("empty_state_card")
data class SduiEmptyStateCard(
    val message: String
) : SduiComponent()

@Serializable
@SerialName("carousel")
data class SduiCarousel(
    val items: List<SduiCarouselItem>
) : SduiComponent()

@Serializable
data class SduiCarouselItem(
    val url: String,
    val action: SduiAction? = null
)

@Serializable
@SerialName("form_container")
data class SduiFormContainer(
    @SerialName("form_id")
    val formId: String,
    @SerialName("submit_action")
    val submitAction: SduiAction? = null,
    val children: List<SduiComponent>
) : SduiComponent()

@Serializable
@SerialName("text_input")
data class SduiTextInput(
    @SerialName("field_name")
    val fieldName: String,
    val label: String,
    val placeholder: String? = null
) : SduiComponent()

@Serializable
@SerialName("dropdown")
data class SduiDropdown(
    @SerialName("field_name")
    val fieldName: String,
    val label: String,
    val options: List<DropdownOption>
) : SduiComponent()

@Serializable
data class DropdownOption(
    val id: String,
    val label: String
)

@Serializable
@SerialName("button")
data class SduiButton(
    val text: String,
    val action: SduiAction? = null
) : SduiComponent()

@Serializable
@SerialName("spacer")
data class SduiSpacer(
    val size : String = "medium"
): SduiComponent()