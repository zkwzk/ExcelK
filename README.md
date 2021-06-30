# ExcelK
ExcelK is a library writen by Kotlin which provide a declaration way for extracting the data from an excel to a DAO model

## Usage
Let say we have below Excel file

**Sheet1**

|id|name|age|class|
|---|---|---|---|
|1|aaa|19|A1|
|2|bbb|20|A2|
|3|ccc|21|A3|
|4|ddd|22|A4|

you just need a class as the DAO model with some annotations to indicate the mapping between the Excel column and the property of the class:
```kotlin
@SheetName("Sheet1")
class HelloWorldDAO(
    @Column("A")
    @Converter(IntConverter::class)
    var id:  Int = 0,

    @Column("B")
    @Converter(StringConverter::class)
    var name: String = "",

    @Column("C")
    @Converter(IntConverter::class)
    var age: Int = 0,

    @Column("D")
    @Converter(StringConverter::class)
    var className: String = "",
)
```
with a few lines of code
```kotlin
val workbook = ExcelReader.read("src/test/resources/helloworld.xls")
val sheetMapping = MetaDataAnalyzer.analyze(HelloWorldDAO::class)
val sheet = workbook.getSheet(sheetMapping.sheetName)
val result = SheetConverter.convert(sheet, sheetMapping)
```

Then you will get a list which including the converted DAO, as well as the error messages during the converting

After you got that DAO list, you can convert it to a few different formats like JSON or SQL script

## For Developer
### Config the pre-push hook
```bash
git config --local core.hooksPath .githooks/
chmod 700 .githooks/pre-push
```
