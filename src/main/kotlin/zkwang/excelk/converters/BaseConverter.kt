package zkwang.excelk.converters

import zkwang.excelk.models.ColumnContext
import zkwang.excelk.models.ColumnConvertResult
import zkwang.excelk.models.RowContext
import zkwang.excelk.utils.ErrorMessageBuilder.getConvertFailedErrorMsg

sealed class BaseConverter<T : Any> : TypeConverter<T> {
    override fun convert(originText: String, columnContext: ColumnContext, rowContext: RowContext) {
        try {
            val value = executeConvert(originText)
            onConvertSuccess(originText, columnContext, rowContext, value)
        } catch (e: Exception) {
            onConvertFailed(originText, columnContext, rowContext, e)
        }
    }

    protected open fun onConvertSuccess(
        originText: String,
        columnContext: ColumnContext,
        rowContext: RowContext, convertedValue: T
    ) {
        val columnName = columnContext.columnName
        columnContext.field.setter.call(rowContext.modelInstance, convertedValue)
        rowContext.columnValueMap[columnName] =
            ColumnConvertResult(columnName, convertedValue, true)
    }

    protected abstract fun executeConvert(originText: String): T
    protected open fun onConvertFailed(
        originText: String,
        columnContext: ColumnContext,
        rowContext: RowContext,
        e: Exception
    ) {
        val columnName = columnContext.columnName
        rowContext.errorMessageMap[columnName] = getConvertFailedErrorMsg(columnName, rowContext.rowNumber, e.toString())
        rowContext.columnValueMap[columnName] = ColumnConvertResult(columnName, null, false)
    }
}
