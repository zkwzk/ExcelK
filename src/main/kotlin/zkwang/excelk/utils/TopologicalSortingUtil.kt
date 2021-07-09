package zkwang.excelk.utils

import java.util.LinkedList
import java.util.Queue

object TopologicalSortingUtil {
    fun sort(dag: Map<String, List<String>>): List<String> {
        val mutableDag = dag.keys.associateWith { dag[it]!!.toMutableList() }.toMutableMap()
        val zeroIndegreeNodes = dag.keys.filter { dag[it]!!.isEmpty() }
        val queue: Queue<String> = LinkedList(zeroIndegreeNodes)
        zeroIndegreeNodes.forEach { mutableDag.remove(it) }
        val sortedList = mutableListOf<String>()
        while (queue.any()) {
            val node = queue.remove()
            mutableDag.remove(node)
            sortedList.add(node)
            mutableDag.keys.forEach {
                mutableDag[it]!!.remove(node)
                if(mutableDag[it]!!.size == 0 && !queue.contains(it)) {
                    queue.add(it)
                }
            }
        }

        if (sortedList.size != dag.keys.size) {
            throw IllegalArgumentException("There a loops in the graph, check nodes ${mutableDag.keys}")
        }

        return sortedList
    }
}
