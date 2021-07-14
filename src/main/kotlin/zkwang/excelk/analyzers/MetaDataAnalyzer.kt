package zkwang.excelk.analyzers

import zkwang.excelk.annotations.Column
import zkwang.excelk.annotations.Converter
import zkwang.excelk.annotations.DependsOnColumns
import zkwang.excelk.annotations.SheetName
import zkwang.excelk.exceptions.ConverterAnnotationRequiredException
import zkwang.excelk.exceptions.SheetNameAnnotationRequiredException
import zkwang.excelk.models.ColumnFieldMapping
import zkwang.excelk.models.SheetMapping
import zkwang.excelk.utils.TopologicalSortingUtil
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
            var columnFieldMappings: MutableList<ColumnFieldMapping> = mutableListOf()
            for (member in modelType.declaredMemberProperties.filterIsInstance<KMutableProperty<*>>()) {
                val columnAnnotation = member.findAnnotation<Column>() ?: continue

                val converterAnnotation = member.findAnnotation<Converter>()
                val fieldName = member.name

                if (converterAnnotation == null) {
                    throw ConverterAnnotationRequiredException(typeName, fieldName)
                }

                val dependsOnColumnsAnnotation = member.findAnnotation<DependsOnColumns>()
                var dependsOnColumns = emptyList<String>()
                if (dependsOnColumnsAnnotation != null) {
                    dependsOnColumns = dependsOnColumnsAnnotation.columnNames.toList()
                }

                val converterInstance =
                    converterAnnotation.typeConverter.createInstance()
                columnFieldMappings.add(
                    ColumnFieldMapping(
                        columnName = columnAnnotation.columnName,
                        field = member,
                        typeConverter = converterInstance,
                        dependsOnColumns = dependsOnColumns
                    )
                )
            }

            val dependsOnColumnDag = columnFieldMappings.associate { it.columnName to it.dependsOnColumns }

            val sortedColumns =
                TopologicalSortingUtil.sort(dependsOnColumnDag)
            columnFieldMappings = sortByList(columnFieldMappings, sortedColumns)

            return SheetMapping(
                modelType = modelType,
                sheetName = sheetNameAnnotation.sheetName,
                dataStartRowNo = sheetNameAnnotation.startRow,
                dataEndRowNo = sheetNameAnnotation.endRow,
                columnFieldMappings = columnFieldMappings
            )
        }

        fun sortByList(
            originList: List<ColumnFieldMapping>,
            byList: List<String>
        ): MutableList<ColumnFieldMapping> {
            val originMap = originList.associateBy { it.columnName }.toMap()
            val sortedList = mutableListOf<ColumnFieldMapping>()
            for (columnName in byList) {
                if (originMap.containsKey(columnName)) {
                    sortedList.add(originMap[columnName]!!)
                }
            }

            return sortedList
        }
    }
}
