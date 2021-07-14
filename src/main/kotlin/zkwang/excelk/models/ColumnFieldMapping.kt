package zkwang.excelk.models

import zkwang.excelk.converters.TypeConverter
import kotlin.reflect.KMutableProperty

data class ColumnFieldMapping(
    val columnName: String,
    val field: KMutableProperty<*>,
    val typeConverter: TypeConverter<*>,
    val dependsOnColumns: List<String>
)
