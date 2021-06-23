package zkwang.excelk.analyzers

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import zkwang.excelk.annotations.Column
import zkwang.excelk.annotations.Converter
import zkwang.excelk.annotations.SheetName
import zkwang.excelk.converters.IntConverter
import zkwang.excelk.converters.StringConverter
import zkwang.excelk.exceptions.ConverterAnnotationRequiredException
import zkwang.excelk.exceptions.SheetNameAnnotationRequiredException
import kotlin.reflect.full.createInstance

internal class MetaDataAnalyzerTest {
    @SheetName("SheetName", 3)
    internal data class ASheet(
        @Column("A")
        @Converter(IntConverter::class)
        var aInt: Int? = null,
        @Column("B")
        @Converter(StringConverter::class)
        var aString: String? = null
    )

    @Test
    fun `should analyze meta data mapping successfully`() {
        val result = MetaDataAnalyzer.analyze(ASheet::class)
        val instance = ASheet::class.createInstance()
        assertThat(result).isNotNull
        assertThat(result.dataStartRowNo).isEqualTo(3)
        assertThat(result.sheetName).isEqualTo("SheetName")
        assertThat(result.columnFieldMappings).hasSize(2)
        val aIntColumnFieldMapping = result.columnFieldMappings[0]
        assertThat(aIntColumnFieldMapping.columnName).isEqualTo("A")
        assertThat(aIntColumnFieldMapping.field.name).isEqualTo("aInt")
        assertThat(aIntColumnFieldMapping.typeConverter).isInstanceOf(IntConverter::class.java)
        assertThat(aIntColumnFieldMapping.typeConverter.convert("2")).isEqualTo(2)
        val aStringColumnFieldMapping = result.columnFieldMappings[1]
        assertThat(aStringColumnFieldMapping.columnName).isEqualTo("B")
        assertThat(aStringColumnFieldMapping.field.name).isEqualTo("aString")
        assertThat(aStringColumnFieldMapping.typeConverter).isInstanceOf(StringConverter::class.java)
        assertThat(aStringColumnFieldMapping.typeConverter.convert("T")).isEqualTo("T")
        aIntColumnFieldMapping.field.set(instance, 5)
        aStringColumnFieldMapping.field.set(instance, "Test")
        assertThat(instance.aInt).isEqualTo(5)
        assertThat(instance.aString).isEqualTo("Test")
    }

    internal data class ASheetWithoutSheetNameAnnotation(
        @Column("A")
        @Converter(IntConverter::class)
        var aInt: Int = 0,

        @Column("B")
        @Converter(StringConverter::class)
        var aString: String = ""
    )

    @Test
    fun `should throw SheetNameAnnotationRequiredException if no sheetname annotation`() {
        assertThrows<SheetNameAnnotationRequiredException> {
            MetaDataAnalyzer.analyze(
                ASheetWithoutSheetNameAnnotation::class
            )
        }.also {
            assertThat(it.message).isEqualTo("For type ${ASheetWithoutSheetNameAnnotation::class.simpleName}, SheetName annotation is required")
        }
    }

    @SheetName("SheetName")
    internal data class ASheetWithOnlyColumnAnnotation(
        @Column("A")
        @Converter(IntConverter::class)
        var AInt: Int = 0,

        @Column("B")
        var AString: String = ""
    )

    @Test
    fun `should throw ConverterAnnotationRequiredException if only specify the column annotation`() {
        assertThrows<ConverterAnnotationRequiredException> {
            MetaDataAnalyzer.analyze(
                ASheetWithOnlyColumnAnnotation::class
            )
        }.also {
            assertThat(it.message).isEqualTo("For AString inside the type ASheetWithOnlyColumnAnnotation, Converter annotation is required if Column annotation is specific")
        }
    }

    @SheetName("SheetName")
    internal data class ASheetWithPropertyNoColumnAttribute(
        @Column("A")
        @Converter(IntConverter::class)
        var aInt: Int = 0,

        @Column("B")
        @Converter(StringConverter::class)
        var aString: String = "",

        var anotherInt: Int = 0,
        var anotherString: String = ""
    )

    @Test
    fun `the property without column annotation will be ignored`() {
        val result = MetaDataAnalyzer.analyze(ASheetWithPropertyNoColumnAttribute::class)
        assertThat(result.columnFieldMappings).hasSize(2)
        val aIntColumnFieldMapping = result.columnFieldMappings[0]
        assertThat(aIntColumnFieldMapping.field.name).isEqualTo("aInt")
        val aStringColumnFieldMapping = result.columnFieldMappings[1]
        assertThat(aStringColumnFieldMapping.field.name).isEqualTo("aString")
    }
}
