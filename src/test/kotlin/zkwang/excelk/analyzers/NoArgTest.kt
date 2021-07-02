package zkwang.excelk.analyzers

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import zkwang.excelk.ASheet
import zkwang.excelk.ASheetWithoutSheetNameAnnotation

internal class NoArgTest {
    @Test
    fun `should build a no arg constructor with NoArg annotation`() {
        assertDoesNotThrow { ASheetWithoutSheetNameAnnotation::class.java.getDeclaredConstructor().newInstance() }.also {
            assertThat(it.aInt).isEqualTo(0)
            assertThat(it.aString).isNull()
        }
    }

    @Test
    fun `should build a no arg constructor with SheetName annotation`() {
        assertDoesNotThrow { ASheet::class.java.getDeclaredConstructor().newInstance() }.also {
            assertThat(it.aString).isNull()
            assertThat(it.aInt).isNull()
        }
    }
}
