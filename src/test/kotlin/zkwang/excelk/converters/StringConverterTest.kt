package zkwang.excelk.converters

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class StringConverterTest {
    private val target: StringConverter = StringConverter()

    @Test
    fun `should return what input`() {
        assertThat(target.convert("1")).isEqualTo("1")
        assertThat(target.convert("")).isEqualTo("")
    }
}
