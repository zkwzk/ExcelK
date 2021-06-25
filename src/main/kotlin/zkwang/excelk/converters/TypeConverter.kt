package zkwang.excelk.converters

import zkwang.excelk.models.ColumnContext
import zkwang.excelk.models.RowContext

interface TypeConverter<T : Any> {
    fun convert(originText: String, columnContext: ColumnContext, rowContext: RowContext)
}
