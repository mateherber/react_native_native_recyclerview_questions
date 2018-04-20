package com.dynamicnativerecyclerviewsample

import com.facebook.react.uimanager.LayoutShadowNode
import com.facebook.yoga.YogaMeasureFunction
import com.facebook.yoga.YogaMeasureMode
import com.facebook.yoga.YogaMeasureOutput
import com.facebook.yoga.YogaNode
import org.jetbrains.anko.dip

class NativeListShadowNode : LayoutShadowNode(), YogaMeasureFunction {
    init {
        setMeasureFunction(this)
    }

    /**
     * It is not clear what should we give as value to the width of the control
     * This way when we have alignItems: 'center' in Yoga layout ScrollView is just not there
     * Should we use Screen width instead?
     */
    override fun measure(
        node: YogaNode,
        width: Float,
        widthMode: YogaMeasureMode,
        height: Float,
        heightMode: YogaMeasureMode
    ): Long {
        val list = NativeListRepository.list.value?.map { it.first } ?: return YogaMeasureOutput.make(0, 0)
        return YogaMeasureOutput.make(
            list.size * (themedContext.dip(10 + 10 + 100)),
            themedContext.dip((list.max() ?: 0) + 10 + 10)
        )
    }
}