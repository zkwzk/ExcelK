package zkwang.excelk.utils

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows


internal class TopologicalSortingUtilTest {
    @Test
    fun `should return correct sorted list`() {
        /* 5 -> 0
           5 -> 2 -> 3 -> 1
           4 -> 0
           4 -> 1
         */
        val dag = mapOf(
            "2" to listOf("5"),
            "0" to listOf("5", "4"),
            "1" to listOf("3", "4"),
            "5" to emptyList(),
            "4" to emptyList(),
            "3" to listOf("2")
        )

        assertDoesNotThrow { TopologicalSortingUtil.sort(dag) }.also {
            assertThat(it).containsExactly(
                "5", "4", "2", "0", "3", "1"
            )
        }
    }

    @Test
    fun `should throw exception if exist cycle`() {
        /*  1 -> 5 -> 0
            5 -> 2 -> 3 -> 1
            4 -> 0
            4 -> 1
          */
        val dag = mapOf(
            "2" to listOf("5"),
            "0" to listOf("5", "4"),
            "1" to listOf("3", "4"),
            "5" to listOf("1"),
            "4" to emptyList(),
            "3" to listOf("2")
        )

        assertThrows<IllegalArgumentException> { TopologicalSortingUtil.sort(dag) }.also {
            assertThat(it.message).isEqualTo(
                "There a loops in the graph, check nodes [2, 0, 1, 5, 3]"
            )
        }
    }
}
