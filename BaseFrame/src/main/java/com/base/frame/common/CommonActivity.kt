package com.base.frame.common

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.view.View
import android.view.WindowManager
import com.base.frame.utils.SystemBarTintManager

open class CommonActivity : FragmentActivity() {

    var color: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (color != -1) {
            initSystemBar(color)
        }
    }

    private fun initSystemBar(color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0 全透明实现
            window.setBackgroundDrawable(null)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE//是否全透明，5.0以上默认半透明
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            val defColor: Int = resources.getColor(color)
            window.statusBarColor = defColor//app主色调
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4 全透明状态栏
            // 状态栏透明 需要在创建SystemBarTintManager 之前调用。
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            setTranslucentStatus(true)
            val tintManager = SystemBarTintManager(this)
            tintManager.isStatusBarTintEnabled = true
            // 使StatusBarTintView 和 actionbar的颜色保持一致，风格统一。
            tintManager.setStatusBarTintResource(color)//app主色调
            // 设置状态栏的文字颜色
            tintManager.setStatusBarDarkMode(false, this)
        }
    }

    @TargetApi(19)
    private fun setTranslucentStatus(on: Boolean) {
        val winParams: WindowManager.LayoutParams = window.attributes
        val bits: Int = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        window.attributes = winParams
    }

    /**
     * 返回上一层，结束当前
     */
    open fun back(view: View) {
        onBackPressed()
    }
}