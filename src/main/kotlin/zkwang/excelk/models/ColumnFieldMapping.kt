package zkwang.excelk.models

import zkwang.excelk.converters.TypeConverter
import java.lang.reflect.Field

data class ColumnFieldMapping(
    val columnName: String,
    val field: Field,
    val typeConverter: TypeConverter<*>
)
