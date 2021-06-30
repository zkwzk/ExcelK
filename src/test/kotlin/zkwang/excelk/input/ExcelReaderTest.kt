package zkwang.excelk.input

import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.nio.file.InvalidPathException
import java.nio.file.Paths

internal class ExcelReaderTest {
    @Test
    fun `should read xls file correctly`() {
        val path = Paths.get("src", "test", "resources", "helloworld.xls")
        val workbook = ExcelReader.read(path.toString())
        assertThat(workbook).isNotNull
        assertThat(workbook).isInstanceOf(HSSFWorkbook::class.java)
        workbook.close()
    }

    @Test
    fun `should read xlsx file correctly`() {
        val path = Paths.get("src", "test", "resources", "helloworld.xlsx")
        val workbook = ExcelReader.read(path.toString())
        assertThat(workbook).isNotNull
        assertThat(workbook).isInstanceOf(XSSFWorkbook::class.java)
        workbook.close()
    }

    @Test
    fun `should throw path invalid exception if path not exist`() {
        val path = "src/test/resource/helloworld.xlsx"
        assertThrows<InvalidPathException> {
            ExcelReader.read(path)
        }.also {
            assertThat(
                it.message
            ).isEqualTo("the given path is not exist or invalid: $path")
        }
    }

    @Test
    fun `should throw path invalid exception if path is a directory`() {
        val path = "src/test/resources"
        assertThrows<InvalidPathException> {
            ExcelReader.read(path)
        }.also {
            assertThat(
                it.message
            ).isEqualTo("the given path is not exist or invalid: $path")
        }
    }

    @Test
    fun `should throw path invalid exception if extension is neither xlsx or xls`() {
        val path = "src/test/resources/helloworld.xls1"
        assertThrows<InvalidPathException> {
            ExcelReader.read(path)
        }.also {
            assertThat(
                it.message
            ).isEqualTo("the given file format is not supported: $path")
        }
    }
}
