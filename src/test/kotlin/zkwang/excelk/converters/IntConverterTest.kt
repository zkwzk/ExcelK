package zkwang.excelk.converters

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import zkwang.excelk.ASheet
import zkwang.excelk.getColumnContext
import zkwang.excelk.getRowContext
import zkwang.excelk.models.ColumnContext
import zkwang.excelk.models.RowContext
import zkwang.excelk.utils.ErrorMessageBuilder.getConvertFailedErrorMsg

internal class IntConverterTest {
    private val target: IntConverter = IntConverter()
    private lateinit var columnContext: ColumnContext
    private lateinit var rowContext: RowContext

    @BeforeEach
    private fun setUp() {
        columnContext = getColumnContext(ASheet::aInt)
        rowContext = getRowContext()
    }

    @Test
    fun `should convert string to int successfully`() {
        target.convert("1", columnContext, rowContext)
        assertThat((rowContext.modelInstance as ASheet).aInt).isEqualTo(1)
        assertThat((rowContext.columnValueMap.keys)).hasSize(1)
        assertThat((rowContext.errorMessageMap.keys)).hasSize(0)
    }

    @Test
    fun `should return null if origin text is empty`() {
        target.convert("", columnContext, rowContext)
        assertThat((rowContext.modelInstance as ASheet).aInt).isNull()
        assertThat((rowContext.columnValueMap.keys)).hasSize(1)
        assertThat((rowContext.columnValueMap[rowContext.columnValueMap.keys.first()]!!.value)).isNull()
        assertThat((rowContext.errorMessageMap.keys)).hasSize(1)
        assertThat((rowContext.errorMessageMap[rowContext.errorMessageMap.keys.first()])).contains(
            getConvertFailedErrorMsg(columnContext.columnName, rowContext.rowNumber, "")
        )
    }

    @Test
    fun `should return null if origin text is not number`() {
        target.convert("1ba", columnContext, rowContext)
        assertThat((rowContext.modelInstance as ASheet).aInt).isNull()
        assertThat((rowContext.columnValueMap.keys)).hasSize(1)
        assertThat((rowContext.columnValueMap[rowContext.columnValueMap.keys.first()]!!.value)).isNull()
        assertThat((rowContext.errorMessageMap.keys)).hasSize(1)
        assertThat((rowContext.errorMessageMap[rowContext.errorMessageMap.keys.first()])).contains(
            getConvertFailedErrorMsg(columnContext.columnName, rowContext.rowNumber, "")
        )
    }
}
