package zkwang.excelk.analyzers

import zkwang.excelk.annotations.Column
import zkwang.excelk.annotations.Converter
import zkwang.excelk.annotations.SheetName
import zkwang.excelk.exceptions.ConverterAnnotationRequiredException
import zkwang.excelk.exceptions.SheetNameAnnotationRequiredException
import zkwang.excelk.models.ColumnFieldMapping
import zkwang.excelk.models.SheetMapping
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation

class MetaDataAnalyzer {
    companion object {
        inline fun <reified T : Any> analyze(modelType: KClass<T>): SheetMapping<T> {
            val sheetNameAnnotations = modelType.annotations.filterIsInstance<SheetName>()
            val typeName = modelType.simpleName!!
            if (sheetNameAnnotations.isEmpty()) {
                throw SheetNameAnnotationRequiredException(typeName)
            }

            val sheetNameAnnotation = sheetNameAnnotations.first()
            val columnFieldMappings: MutableList<ColumnFieldMapping> = mutableListOf()
            for (member in modelType.declaredMemberProperties.filterIsInstance<KMutableProperty<*>>()) {
                val columnAnnotation = member.findAnnotation<Column>() ?: continue

                val converterAnnotation = member.findAnnotation<Converter>()
                val fieldName = member.name

                if (converterAnnotation == null) {
                    throw ConverterAnnotationRequiredException(typeName, fieldName)
                }

                val converterInstance =
                    converterAnnotation.typeConverter.createInstance()
                columnFieldMappings.add(
                    ColumnFieldMapping(
                        columnName = columnAnnotation.columnName,
                        field = member,
                        typeConverter = converterInstance
                    )
                )
            }

            return SheetMapping(
                modelType = modelType,
                sheetName = sheetNameAnnotation.sheetName,
                dataStartRowNo = sheetNameAnnotation.startRow,
                dataEndRowNo = sheetNameAnnotation.endRow,
                columnFieldMappings = columnFieldMappings
            )
        }
    }
}
