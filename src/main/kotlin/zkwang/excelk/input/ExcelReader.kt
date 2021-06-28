package zkwang.excelk.input

import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.nio.file.InvalidPathException

object ExcelReader {
    fun read(path: String): Workbook {
        val file = File(path)
        if (!file.exists() || file.isDirectory) {
            throw InvalidPathException(path, "the given path is not exist or invalid")
        }

        return when (file.extension) {
            "xlsx" -> XSSFWorkbook(file.inputStream())
            "xls" -> HSSFWorkbook(file.inputStream())
            else -> throw InvalidPathException(path, "the given file format is not supported")
        }
    }
}
