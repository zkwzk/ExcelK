package zkwang.excelk.converters

class StringConverter : BaseConverter<String>() {
    override fun executeConvert(originText: String): String = originText
}
