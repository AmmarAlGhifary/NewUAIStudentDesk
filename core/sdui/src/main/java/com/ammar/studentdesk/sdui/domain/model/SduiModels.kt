package com.ammar.studentdesk.sdui.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class SduiAction

@Serializable
@SerialName("none")
object NoneAction : SduiAction()

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
    val formId: String,
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
    val cancelText: String? = null,
    @SerialName("cancel_action")
    val cancelAction: SduiAction? = null,
    override val modifier: SduiModifier? = null
) : SduiComponent()


@Serializable
sealed class SduiComponent {
    abstract val modifier: SduiModifier?
}

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
    val body: SduiComponent,
    override val modifier: SduiModifier? = null
) : SduiComponent()


@Serializable
@SerialName("column")
data class SduiColumn(
    val children: List<SduiComponent>,
    val arrangement: String? = null,
    val alignment: String? = null,
    override val modifier: SduiModifier? = null
) : SduiComponent()

@Serializable
@SerialName("horizontal_list")
data class SduiHorizontalList(
    val children: List<SduiComponent>,
    val arrangement: String? = null,
    val alignment: String? = null,
    override val modifier: SduiModifier? = null
) : SduiComponent()

@Serializable
@SerialName("section_header")
data class SduiSectionHeader(
    val title: String,
    override val modifier: SduiModifier? = null
) : SduiComponent()

@Serializable
@SerialName("info_card")
data class SduiInfoCard(
    val title: String,
    val description: String,
    val action: SduiAction? = null,
    override val modifier: SduiModifier? = null
) : SduiComponent()

@Serializable
@SerialName("info_card_profile")
data class SduiInfoCardProfile(
    val description: String,
    val action: SduiAction? = null,
    override val modifier: SduiModifier? = null
) : SduiComponent()

@Serializable
@SerialName("info_card_profile_circular_round_image")
data class SduiInfoCardProfileCircularRound(
    @SerialName("image_url")
    val imageUrl : String? = null,
    val title: String,
    @SerialName("image_modifier") val imageModifier: SduiModifier? = null,
    @SerialName("title_modifier") val titleModifier: SduiModifier? = null,
    override val modifier: SduiModifier? = null
) : SduiComponent()

@Serializable
@SerialName("info_card_profile_detailed")
data class SduiCardProfileDetailed(
    val title: String,
    val subtitle: String,
    override val modifier: SduiModifier? = null
) : SduiComponent()

@Serializable
@SerialName("schedule_card")
data class SduiScheduleCard(
    @SerialName("course_name")
    val courseName: String,
    val time: String,
    val room: String,
    val lecturer: String? = null,
    val action: SduiAction? = null,
    override val modifier: SduiModifier? = null
) : SduiComponent()

@Serializable
@SerialName("warning_banner")
data class SduiWarningBanner(
    val title: String,
    val description: String,
    val action: SduiAction? = null,
    override val modifier: SduiModifier? = null
) : SduiComponent()

@Serializable
@SerialName("empty_state_card")
data class SduiEmptyStateCard(
    val message: String,
    override val modifier: SduiModifier? = null
) : SduiComponent()

@Serializable
@SerialName("carousel")
data class SduiCarousel(
    val items: List<SduiCarouselItem>,
    override val modifier: SduiModifier? = null
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
    val children: List<SduiComponent>,
    override val modifier: SduiModifier? = null
) : SduiComponent()


@Serializable
data class SduiTab(
    val title: String,
    val children: List<SduiComponent>
)

@Serializable
@SerialName("tab_layout")
data class SduiTabLayout(
    val tabs: List<SduiTab>,
    override val modifier: SduiModifier? = null
) : SduiComponent()


@Serializable
@SerialName("history_card")
data class SduiHistoryCard(
    val title: String,
    @SerialName("date_text") val dateText: String,
    @SerialName("prodi_text") val prodiText: String,
    @SerialName("akademik_text") val akademikText: String,
    @SerialName("ready_text") val readyText: String,
    @SerialName("can_cancel") val canCancel: Boolean = false,
    @SerialName("cancel_action") val cancelAction: SduiAction? = null,
    @SerialName("can_download") val canDownload: Boolean = false,
    @SerialName("download_action") val downloadAction: SduiAction? = null,
    override val modifier: SduiModifier? = null
) : SduiComponent()

@Serializable
@SerialName("score_card")
data class SduiScoreCard(
    val title: String,
    @SerialName("date_text") val dateText: String,
    @SerialName("score_text") val scoreText: String? = null,
    @SerialName("status_text") val statusText: String? = null,
    @SerialName("status_color") val statusColor: String? = null,
    val action: SduiAction? = null,
    override val modifier: SduiModifier? = null
) : SduiComponent()

@Serializable
@SerialName("accordion")
data class SduiAccordion(
    val title: String,
    @SerialName("is_expanded") val isExpanded: Boolean = false,
    @SerialName("header_modifier") val headerModifier: SduiModifier? = null,  // Untuk styling row w
    @SerialName("content_modifier") val contentModifier: SduiModifier? = null, // Untuk styling the children wrapper
    val children: List<SduiComponent>,
    override val modifier: SduiModifier? = null // Untuk styling box
) : SduiComponent()
@Serializable
@SerialName("dropdown_input")
data class SduiDropdownInput(
    val id: String,
    val label: String,
    val options: List<String>,
    override val modifier: SduiModifier? = null
) : SduiComponent()


@Serializable
@SerialName("text_area_input")
data class SduiTextAreaInput(
    val id: String,
    val label: String,
    val placeholder: String? = null,
    override val modifier: SduiModifier? = null
) : SduiComponent()


@Serializable
@SerialName("text_input")
data class SduiTextInput(
    @SerialName("field_name")
    val fieldName: String,
    val label: String,
    val placeholder: String? = null,
    override val modifier: SduiModifier? = null
) : SduiComponent()

@Serializable
@SerialName("dropdown")
data class SduiDropdown(
    @SerialName("field_name")
    val fieldName: String,
    val label: String,
    val options: List<DropdownOption>,
    override val modifier: SduiModifier? = null
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
    val action: SduiAction? = null,
    override val modifier: SduiModifier? = null
) : SduiComponent()

@Serializable
@SerialName("spacer")
data class SduiSpacer(
    val size : String = "medium",
    override val modifier: SduiModifier? = null
): SduiComponent()

@Serializable
@SerialName("html_text")
data class SduiHtmlText(
    val html: String,
    override val modifier: SduiModifier? = null
) : SduiComponent()

@Serializable
@SerialName("image_upload_input")
data class SduiImageUploadInput(
    val name: String,
    val label: String,
    val hint: String? = null,
    override val modifier: SduiModifier? = null
) : SduiComponent()

@Serializable
data class SduiModifier(
    val margin: SduiPadding? = null,
    val padding: SduiPadding? = null,
    val width: SduiSize? = null,
    val height: SduiSize? = null,
    val alignment: String? = null,
    val weight: Float? = null,
    val elevation: Int? = null,
    @SerialName("background_color") val backgroundColor: String? = null,
    @SerialName("corner_radius") val cornerRadius: Int? = null,
    @SerialName("border_width") val borderWidth: Int? = null,
    @SerialName("border_color") val borderColor: String? = null
)

@Serializable
data class SduiPadding(
    val all: Int? = null,
    val horizontal: Int? = null,
    val vertical: Int? = null,
    val start: Int? = null,
    val top: Int? = null,
    val end: Int? = null,
    val bottom: Int? = null
)

@Serializable
data class SduiSize(
    val type: String,
    val value: Int? = null
)
