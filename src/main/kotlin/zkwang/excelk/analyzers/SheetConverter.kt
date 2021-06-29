package zkwang.excelk.analyzers

import org.apache.poi.ss.usermodel.DataFormatter
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.util.CellReference
import zkwang.excelk.models.ColumnContext
import zkwang.excelk.models.RowContext
import zkwang.excelk.models.RowConvertResult
import zkwang.excelk.models.SheetMapping
import zkwang.excelk.utils.cast
import kotlin.reflect.full.createInstance


class SheetConverter {
    companion object {
        inline fun <reified T : Any> convert(
            sheet: Sheet,
            sheetMapping: SheetMapping<T>
        ): List<RowConvertResult<T>> {
            val startRowIndex = getStartRowIndex(sheetMapping)
            val lastRowIndex = getEndRowIndex(sheet, sheetMapping)
            val convertResult: MutableList<RowConvertResult<T>> = mutableListOf()
            for (rowIndex in startRowIndex..lastRowIndex) {
                val rowContext = RowContext(
                    modelInstance = sheetMapping.modelType.createInstance(),
                    rowNumber = rowIndex + 1,
                    columnValueMap = mutableMapOf(),
                    errorMessageMap = mutableMapOf()
                )
                val rowData = sheet.getRow(rowIndex)
                for (columnMap in sheetMapping.columnFieldMappings) {
                    val columnName = columnMap.columnName
                    val columnIndex = getColumnIndexFromName(columnName)
                    val cell = rowData.getCell(columnIndex)
                    val formatter = DataFormatter()
                    val cellStringValue = formatter.formatCellValue(cell)
                    val columnContext = ColumnContext(columnName, columnMap.field)
                    columnMap.typeConverter.convert(
                        originText = cellStringValue,
                        columnContext = columnContext,
                        rowContext = rowContext
                    )
                }

                convertResult.add(
                    RowConvertResult(
                        modelInstance = cast(rowContext.modelInstance),
                        rowNumber = rowContext.rowNumber,
                        columnConvertResults = rowContext.columnValueMap,
                        errorMsgMap = rowContext.errorMessageMap
                    )
                )
            }

            return convertResult
        }

        fun <T : Any> getStartRowIndex(sheetMapping: SheetMapping<T>) =
            sheetMapping.dataStartRowNo - 1

        fun <T : Any> getEndRowIndex(sheet: Sheet, sheetMapping: SheetMapping<T>) =
            if (sheetMapping.dataEndRowNo > 0) sheetMapping.dataEndRowNo - 1 else sheet.lastRowNum

        fun getColumnIndexFromName(columnName: String): Int =
            CellReference.convertColStringToIndex(columnName)

        fun getColumnNameFromIndex(columnIndex: Int): String =
            CellReference.convertNumToColString(columnIndex)
    }
}
