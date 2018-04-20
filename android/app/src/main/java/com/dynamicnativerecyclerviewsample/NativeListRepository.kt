package com.dynamicnativerecyclerviewsample

import android.graphics.Color
import io.reactivex.subjects.BehaviorSubject
import java.util.*

object NativeListRepository {
    private val random = Random()

    val list: BehaviorSubject<List<Pair<Int, Int>>> =
        BehaviorSubject.createDefault(listOf(25, 100, 50, 40, 110, 70, 250, 100, 100, 50, 100).map { it to Color.RED })

    /**
     * Simulating Data Source update which triggers RecyclerView changes
     */
    fun changeItem(selectedIndex: Int) {
        val currentList = list.value ?: return
        val newList = currentList.mapIndexed { index, item ->
            if (index == selectedIndex) {
                item.copy(
                    random.nextInt(351) + 50,
                    Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256))
                )
            } else {
                item
            }
        }
        list.onNext(newList)
    }
}