package zkwang.excelk.models

data class SheetMapping(
    val sheetName: String,
    val dataStartRowNo: Int,
    val dataEndRowNo: Int,
    val columnFieldMappings: List<ColumnFieldMapping>
)
