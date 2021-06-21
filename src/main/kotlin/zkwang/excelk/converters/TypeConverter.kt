package zkwang.excelk.converters

interface TypeConverter<T> {
    fun convert(originText: String): T
}
