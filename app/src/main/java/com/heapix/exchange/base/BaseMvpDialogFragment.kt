package com.heapix.exchange.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.arellomobile.mvp.MvpDialogFragment

abstract class BaseMvpDialogFragment : MvpDialogFragment(),
    BaseMvpView {

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun onViewCreated(view: View)

    override fun onCreateView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater?.inflate(
        getLayoutId(), container, false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initWindowParams()
        onViewCreated(view)
    }

    private fun initWindowParams() = dialog.window?.apply {
        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun showMessage(resId: Int) {
        (activity as? BaseMvpActivity)?.showMessage(resId)
    }

    override fun showMessage(msg: String?) {
        (activity as? BaseMvpActivity)?.showMessage(msg)
    }

    override fun handleRestError(e: Throwable) {
        (activity as? BaseMvpActivity)?.handleRestError(e)
    }

//    override fun requestPermissions(vararg permissions: String, listener: OnPermissionsListener) {
//        (activity as? BaseMvpActivity)?.requestPermissions(*permissions, listener = listener)
//    }

//    override fun hideKeyboard() {
//        (activity as? BaseMvpActivity)?.hideKeyboard()
//    }
}