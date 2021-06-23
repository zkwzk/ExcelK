package zkwang.excelk.converters

class IntConverter : BaseConverter<Int>() {
    override fun executeConvert(originText: String): Int =
        originText.toInt()
}
