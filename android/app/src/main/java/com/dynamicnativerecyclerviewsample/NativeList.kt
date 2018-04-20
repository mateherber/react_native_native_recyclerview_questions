package com.dynamicnativerecyclerviewsample

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import com.facebook.react.bridge.ReactContext
import com.facebook.react.uimanager.UIManagerModule
import org.jetbrains.anko.backgroundColor

internal class NativeList @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {
    private var firstRun = true
    private var requestedLayout = false

    private val listAdapter = NativeListAdapter()

    init {
        adapter = listAdapter
        backgroundColor = Color.YELLOW
        layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
        subscribeToDataChanges()
    }

    private fun subscribeToDataChanges() {
        NativeListRepository.list.subscribe {
            requestedLayout = false
            listAdapter.submitList(it)

            if (!firstRun) {
                /**
                 * Without this hack we can't get the Yoga engine to trigger the ShadowNode size calculation
                 * This is really slow (probably due to dispatching)
                 */
                (context as? ReactContext)?.apply {
                    runOnNativeModulesQueueThread {
                        getNativeModule(UIManagerModule::class.java).invalidateNodeLayout(id)
                    }
                }
            } else {
                firstRun = false
            }
        }
    }

    /**
     * Without this hack our RecyclerView item UI (color, size)
     * are not being updated even if we update the date source
     * It gets updated however if we start scrolling
     */
    @SuppressLint("WrongCall")
    override fun requestLayout() {
        super.requestLayout()
        if (!requestedLayout) {
            requestedLayout = true
            this.post {
                requestedLayout = false
                layout(left, top, right, bottom)
                onLayout(false, left, top, right, bottom)
            }
        }
    }
}

