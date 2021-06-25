package zkwang.excelk.converters

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import zkwang.excelk.ASheet
import zkwang.excelk.getColumnContext
import zkwang.excelk.getRowContext
import zkwang.excelk.models.ColumnContext
import zkwang.excelk.models.RowContext

internal class StringConverterTest {
    private val target: StringConverter = StringConverter()
    private lateinit var columnContext: ColumnContext
    private lateinit var rowContext: RowContext

    @BeforeEach
    private fun setUp() {
        columnContext = getColumnContext(ASheet::aString)
        rowContext = getRowContext()
    }

    @Test
    fun `should return what input`() {
        target.convert("1", columnContext, rowContext)
        assertThat((rowContext.modelInstance as ASheet).aString).isEqualTo("1")
    }
}
