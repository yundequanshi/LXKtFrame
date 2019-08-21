package com.base.frame.common

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

open class CommonFragment : Fragment(), CoroutineScope by MainScope()  {

    open var mView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mView == null) {
            initView(inflater)
        } else {
            container?.removeView(mView)
        }
        return mView
    }

    open fun initView(inflater: LayoutInflater) {

    }
}