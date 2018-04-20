package com.dynamicnativerecyclerviewsample

import android.app.Application
import com.facebook.react.ReactApplication
import com.facebook.react.ReactNativeHost
import com.facebook.react.ReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.shell.MainReactPackage
import com.facebook.react.uimanager.ViewManager
import com.facebook.soloader.SoLoader

class MainApplication : Application(), ReactApplication {
    private val mReactNativeHost = object : ReactNativeHost(this) {
        override fun getUseDeveloperSupport(): Boolean {
            return BuildConfig.DEBUG
        }

        override fun getPackages(): List<ReactPackage> {
            return listOf(
                MainReactPackage(),
                object : ReactPackage {
                    override fun createNativeModules(reactContext: ReactApplicationContext): List<NativeModule> {
                        return emptyList()
                    }

                    override fun createViewManagers(reactContext: ReactApplicationContext): List<ViewManager<*, *>> {
                        return listOf(NativeListManager())
                    }
                }
            )
        }

        override fun getJSMainModuleName(): String {
            return "index"
        }
    }

    override fun getReactNativeHost(): ReactNativeHost {
        return mReactNativeHost
    }

    override fun onCreate() {
        super.onCreate()
        SoLoader.init(this, /* native exopackage */ false)
    }
}
