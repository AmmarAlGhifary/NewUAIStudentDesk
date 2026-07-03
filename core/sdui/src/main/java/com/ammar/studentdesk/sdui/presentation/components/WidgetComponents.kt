package com.ammar.studentdesk.sdui.presentation.components

import android.graphics.Color.parseColor
import android.net.Uri
import android.widget.TextView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
import androidx.core.graphics.toColorInt
import androidx.core.text.HtmlCompat
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ammar.studentdesk.sdui.domain.model.*
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
        title = { Text(text = model.title, fontWeight = SemiBold, fontSize = 16.sp, color = colorScheme.onSurface) },
        text = { Text(text = model.message, fontSize = 14.sp, color = colorScheme.onSurfaceVariant) },
        confirmButton = {
            TextButton(onClick = {
                onAction(model.confirmAction)
                onDismissRequest()
            }) { Text(text = model.confirmText, color = colorScheme.onSurface) }
        },
        dismissButton = {
            TextButton(onClick = {
                model.cancelAction?.let { onAction(it) }
                onDismissRequest()
            }) { Text(text = model.cancelText ?: "Batal", color = colorScheme.error) }
        }
    )
}

@Composable
fun SduiSectionHeaderComponent(model: SduiSectionHeader, modifier: Modifier = Modifier) {
    Text(
        text = model.title,
        style = typography.titleMedium,
        fontWeight = Bold,
        modifier = modifier
    )
}

@Composable
fun SduiInfoCardComponent(model: SduiInfoCard, modifier: Modifier = Modifier, onAction: (SduiAction) -> Unit) {
    Column(modifier = modifier.clickable { model.action?.let { onAction(it) } }) {
        Text(text = model.title, style = typography.titleMedium, fontWeight = SemiBold)
        Text(text = model.description, style = typography.bodyMedium)
    }
}

@Composable
fun SduiCardProfileDetailedComponent(model: SduiCardProfileDetailed, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(text = model.title, style = typography.bodyLarge)
        Text(text = model.subtitle, style = typography.bodyMedium)
    }
}

@Composable
fun SduiInfoCardProfileCircularRoundComponent(model: SduiInfoCardProfileCircularRound, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        AsyncImage(
            model = model.imageUrl,
            contentDescription = "Profile Picture",
            contentScale = ContentScale.Crop,
            modifier = model.imageModifier?.toComposeModifier() ?: Modifier
        )
        Text(
            text = model.title,
            style = typography.titleMedium,
            fontWeight = Bold,
            modifier = model.titleModifier?.toComposeModifier() ?: Modifier
        )
    }
}

@Composable
fun SduiInfoCardProfileComponent(model: SduiInfoCardProfile, modifier: Modifier = Modifier, onAction: (SduiAction) -> Unit) {
    Column(modifier = modifier.clickable { model.action?.let { onAction(it) } }) {
        Text(text = model.description, style = typography.bodyLarge)
    }
}

@Composable
fun SduiWarningBannerComponent(model: SduiWarningBanner, modifier: Modifier = Modifier, onAction: (SduiAction) -> Unit) {
    Column(modifier = modifier.clickable { model.action?.let { onAction(it) } }) {
        Text(text = model.title, style = typography.titleMedium, fontWeight = Bold, color = colorScheme.onErrorContainer)
        SelectionContainer {
            Text(text = model.description, style = typography.bodyMedium, color = colorScheme.onErrorContainer)
        }
    }
}

@Composable
fun SduiEmptyStateCardComponent(model: SduiEmptyStateCard, modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Text(text = model.message, style = typography.bodyMedium, color = colorScheme.onSurfaceVariant)
    }
}

@Composable
fun SduiScheduleCardComponent(model: SduiScheduleCard, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(text = model.courseName, style = typography.titleSmall, fontWeight = Bold, maxLines = 2)
        Text(text = "⌚ ${model.time}", style = typography.bodySmall, color = colorScheme.onSurfaceVariant)
        Text(text = "📍 ${model.room}", style = typography.bodySmall, color = colorScheme.onSurfaceVariant)
    }
}

@Composable
fun SduiTextInputComponent(model: SduiTextInput, modifier: Modifier = Modifier) {
    val formState = LocalFormState.current
    val currentValue = formState[model.fieldName] ?: ""
    OutlinedTextField(
        value = currentValue,
        onValueChange = { formState[model.fieldName] = it },
        label = { Text(model.label) },
        placeholder = { model.placeholder?.let { Text(it) } },
        modifier = modifier
    )
}

@Composable
fun SduiTextAreaInputComponent(model: SduiTextAreaInput, modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf("") }
    OutlinedTextField(
        value = text,
        onValueChange = { },
        label = { Text(model.label) },
        placeholder = { model.placeholder?.let { Text(it) } },
        modifier = modifier,
        minLines = 4,
        maxLines = 6
    )
}

@Composable
fun SduiTabLayoutComponent(model: SduiTabLayout, modifier: Modifier = Modifier, onAction: (SduiAction) -> Unit) {
    val pagerState = rememberPagerState(pageCount = { model.tabs.size })
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = modifier) {
        TabRow(selectedTabIndex = pagerState.currentPage) {
            model.tabs.forEachIndexed { index, tab ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                    text = { Text(tab.title) }
                )
            }
        }
        HorizontalPager(state = pagerState) { page ->
            Column {
                model.tabs[page].children.forEach { childComponent ->
                    UiComponentRenderer(component = childComponent, onAction = onAction)
                }
            }
        }
    }
}

@Composable
fun SduiHistoryCardComponent(model: SduiHistoryCard, modifier: Modifier = Modifier, onAction: (SduiAction) -> Unit ) {
    Column(modifier = modifier) {
        Text(text = model.title, style = typography.titleMedium, fontWeight = Bold)
        Text(text = model.dateText, style = typography.bodyMedium)

        Divider(modifier = Modifier.padding(vertical = 8.dp))

        Text(text = model.prodiText, style = typography.bodySmall)
        Text(text = model.akademikText, style = typography.bodySmall)
        Text(text = model.readyText, color = colorScheme.primary, fontWeight = Bold)
        if (model.canCancel || model.canDownload) {
            Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.End) {
                if (model.canCancel) {
                    OutlinedButton(onClick = { model.cancelAction?.let {
                        onAction(it)
                    } },
                        modifier = Modifier.padding(end = 8.dp)) {
                        Text("Batal")
                    }
                }
                if (model.canDownload) {
                    Button(onClick = { model.downloadAction?.let { onAction(it) } }) {
                        Text("Unduh Surat")
                    }
                }
            }
        }
    }
}

@Composable
fun SduiAccordionComponent(model: SduiAccordion, modifier: Modifier = Modifier, onAction: (SduiAction) -> Unit) {
    var expanded by remember { mutableStateOf(model.isExpanded) }
    Column(modifier = modifier) {

        val headerModifier = model.headerModifier?.toComposeModifier() ?: modifier.padding(16.dp)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .then(headerModifier),
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
            val contentModifier = model.contentModifier?.toComposeModifier() ?: modifier.padding(16.dp)
            Column (modifier = contentModifier){
                model.children.forEach { child ->
                    UiComponentRenderer(component = child, onAction = onAction)
                }
            }
        }
    }
}

@Composable
fun SduiCarouselComponent(component: SduiCarousel, modifier: Modifier = Modifier, onAction: (SduiAction) -> Unit) {
    val actualSize = component.items.size
    val virtualPageCount = if (actualSize > 1) Int.MAX_VALUE else actualSize
    val initialStartIndex = if (actualSize > 1) { (Int.MAX_VALUE / 2) - ((Int.MAX_VALUE / 2) % actualSize) } else 0
    val pagerState = rememberPagerState(initialPage = initialStartIndex, pageCount = { virtualPageCount })

    LaunchedEffect(actualSize) {
        if (actualSize > 1) {
            while (true) {
                delay(3000)
                pagerState.animateScrollToPage(pagerState.currentPage + 1)
            }
        }
    }
    Box(modifier = modifier) {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 16.dp),
            pageSpacing = 8.dp
        ) { page ->
            val item = component.items[page % actualSize]
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(item.url).crossfade(true).build(),
                contentDescription = "Carousel Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { item.action?.let { onAction(it) } }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SduiDropdownComponent(model: SduiDropdown, modifier: Modifier = Modifier) {
    val formState = LocalFormState.current
    var expanded by remember { mutableStateOf(false) }
    val currentValueId = formState[model.fieldName]
    val currentValueLabel = model.options.find { it.id == currentValueId }?.label ?: ""

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = currentValueLabel,
            onValueChange = {},
            readOnly = true,
            label = { Text(model.label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            modifier = Modifier.menuAnchor().fillMaxWidth()
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
fun SduiDropdownInputComponent(model: SduiDropdownInput, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(model.options.firstOrNull() ?: "") }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            label = { Text(model.label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor().fillMaxWidth()
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
fun SduiButtonComponent(model: SduiButton, modifier: Modifier = Modifier, onAction: (SduiAction) -> Unit) {
    LocalFormState.current
    Button(
        onClick = { model.action?.let { onAction(it) } },
        modifier = modifier
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
fun SduiHtmlTextComponent(model: SduiHtmlText, modifier: Modifier = Modifier) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            TextView(context).apply {
                textSize = 14f
                setTextColor(android.graphics.Color.DKGRAY)
            }
        },
        update = { textView ->
            textView.text = HtmlCompat.fromHtml(model.html, HtmlCompat.FROM_HTML_MODE_COMPACT)
        }
    )
}

@Composable
fun SduiImageUploadInputComponent(model: SduiImageUploadInput, modifier: Modifier = Modifier) {
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

    Column(modifier = modifier) {
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
                modifier = Modifier.fillMaxWidth().height(150.dp).clickable { launcher.launch("image/*") }
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
fun SduiScoreCardComponent(model: SduiScoreCard, modifier: Modifier = Modifier, onAction: (SduiAction) -> Unit) {
    val statusColor = model.statusColor?.let { hex ->
        runCatching { Color(hex.toColorInt()) }.getOrNull()
    } ?: colorScheme.primary

    Row(
        modifier = modifier.clickable { model.action?.let { onAction(it) } },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = model.title, style = typography.titleMedium, fontWeight = Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = model.dateText, 
                style = typography.bodyMedium,
                color = colorScheme.onSurfaceVariant
            )
            
            if (model.statusText != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .background(statusColor.copy(alpha = 0.1f), shape = RoundedCornerShape(16.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = model.statusText,
                        color = statusColor,
                        style = typography.labelSmall,
                        fontWeight = Bold
                    )
                }
            }
        }
        
        if (model.scoreText != null) {
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(colorScheme.surfaceVariant, shape = RoundedCornerShape(12.dp))
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(
                    text = "Skor",
                    style = typography.labelSmall,
                    color = colorScheme.onSurfaceVariant
                )
                Text(
                    text = model.scoreText,
                    style = typography.headlineSmall,
                    fontWeight = Bold,
                    color = colorScheme.onSurface
                )
            }
        }
    }
}
