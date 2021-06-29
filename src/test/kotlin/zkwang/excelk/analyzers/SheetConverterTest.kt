package zkwang.excelk.analyzers

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import zkwang.excelk.HelloWorldDAO
import zkwang.excelk.input.ExcelReader

internal class SheetConverterTest {
    @Test
    fun `should return correct column index by column name`() {
        val workbook = ExcelReader.read("src/test/resources/helloworld.xls")
        val sheetMapping = MetaDataAnalyzer.analyze(HelloWorldDAO::class)
        val sheet = workbook.getSheet(sheetMapping.sheetName)
        val result = SheetConverter.convert(sheet, sheetMapping)
        assertThat(result).hasSize(200)
        assertThat(result[0].isSuccess).isTrue
        assertThat(result[0].modelInstance).isInstanceOf(HelloWorldDAO::class.java)
        assertThat(result[0].modelInstance).isInstanceOf(HelloWorldDAO::class.java)
        assertThat(result[0].modelInstance.AInt).isEqualTo(1)
        assertThat(result[0].modelInstance.AString).isEqualTo("a98232ee-42fc-4de0-9cb8-62a960fd7695")
        assertThat(result[199].modelInstance.AInt).isEqualTo(1)
        assertThat(result[199].modelInstance.AString).isEqualTo("23644f0e-ad36-4859-be57-b440420e5b44")
        assertThat(result[199].isSuccess).isTrue
    }

    @Test
    fun `should return correct row start index`() {
        val sheetMapping = MetaDataAnalyzer.analyze(HelloWorldDAO::class)
        assertThat(SheetConverter.getStartRowIndex(sheetMapping)).isEqualTo(1)
    }

    @Test
    fun `should return correct row end index`() {
        val workbook = ExcelReader.read("src/test/resources/helloworld.xls")
        val sheet = workbook.getSheetAt(0)
        val sheetMapping = MetaDataAnalyzer.analyze(HelloWorldDAO::class)
        assertThat(SheetConverter.getEndRowIndex(sheet, sheetMapping)).isEqualTo(200)
    }

    @Test
    fun `should convert column name to column index correctly`() {
        assertThat(SheetConverter.getColumnIndexFromName("A")).isEqualTo(0)
    }

    @Test
    fun `should convert column index to column name correctly`() {
        assertThat(SheetConverter.getColumnNameFromIndex(1)).isEqualTo("B")
    }
}
