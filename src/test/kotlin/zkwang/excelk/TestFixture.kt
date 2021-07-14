package zkwang.excelk

import zkwang.excelk.annotations.Column
import zkwang.excelk.annotations.Converter
import zkwang.excelk.annotations.DependsOnColumns
import zkwang.excelk.annotations.NoArg
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
    var aInt: Int?,
    @Column("B")
    @Converter(StringConverter::class)
    var aString: String?
)

@NoArg
internal class ASheetWithoutSheetNameAnnotation(
    @Column("A")
    @Converter(IntConverter::class)
    var aInt: Int,

    @Column("B")
    @Converter(StringConverter::class)
    var aString: String
)

@SheetName("SheetName")
internal class ASheetWithOnlyColumnAnnotation(
    @Column("A")
    @Converter(IntConverter::class)
    var AInt: Int,

    @Column("B")
    var AString: String
)

@SheetName("Sheet1")
internal class HelloWorldDAO(
    @Column("A")
    @Converter(StringConverter::class)
    var AString: String,

    @Column("B")
    @Converter(IntConverter::class)
    var AInt: Int
)

@SheetName("SheetName")
internal class ASheetWithPropertyNoColumnAttribute(
    @Column("A")
    @Converter(IntConverter::class)
    var aInt: Int,

    @Column("B")
    @Converter(StringConverter::class)
    var aString: String,

    var anotherInt: Int,
    var anotherString: String
)


@SheetName("SheetName")
internal class ASheetWithDependsOnColumn(
    @Column("A")
    @DependsOnColumns(["B", "C"])
    @Converter(IntConverter::class)
    var aInt: Int,
    @Column("B")
    @DependsOnColumns(["D"])
    @Converter(StringConverter::class)
    var aString: String,

    @Column("C")
    @Converter(StringConverter::class)
    var anotherString: String,

    @Column("D")
    @Converter(IntConverter::class)
    @DependsOnColumns(["C"])
    var anotherInt: Int
)

fun getColumnContext(filed: KMutableProperty<*>) = ColumnContext("A", filed)
fun getRowContext() = RowContext(
    modelInstance = ASheet::class.java.getDeclaredConstructor().newInstance(),
    rowNumber = 22,
    columnValueMap = mutableMapOf(),
    errorMessageMap = mutableMapOf()
)
