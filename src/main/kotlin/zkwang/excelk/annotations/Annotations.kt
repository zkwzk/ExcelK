package zkwang.excelk.annotations

import zkwang.excelk.converters.TypeConverter
import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.PROPERTY)
annotation class Column(val columnName: String)

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class SheetName(val sheetName: String, val startRow: Int = 2, val endRow: Int = -1)

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.PROPERTY)
annotation class Converter(val typeConverter: KClass<out TypeConverter<*>>)
