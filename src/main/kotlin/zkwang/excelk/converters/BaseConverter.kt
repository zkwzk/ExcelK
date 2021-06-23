package zkwang.excelk.converters

sealed class BaseConverter<T> : TypeConverter<T> {
    override fun convert(originText: String): T? {
        return try {
            executeConvert(originText)
        } catch (e: Exception) {
            null
        }
    }

    protected abstract fun executeConvert(originText: String): T
}
