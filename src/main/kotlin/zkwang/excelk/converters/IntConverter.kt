package zkwang.excelk.converters

class IntConverter : TypeConverter<Int> {
    override fun convert(originText: String): Int = originText.toInt()
}
