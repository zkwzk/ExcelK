package zkwang.excelk.models

data class RowConvertResult<T: Any>(
    val modelInstance: T,
    val rowNumber: Int,
    val columnConvertResults: Map<String, ColumnConvertResult>,
    val errorMsgMap: Map<String, String>
) {
    val isSuccess: Boolean = columnConvertResults.all { it.value.isSuccess }
}
