package com.zamulabs.dineeasepos.components.table

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Shape
import androidx.compose.foundation.shape.RoundedCornerShape
import com.seanproctor.datatable.DataColumn
import com.seanproctor.datatable.DataTableScope
import com.seanproctor.datatable.material3.DataTable
import com.seanproctor.datatable.material3.PaginatedDataTable
import com.seanproctor.datatable.paging.PaginatedDataTableState
import com.seanproctor.datatable.paging.rememberPaginatedDataTableState

/**
 * AppDataTable is a thin, reusable wrapper around compose-data-table (Material3)
 * that standardizes paddings, colors and allows headers and cells to be any composable.
 *
 * Usage:
 * AppDataTable(
 *   columns = listOf(
 *     DataColumn { HeaderComposable() },
 *     DataColumn { Text("Status") },
 *   )
 * ) {
 *   row { cell { AnyComposable() } }
 * }
 */
@Composable
fun AppDataTable(
    columns: List<DataColumn>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
    paginated: Boolean = false,
    state: PaginatedDataTableState = rememberPaginatedDataTableState(initialPageSize = 10),
    content: DataTableScope.() -> Unit,
) {
    // Outer Box adds an explicit border since the library doesn't expose a border API
    val borderColor = MaterialTheme.colorScheme.outline
    val headerColor = MaterialTheme.colorScheme.surfaceVariant
    val shape: Shape = RoundedCornerShape(8.dp)

    Box(
        modifier = modifier
            .border(width = 1.dp, color = borderColor, shape = shape)
    ) {
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 1.dp,
            shadowElevation = 0.dp,
            shape = shape,
        ) {
            if (paginated) {
                PaginatedDataTable(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    columns = columns,
                    state = state,
                    content = content,
                    contentPadding = contentPadding,
                    headerBackgroundColor = headerColor,
                )
            } else {
                DataTable(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    columns = columns,
                    content = content,
                    contentPadding = contentPadding,
                    headerBackgroundColor = headerColor,
                )
            }
        }
    }
}
