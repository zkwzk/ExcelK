package zkwang.excelk.utils

object ErrorMessageBuilder {
    fun getConvertFailedErrorMsg(columnName: String, rowNo: Int, message: String?) =
        "Row $rowNo, Column $columnName, convert failed due to $message"
}
