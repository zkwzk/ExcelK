package zkwang.excelk.models

import kotlin.reflect.KMutableProperty

data class ColumnContext(val columnName: String, val field: KMutableProperty<*>)
