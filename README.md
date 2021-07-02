# ExcelK
ExcelK is a library writen by Kotlin which provide a declaration way for extracting the data from an excel to a list of entity object

## Usage
Let say we have below Excel file

**Sheet1**

|id|name|age|class|
|---|---|---|---|
|1|aaa|19|A1|
|2|bbb|20|A2|
|3|ccc|21|A3|
|4|ddd|22|A4|

you just need a class as the entity with some annotations to indicate the mapping between the Excel column and the property of the class:
```kotlin
@SheetName("Sheet1")
data class HelloWorldEntity(
    @Column("A")
    @Converter(IntConverter::class)
    var id:  Int,

    @Column("B")
    @Converter(StringConverter::class)
    var name: String,

    @Column("C")
    @Converter(IntConverter::class)
    var age: Int,

    @Column("D")
    @Converter(StringConverter::class)
    var className: String,
)
```
with a few lines of code
```kotlin
val workbook = ExcelReader.read("src/test/resources/helloworld.xls")
val sheetMapping = MetaDataAnalyzer.analyze(HelloWorldEntity::class)
val sheet = workbook.getSheet(sheetMapping.sheetName)
val result = SheetConverter.convert(sheet, sheetMapping)
```

Please note that the entity class should be marked with `@SheetName` annotation, for each of the property in the entity should be mutable,  otherwise it will be ignored

Then you will get a list of `RowConvertResult<T>` which including the converted entity, as well as the error messages during the converting

```kotlin
data class RowConvertResult<T: Any>(
    val modelInstance: T,
    val rowNumber: Int,
    val columnConvertResults: Map<String, ColumnConvertResult>,
    val errorMsgMap: Map<String, String>
) {
    val isSuccess: Boolean = columnConvertResults.all { it.value.isSuccess }
}
```

After you got that entity list, you can convert it to a few different formats like JSON or SQL script

## For Developer
### Config the pre-push hook
```bash
git config --local core.hooksPath .githooks/
chmod 700 .githooks/pre-push
```
