package zkwang.excelk.analyzers

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import zkwang.excelk.ASheet
import zkwang.excelk.ASheetWithOnlyColumnAnnotation
import zkwang.excelk.ASheetWithPropertyNoColumnAttribute
import zkwang.excelk.ASheetWithoutSheetNameAnnotation
import zkwang.excelk.converters.IntConverter
import zkwang.excelk.converters.StringConverter
import zkwang.excelk.exceptions.ConverterAnnotationRequiredException
import zkwang.excelk.exceptions.SheetNameAnnotationRequiredException
import kotlin.reflect.full.createInstance

internal class MetaDataAnalyzerTest {
    @Test
    fun `should analyze meta data mapping successfully`() {
        val result = MetaDataAnalyzer.analyze(ASheet::class)
        val instance = ASheet::class.createInstance()
        assertThat(result).isNotNull
        assertThat(result.dataStartRowNo).isEqualTo(3)
        assertThat(result.dataEndRowNo).isEqualTo(-1)
        assertThat(result.sheetName).isEqualTo("SheetName")
        assertThat(result.columnFieldMappings).hasSize(2)
        val aIntColumnFieldMapping = result.columnFieldMappings[0]
        assertThat(aIntColumnFieldMapping.columnName).isEqualTo("A")
        assertThat(aIntColumnFieldMapping.field.name).isEqualTo("aInt")
        assertThat(aIntColumnFieldMapping.typeConverter).isInstanceOf(IntConverter::class.java)
        val aStringColumnFieldMapping = result.columnFieldMappings[1]
        assertThat(aStringColumnFieldMapping.columnName).isEqualTo("B")
        assertThat(aStringColumnFieldMapping.field.name).isEqualTo("aString")
        assertThat(aStringColumnFieldMapping.typeConverter).isInstanceOf(StringConverter::class.java)
        aIntColumnFieldMapping.field.setter.call(instance, 5)
        aStringColumnFieldMapping.field.setter.call(instance, "Test")
        assertThat(instance.aInt).isEqualTo(5)
        assertThat(instance.aString).isEqualTo("Test")
    }

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
