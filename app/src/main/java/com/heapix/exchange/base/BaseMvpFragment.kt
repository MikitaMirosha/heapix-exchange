package com.heapix.exchange.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import com.heapix.exchange.MyApp
import com.heapix.exchange.utils.rx.SchedulerProvider
import org.kodein.di.instance

abstract class BaseMvpFragment : MvpFragment(),
    BaseMvpView {
    protected val schedulers: SchedulerProvider by MyApp.kodein.instance()

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun onViewCreated(view: View)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        initViews(view)
        onViewCreated(view)
    }

//    @SuppressLint("ClickableViewAccessibility")
//    private fun initViews(view: View) {
//        val rootLayout = view.findViewById<View>(R.id.vRootLayout)
//
//        rootLayout?.setOnTouchListener { v, event ->
//            if (event.action == MotionEvent.ACTION_DOWN) {
//                hideKeyboard()
//                return@setOnTouchListener true
//            }
//
//            return@setOnTouchListener false
//        }
//    }

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

//    override fun showKeyboard(view: View) {
//        (activity as? BaseMvpActivity)?.showSoftKeyboard(view)
//    }

    fun showKeyboard(view: View) {
        val inputMethodManager: InputMethodManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(view, 0)
    }

    fun hideKeyboard(view: View) {
        val inputMethodManager: InputMethodManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

//    fun startActivityNoAnimation(intent: Intent?) {
//        super.startActivity(intent)
//        activity?.overridePendingTransition(R.anim.no_animation, R.anim.no_animation)
//    }

    open fun scrollToTheTop() {}

//    fun image(path: String?): String? {
//        if (path == null)
//            return null
//
//        return BuildConfig.IMAGES_PATH + path
//    }

//    override fun showProductCantBeReadyDueToTime() {
//        (activity as? BaseMvpActivity)?.showProductCantBeReadyDueToTime()
//    }
}