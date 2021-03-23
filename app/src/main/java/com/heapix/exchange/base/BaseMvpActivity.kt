package com.heapix.exchange.base

import android.os.Bundle
import android.widget.Toast
import com.heapix.exchange.R

abstract class BaseMvpActivity : MvpActivity(), BaseMvpView {

    open fun onPreCreate() {}

    abstract fun getLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onPreCreate()
        setContentView(getLayoutId())

        onCreateActivity(savedInstanceState)
    }

    abstract fun onCreateActivity(savedInstanceState: Bundle?)

    override fun showMessage(resId: Int) =
        Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show()

    override fun showMessage(msg: String?) =
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

    override fun handleRestError(e: Throwable) = showMessage(R.string.error_during_api_call)

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}
