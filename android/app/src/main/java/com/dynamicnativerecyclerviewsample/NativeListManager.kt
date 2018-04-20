package com.dynamicnativerecyclerviewsample

import com.facebook.react.uimanager.BaseViewManager
import com.facebook.react.uimanager.ThemedReactContext

internal class NativeListManager : BaseViewManager<NativeList, NativeListShadowNode>() {
    companion object {
        const val NAME = "NativeList"
    }

    override fun getName(): String {
        return NAME
    }

    override fun createViewInstance(reactContext: ThemedReactContext): NativeList {
        return NativeList(reactContext)
    }

    override fun getShadowNodeClass() = NativeListShadowNode::class.java

    override fun createShadowNodeInstance() = NativeListShadowNode()

    override fun updateExtraData(root: NativeList?, extraData: Any?) {
    }
}

