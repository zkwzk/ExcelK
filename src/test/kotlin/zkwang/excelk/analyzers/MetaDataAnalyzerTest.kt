package zkwang.excelk.analyzers

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import zkwang.excelk.annotations.Column
import zkwang.excelk.annotations.Converter
import zkwang.excelk.annotations.SheetName
import zkwang.excelk.converters.IntConverter
import zkwang.excelk.converters.StringConverter
import zkwang.excelk.models.SheetMetaData

internal class MetaDataAnalyzerTest {
    @SheetName("SheetName", 3)
    internal class ASheet(
        @Column("A")
        @Converter(IntConverter::class)
        var AInt: Int,
        @Column("B")
        @Converter(StringConverter::class)
        var AString: String
    ) : SheetMetaData

    @Test
    fun `should analyze meta data mapping successfully`() {
        val result = MetaDataAnalyzer.analyze(ASheet::class)
        assertThat(result).isNotNull
        assertThat(result.dataStartRowNo).isEqualTo(3)
        assertThat(result.sheetName).isEqualTo("SheetName")
        assertThat(result.columnFieldMappings).hasSize(2)
        assertThat(result.columnFieldMappings[0].columnName).isEqualTo("A")
        assertThat(result.columnFieldMappings[0].field.name).isEqualTo("AInt")
        assertThat(result.columnFieldMappings[0].typeConverter).isInstanceOf(IntConverter::class.java)
        assertThat(result.columnFieldMappings[0].typeConverter.convert("2")).isEqualTo(2)
        assertThat(result.columnFieldMappings[1].columnName).isEqualTo("B")
        assertThat(result.columnFieldMappings[1].field.name).isEqualTo("AString")
        assertThat(result.columnFieldMappings[1].typeConverter).isInstanceOf(StringConverter::class.java)
        assertThat(result.columnFieldMappings[1].typeConverter.convert("T")).isEqualTo("T")
    }
}
