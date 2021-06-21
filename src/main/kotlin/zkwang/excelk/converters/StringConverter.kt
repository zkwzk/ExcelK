package zkwang.excelk.converters

class StringConverter : TypeConverter<String> {
    override fun convert(originText: String): String = originText
}
