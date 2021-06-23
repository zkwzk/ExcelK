package zkwang.excelk.converters

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class IntConverterTest {
    private val target: IntConverter = IntConverter()

    @Test
    fun `should convert string to int successfully`() {
        assertThat(target.convert("1")).isEqualTo(1)
    }

    @Test
    fun `should return null if origin text is empty`() {
        assertThat(target.convert("")).isNull()
    }

    @Test
    fun `should return null if origin text is not number`() {
        assertThat(target.convert("1ba")).isNull()
    }
}
