package com.ammar.studentdesk.sdui.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import android.widget.TextView
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import android.net.Uri
import androidx.compose.foundation.text.selection.SelectionContainer
import com.ammar.studentdesk.sdui.domain.model.SduiAccordion
import com.ammar.studentdesk.sdui.domain.model.SduiAction
import com.ammar.studentdesk.sdui.domain.model.SduiButton
import com.ammar.studentdesk.sdui.domain.model.SduiCardProfileDetailed
import com.ammar.studentdesk.sdui.domain.model.SduiCarousel
import com.ammar.studentdesk.sdui.domain.model.SduiDialog
import com.ammar.studentdesk.sdui.domain.model.SduiDropdown
import com.ammar.studentdesk.sdui.domain.model.SduiDropdownInput
import com.ammar.studentdesk.sdui.domain.model.SduiEmptyStateCard
import com.ammar.studentdesk.sdui.domain.model.SduiHistoryCard
import com.ammar.studentdesk.sdui.domain.model.SduiHtmlText
import com.ammar.studentdesk.sdui.domain.model.SduiInfoCard
import com.ammar.studentdesk.sdui.domain.model.SduiInfoCardProfile
import com.ammar.studentdesk.sdui.domain.model.SduiInfoCardProfileCircularRound
import com.ammar.studentdesk.sdui.domain.model.SduiScheduleCard
import com.ammar.studentdesk.sdui.domain.model.SduiSectionHeader
import com.ammar.studentdesk.sdui.domain.model.SduiSpacer
import com.ammar.studentdesk.sdui.domain.model.SduiTabLayout
import com.ammar.studentdesk.sdui.domain.model.SduiTextAreaInput
import com.ammar.studentdesk.sdui.domain.model.SduiTextInput
import com.ammar.studentdesk.sdui.domain.model.SduiWarningBanner
import com.ammar.studentdesk.sdui.presentation.registry.UiComponentRenderer
import com.ammar.studentdesk.sdui.presentation.utils.toColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SduiDialogComponent(
    model: SduiDialog,
    onDismissRequest: () -> Unit,
    onAction: (SduiAction) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = model.title,
                fontWeight = SemiBold,
                fontSize = 16.sp,
                color = colorScheme.onSurface
            )
        },
        text = {
            Text(
                text = model.message,
                fontSize = 14.sp,
                color = colorScheme.onSurfaceVariant
            )
        },
        confirmButton = {
            TextButton(onClick = {
                onAction(model.confirmAction)
                onDismissRequest()
            }) {
                Text(
                    text = model.confirmText,
                    color = colorScheme.onSurface
                )
            }
        },
        dismissButton = {
            TextButton(onClick = {
                if (model.cancelAction != null) {
                    onAction(model.cancelAction)
                }
                onDismissRequest()
            }) {
                Text(
                    text = model.cancelText.toString(),
                    color = colorScheme.error
                )
            }
        }
    )
}

@Composable
fun SduiSectionHeaderComponent(
    model: SduiSectionHeader,
    modifier: Modifier = Modifier,
) {
    Text(
        text = model.title,
        style = typography.titleMedium,
        fontWeight = Bold,
        modifier = modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun SduiInfoCardComponent(
    model: SduiInfoCard,
    modifier: Modifier = Modifier,
    onAction: (SduiAction) -> Unit
) {
    val isDark = isSystemInDarkTheme()
    val containerColor = if (isDark) {
        colorScheme.secondaryContainer
    } else {
        model.backgroundColor.toColor(colorScheme.surfaceVariant)
    }

    val contentColor = if (isDark) {
        colorScheme.onSecondaryContainer
    } else {
        colorScheme.onSurface
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable { model.action?.let { onAction(it) } },
        colors = cardColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = model.title,
                style = typography.titleMedium,
                fontWeight = SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = model.description,
                style = typography.bodyMedium
            )
        }
    }
}

@Composable
fun SduiCardProfileDetailedComponent(
    model: SduiCardProfileDetailed,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        border = BorderStroke(1.dp, colorScheme.outline),
        colors = cardColors(containerColor = colorScheme.surfaceVariant)
    ) {
        Text(
            text = model.title,
            modifier = Modifier.padding(top = 10.dp, start = 10.dp),
            style = typography.bodyLarge
        )
        Text(
            model.subtitle,
            modifier = modifier.padding(start = 10.dp, bottom = 10.dp),
            style = typography.bodyMedium
        )
    }
}

@Composable
fun SduiInfoCardProfileCircularRoundComponent(
    model: SduiInfoCardProfileCircularRound,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        AsyncImage(
            model = model.imageUrl,
            contentDescription = "Profile Picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Gray, CircleShape)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = model.title,
            style = typography.titleMedium,
            fontWeight = Bold
        )
    }
}

@Composable
fun SduiInfoCardProfileComponent(
    model: SduiInfoCardProfile,
    modifier: Modifier = Modifier,
    onAction: (SduiAction) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { model.action?.let { onAction(it) } },
        border = BorderStroke(1.dp, colorScheme.outline),
        colors = cardColors(
            containerColor = colorScheme.surfaceVariant
        )
    ) {
        Text(
            text = model.description,
            modifier = Modifier.padding(24.dp),
            style = typography.bodyLarge
        )
    }
}

@Composable
fun SduiWarningBannerComponent(
    model: SduiWarningBanner,
    modifier: Modifier = Modifier,
    onAction: (SduiAction) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { model.action?.let { onAction(it) } },
        colors = cardColors(
            containerColor = colorScheme.errorContainer
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = model.title,
                style = typography.titleMedium,
                fontWeight = Bold,
                color = colorScheme.onErrorContainer
            )
            Spacer(modifier = Modifier.height(4.dp))
            SelectionContainer() {
                Text(
                    text = model.description,
                    style = typography.bodyMedium,
                    color = colorScheme.onErrorContainer
                )
            }
        }
    }
}

@Composable
fun SduiEmptyStateCardComponent(
    model: SduiEmptyStateCard,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
        contentAlignment = Center
    ) {
        Text(
            text = model.message,
            style = typography.bodyMedium,
            color = colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun SduiScheduleCardComponent(
    model: SduiScheduleCard,
    modifier: Modifier = Modifier,
    onAction: (SduiAction) -> Unit
) {
    Card(
        modifier = modifier
            .width(200.dp)
            .padding(end = 12.dp, top = 8.dp, bottom = 8.dp)
            .clickable { model.action?.let { onAction(it) } },
        colors = cardColors(
            containerColor = colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = model.courseName,
                style = typography.titleSmall,
                fontWeight = Bold,
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "⌚ ${model.time}",
                style = typography.bodySmall,
                color = colorScheme.onSurfaceVariant
            )
            Text(
                text = "📍 ${model.room}",
                style = typography.bodySmall,
                color = colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun SduiTextInputComponent(
    model: SduiTextInput,
    modifier: Modifier = Modifier
) {
    val formState = LocalFormState.current
    val currentValue = formState[model.fieldName] ?: ""
    OutlinedTextField(
        value = currentValue,
        onValueChange = { formState[model.fieldName] = it },
        label = { Text(model.label) },
        placeholder = { model.placeholder?.let { Text(it) } },
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}


@Composable
fun SduiTextAreaInputComponent(model: SduiTextAreaInput) {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text(model.label) },
        placeholder = { model.placeholder?.let { Text(it) } },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        minLines = 4,
        maxLines = 6
    )
}

@Composable
fun SduiTabLayoutComponent(
    model: SduiTabLayout,
    onAction: (SduiAction) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { model.tabs.size })
    val coroutineScope = rememberCoroutineScope()

    Column {
        TabRow(selectedTabIndex = pagerState.currentPage) {
            model.tabs.forEachIndexed { index, tab ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = { Text(tab.title) }
                )
            }
        }

        HorizontalPager(state = pagerState) { page ->
            Column {
                model.tabs[page].children.forEach { childComponent ->
                    UiComponentRenderer(
                        component = childComponent,
                        onAction = onAction
                    )
                }
            }
        }
    }
}

@Composable
fun SduiHistoryCardComponent(model: SduiHistoryCard) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = model.title, style = typography.titleMedium, fontWeight = Bold)
            Text(text = "Tanggal: ${model.date}", style = typography.bodyMedium)

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            Text(text = "Ka.Prodi: ${model.statusKaProdi}", style = typography.bodySmall)
            Text(text = "Akademik: ${model.statusAkademik}", style = typography.bodySmall)

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Siap Diambil: ${model.readyDate}", color = colorScheme.primary, fontWeight = Bold)

            if (model.canCancel || model.canDownload) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    if (model.canCancel) {
                        OutlinedButton(onClick = { /* Handle cancel */ }, modifier = Modifier.padding(end = 8.dp)) {
                            Text("Batal")
                        }
                    }
                    if (model.canDownload) {
                        Button(onClick = { /* Handle download */ }) {
                            Text("Unduh Surat")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SduiAccordionComponent(
    model: SduiAccordion,
    onAction: (SduiAction) -> Unit
) {
    var expanded by remember { mutableStateOf(model.isExpanded) }

    Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = model.title, style = typography.titleMedium)
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "Expand/Collapse"
                )
            }

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(bottom = 16.dp)) {
                    model.children.forEach { child ->
                        UiComponentRenderer(component = child, onAction = onAction)
                    }
                }
            }
        }
    }
}

@Composable
fun SduiCarouselComponent(
    component: SduiCarousel,
    onAction: (SduiAction) -> Unit
) {
    val actualSize = component.items.size
    val virtualPageCount = if (actualSize > 1) Int.MAX_VALUE else actualSize

    val initialStartIndex = if (actualSize > 1) {
        (Int.MAX_VALUE / 2) - ((Int.MAX_VALUE / 2) % actualSize)
    } else 0

    val pagerState = rememberPagerState(
        initialPage = initialStartIndex,
        pageCount = { virtualPageCount }
    )

    LaunchedEffect(actualSize) {
        if (actualSize > 1) {
            while (true) {
                delay(3000)
                pagerState.animateScrollToPage(pagerState.currentPage + 1)
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(vertical = 16.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 16.dp),
            pageSpacing = 8.dp
        ) { page ->
            val item = component.items[page % actualSize]

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.url)
                    .crossfade(true)
                    .build(),
                contentDescription = "Carousel Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable {
                        item.action?.let { onAction(it) }
                    }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SduiDropdownComponent(
    model: SduiDropdown,
    modifier: Modifier = Modifier
) {
    val formState = LocalFormState.current
    var expanded by remember { mutableStateOf(false) }
    val currentValueId = formState[model.fieldName]
    val currentValueLabel = model.options.find { it.id == currentValueId }?.label ?: ""

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        OutlinedTextField(
            value = currentValueLabel,
            onValueChange = {},
            readOnly = true,
            label = { Text(model.label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            model.options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.label) },
                    onClick = {
                        formState[model.fieldName] = option.id
                        expanded = false
                    }
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SduiDropdownInputComponent(model: SduiDropdownInput) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(model.options.firstOrNull() ?: "") }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            label = { Text(model.label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.fillMaxWidth().menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            model.options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        selectedOption = selectionOption
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun SduiButtonComponent(
    model: SduiButton,
    modifier: Modifier = Modifier,
    onAction: (SduiAction) -> Unit
) {
    LocalFormState.current
    Button(
        onClick = {
            model.action?.let { onAction(it) }
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Text(text = model.text)
    }
}

@Composable
fun SduiSpacerComponent(model: SduiSpacer) {
    val spacingDp = when (model.size.lowercase()) {
        "small" -> 8.dp
        "medium" -> 16.dp
        "large" -> 24.dp
        "extra_large" -> 32.dp
        else -> 16.dp
    }
    Spacer(modifier = Modifier.height(spacingDp))
}

@Composable
fun SduiHtmlTextComponent(
    model: SduiHtmlText,
    modifier: Modifier = Modifier
) {
    AndroidView(
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        factory = { context ->
            TextView(context).apply {
                textSize = 14f // SP approx
                setTextColor(android.graphics.Color.DKGRAY)
            }
        },
        update = { textView ->
            textView.text = HtmlCompat.fromHtml(model.html, HtmlCompat.FROM_HTML_MODE_COMPACT)
        }
    )
}

@Composable
fun SduiImageUploadInputComponent(
    model: com.ammar.studentdesk.sdui.domain.model.SduiImageUploadInput,
    modifier: Modifier = Modifier
) {
    val formState = LocalFormState.current
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
        if (uri != null) {
            formState[model.name] = uri.toString()
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(text = model.label, style = typography.bodyMedium, fontWeight = SemiBold)
        if (model.hint != null) {
            Text(text = model.hint, style = typography.bodySmall, color = colorScheme.onSurfaceVariant)
        }
        Spacer(modifier = Modifier.height(8.dp))
        
        if (selectedImageUri != null) {
            AsyncImage(
                model = selectedImageUri,
                contentDescription = "Selected image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(1.dp, colorScheme.outline, RoundedCornerShape(8.dp))
                    .clickable { launcher.launch("image/*") }
            )
        } else {
            OutlinedButton(
                onClick = { launcher.launch("image/*") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Pilih Gambar")
            }
        }
    }
}
@Composable
fun SduiScoreCardComponent(
    model: com.ammar.studentdesk.sdui.domain.model.SduiScoreCard,
    onAction: (SduiAction) -> Unit
) {
    val statusColor = model.statusColor?.toColor(colorScheme.primary) ?: colorScheme.primary

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable { model.action?.let { onAction(it) } },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = model.title, style = typography.titleMedium, fontWeight = Bold)
            Text(text = "Tanggal: ${model.date}", style = typography.bodyMedium)

            if (model.score != null || model.status != null) {
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                if (model.score != null) {
                    Text(text = "Skor: ${model.score}", style = typography.bodyMedium)
                }
                if (model.status != null) {
                    Text(
                        text = model.status, 
                        color = statusColor, 
                        fontWeight = Bold,
                        style = typography.titleSmall
                    )
                }
            }
        }
    }
}
