package zkwang.excelk

import zkwang.excelk.annotations.Column
import zkwang.excelk.annotations.Converter
import zkwang.excelk.annotations.SheetName
import zkwang.excelk.converters.IntConverter
import zkwang.excelk.converters.StringConverter
import zkwang.excelk.models.ColumnContext
import zkwang.excelk.models.RowContext
import kotlin.reflect.KMutableProperty

@SheetName("SheetName", 3)
internal class ASheet(
    @Column("A")
    @Converter(IntConverter::class)
    var aInt: Int? = null,
    @Column("B")
    @Converter(StringConverter::class)
    var aString: String? = null
)

internal class ASheetWithoutSheetNameAnnotation(
    @Column("A")
    @Converter(IntConverter::class)
    var aInt: Int = 0,

    @Column("B")
    @Converter(StringConverter::class)
    var aString: String = ""
)


@SheetName("SheetName")
internal class ASheetWithOnlyColumnAnnotation(
    @Column("A")
    @Converter(IntConverter::class)
    var AInt: Int = 0,

    @Column("B")
    var AString: String = ""
)

@SheetName("SheetName")
internal class ASheetWithPropertyNoColumnAttribute(
    @Column("A")
    @Converter(IntConverter::class)
    var aInt: Int = 0,

    @Column("B")
    @Converter(StringConverter::class)
    var aString: String = "",

    var anotherInt: Int = 0,
    var anotherString: String = ""
)

fun getColumnContext(filed: KMutableProperty<*>) = ColumnContext("A", filed)
fun getRowContext() = RowContext(
    modelInstance = ASheet(),
    rowNumber = 22,
    columnValueMap = mutableMapOf(),
    errorMessageMap = mutableMapOf()
)
