package zkwang.excelk.models

data class RowContext(
    val modelInstance: Any,
    val rowNumber: Int,
    val columnValueMap: MutableMap<String, ColumnConvertResult>,
    val errorMessageMap: MutableMap<String, String>
)

