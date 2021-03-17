package com.heapix.exchange.base

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.arellomobile.mvp.MvpDelegate

abstract class MvpActivity : FragmentActivity() {

    private var mMvpDelegate: MvpDelegate<out MvpActivity>? = null

    private val mvpDelegate: MvpDelegate<out MvpActivity>
        get() {
            if (mMvpDelegate == null) {
                mMvpDelegate = MvpDelegate(this)
            }
            return mMvpDelegate!!
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mvpDelegate.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

        mvpDelegate.onAttach()
    }

    override fun onResume() {
        super.onResume()

        mvpDelegate.onAttach()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        mvpDelegate.onSaveInstanceState(outState)
        mvpDelegate.onDetach()
    }

    override fun onStop() {
        super.onStop()

        mvpDelegate.onDetach()
    }

    override fun onDestroy() {
        super.onDestroy()

        mvpDelegate.onDestroyView()

        if (isFinishing) {
            mvpDelegate.onDestroy()
        }
    }

}
