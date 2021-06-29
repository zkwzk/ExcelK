package zkwang.excelk.models

import kotlin.reflect.KClass

data class SheetMapping<T: Any>(
    val modelType: KClass<T>,
    val sheetName: String,
    val dataStartRowNo: Int,
    val dataEndRowNo: Int,
    val columnFieldMappings: List<ColumnFieldMapping>
)
