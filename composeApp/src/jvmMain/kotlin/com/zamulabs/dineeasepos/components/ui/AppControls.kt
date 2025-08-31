package com.zamulabs.dineeasepos.components.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.Alignment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Centralized reusable UI controls for DineEasePOS.
 *
 * These wrappers apply default theme-driven styling (shape, heights, colors)
 * and expose flexible parameters and content slots so screens can extend behavior
 * without duplicating boilerplate.
 */

// TextField
@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: (@Composable (() -> Unit))? = null,
    placeholder: (@Composable (() -> Unit))? = null,
    leading: (@Composable (() -> Unit))? = null,
    trailing: (@Composable (() -> Unit))? = null,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    isError: Boolean = false,
    supportingText: (@Composable (() -> Unit))? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    height: Dp = 56.dp,
    shape: CornerBasedShape = MaterialTheme.shapes.extraLarge,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.height(height),
        label = label,
        placeholder = placeholder,
        leadingIcon = leading,
        trailingIcon = trailing,
        singleLine = singleLine,
        enabled = enabled,
        isError = isError,
        supportingText = supportingText,
        keyboardOptions = keyboardOptions,
        shape = shape,
    )
}

// Primary Button with optional loading state
@Composable
fun AppButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    shape: CornerBasedShape = MaterialTheme.shapes.extraLarge,
    height: Dp = 48.dp,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        enabled = enabled && !loading,
        shape = shape,
        modifier = modifier.height(height),
        colors = colors,
    ) {
        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.height(20.dp),
                strokeWidth = 2.dp,
                color = MaterialTheme.colorScheme.onPrimary
            )
        } else {
            content()
        }
    }
}

// Outlined variant
@Composable
fun AppOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: CornerBasedShape = MaterialTheme.shapes.extraLarge,
    height: Dp = 48.dp,
    colors: ButtonColors = ButtonDefaults.outlinedButtonColors(),
    content: @Composable RowScope.() -> Unit,
) {
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        shape = shape,
        modifier = modifier.height(height),
        colors = colors,
    ) { content() }
}

// Simple Dropdown built from ExposedDropdownMenuBox
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> AppDropdown(
    label: String,
    selected: T?,
    items: List<T>,
    onSelected: (T) -> Unit,
    modifier: Modifier = Modifier,
    itemLabel: (T) -> String = { it.toString() },
    placeholder: String = "",
) {
    val expanded: MutableState<Boolean> = remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(expanded = expanded.value, onExpandedChange = { expanded.value = !expanded.value }) {
        OutlinedTextField(
            value = selected?.let(itemLabel) ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            placeholder = { if (placeholder.isNotEmpty()) Text(placeholder) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value) },
            modifier = modifier.height(56.dp).menuAnchor(),
            shape = MaterialTheme.shapes.extraLarge,
        )
        ExposedDropdownMenu(expanded = expanded.value, onDismissRequest = { expanded.value = false }) {
            items.forEach { item ->
                DropdownMenuItem(text = { Text(itemLabel(item)) }, onClick = {
                    onSelected(item)
                    expanded.value = false
                })
            }
        }
    }
}

// FilterChip wrapper for consistent shape and colors
@Composable
fun AppFilterChip(
    selected: Boolean,
    onClick: () -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    shape: CornerBasedShape = MaterialTheme.shapes.extraLarge,
    selectedContainerColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(label) },
        modifier = modifier,
        shape = shape,
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = selectedContainerColor
        )
    )
}

// Reusable back + breadcrumb header using AppScreenTopBar for consistency
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackBreadcrumb(
    parentLabel: String,
    currentLabel: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    actions: @Composable () -> Unit = {},
) {
    AppScreenTopBar(
        modifier = modifier,
        navigationIcon = Icons.Filled.ArrowBack,
        onNavigationClick = onBack,
        actions = actions, 
        titleContent = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = parentLabel,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    text = "/",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    text = currentLabel,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    )
}
