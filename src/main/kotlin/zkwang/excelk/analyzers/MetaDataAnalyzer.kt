package zkwang.excelk.analyzers

import zkwang.excelk.annotations.Column
import zkwang.excelk.annotations.Converter
import zkwang.excelk.annotations.SheetName
import zkwang.excelk.exceptions.ConverterAnnotationRequiredException
import zkwang.excelk.exceptions.SheetNameAnnotationRequiredException
import zkwang.excelk.models.ColumnFieldMapping
import zkwang.excelk.models.SheetMapping
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.javaField

class MetaDataAnalyzer {
    companion object {
        fun analyze(sheetMetaData: KClass<*>): SheetMapping {
            val sheetNameAnnotations = sheetMetaData.annotations.filterIsInstance<SheetName>()
            val typeName = sheetMetaData.qualifiedName!!
            if (sheetNameAnnotations.isEmpty()) {
                throw SheetNameAnnotationRequiredException(typeName)
            }

            val sheetNameAnnotation = sheetNameAnnotations.first()
            val columnFieldMappings: MutableList<ColumnFieldMapping> = mutableListOf()
            for (member in sheetMetaData.declaredMemberProperties) {
                val field = member.javaField!!
                val columnAnnotations = field.annotations.filterIsInstance<Column>()
                if (columnAnnotations.isEmpty()) {
                    continue
                }

                val converterAnnotations = field.annotations.filterIsInstance<Converter>()
                val fieldName = field.name

                if (converterAnnotations.isEmpty()) {
                    throw ConverterAnnotationRequiredException(typeName, fieldName)
                }

                val columnAnnotation = columnAnnotations.first()
                val converterAnnotation = converterAnnotations.first()
                val converterInstance =
                    converterAnnotation.typeConverter.constructors.first().call()
                field.isAccessible = true
                columnFieldMappings.add(
                    ColumnFieldMapping(
                        columnName = columnAnnotation.columnName,
                        field = field,
                        typeConverter = converterInstance
                    )
                )
            }

            return SheetMapping(
                sheetName = sheetNameAnnotation.sheetName,
                dataStartRowNo = sheetNameAnnotation.startRow,
                columnFieldMappings = columnFieldMappings
            )
        }
    }
}
