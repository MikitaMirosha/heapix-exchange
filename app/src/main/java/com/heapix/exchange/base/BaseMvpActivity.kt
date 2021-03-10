package com.heapix.exchange.base

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.Toast
import androidx.core.content.ContextCompat

abstract class BaseMvpActivity : MvpActivity(),
    BaseMvpView {

    companion object {
        const val RESULT_KEY_1 = "result1"
        const val RESULT_KEY_2 = "result2"
    }


    abstract fun getLayoutId(): Int

    val statusBarHeight: Int by lazy {
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")

        return@lazy if (resourceId > 0) {
            resources.getDimensionPixelSize(resourceId)
        } else {
            0
        }
    }

    open fun onPreCreate() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
//                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//
//        window.statusBarColor = Color.TRANSPARENT
        onPreCreate()
        setContentView(getLayoutId())
//        val transition = TransitionInflater.from(this).inflateTransition(R.transition.move)
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            window?.sharedElementEnterTransition = transition
//            window?.sharedElementExitTransition = transition
//            window?.sharedElementReturnTransition = transition
//        }

//        initViews()
//        initListeners()

        onCreateActivity(savedInstanceState)
    }

    abstract fun onCreateActivity(savedInstanceState: Bundle?)

//    private fun initListeners() {
//        findViewById<ImageView>(R.id.vIvBack)?.setOnClickListener {
//            finish()
//        }
//    }

//    @SuppressLint("ClickableViewAccessibility")
//    private fun initViews() {
//        val rootLayout = findViewById<View>(R.id.vRootLayout)
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

//    override fun startActivity(intent: Intent?) {
//        super.startActivity(intent)
//        overridePendingTransition(R.anim.side_sheet_slide_in, R.anim.no_animation)
//    }
//
//    fun startActivityNoAnimation(intent: Intent?) {
//        super.startActivity(intent)
//        overridePendingTransition(R.anim.no_animation, R.anim.no_animation)
//    }
//
//    override fun startActivityForResult(intent: Intent?, requestCode: Int) {
//        super.startActivityForResult(intent, requestCode)
//        overridePendingTransition(R.anim.side_sheet_slide_in, R.anim.no_animation)
//    }
//
//    override fun startActivityForResult(intent: Intent?, requestCode: Int, options: Bundle?) {
//        super.startActivityForResult(intent, requestCode, options)
//        overridePendingTransition(R.anim.side_sheet_slide_in, R.anim.no_animation)
//    }
//
//    override fun startActivity(intent: Intent?, options: Bundle?) {
//        super.startActivity(intent, options)
//        overridePendingTransition(R.anim.side_sheet_slide_in, R.anim.no_animation)
//    }
//
//    override fun finish() {
//        super.finish()
//        overridePendingTransition(R.anim.no_animation, R.anim.side_sheet_slide_out)
//    }
//
//    override fun onBackPressed() {
//        super.onBackPressed()
//        overridePendingTransition(R.anim.side_sheet_slide_in_two, R.anim.side_sheet_slide_out)
//    }

//    override fun attachBaseContext(context: Context) {
//        super.attachBaseContext(CalligraphyContextWrapper.wrap(context))
//    }

    override fun showMessage(resId: Int) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show()
    }

//    override fun hideKeyboard() {
//        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        var token: IBinder? = null
//
//        currentFocus?.let {
//            if (token == null) {
//                token = it.windowToken
//            }
//        }
//
//        if (token != null) {
//            imm.hideSoftInputFromWindow(token, 0)
//        }
//    }

    override fun showMessage(msg: String?) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun handleRestError(e: Throwable) {
        showMessage("error during api call")
    }

//    override fun requestPermissions(vararg permissions: String, listener: OnPermissionsListener) {
//        askPermissions(*permissions) {
//            onGranted {
//                listener.onGranted()
//            }
//
//            onDenied {
//                listener.onDenied(it)
//            }
//
//            onNeverAskAgain {
//                //
//            }
//
//            onShowRationale {
//                it.retry()
//            }
//        }
//    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        handlePermissionsResult(requestCode, permissions, grantResults)
    }

    fun isPermissionGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            permission
        ) != PackageManager.PERMISSION_GRANTED
    }

//    fun isNetworkAvailable(): Boolean {
//        val connectivityManager =
//            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val activeNetworkInfo = connectivityManager.activeNetworkInfo
//        return activeNetworkInfo != null && activeNetworkInfo.isConnected
//    }

//    protected fun makeSceneTransition(transitionView: View, activityClass: Class<*>) {
//        val intent = Intent(this, activityClass)
//
//        intent.putExtra(Constants.KEY_TRANSITION_NAME, (transitionView.id.toString()))
//        ViewCompat.setTransitionName(transitionView, transitionView.id.toString())
//
//        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
//            this,
//            transitionView,
//            ViewCompat.getTransitionName(transitionView)!!
//        )
//        startActivity(intent, options.toBundle())
//    }

//    fun openFragment(
//        fragmentClass: Class<out BaseMvpFragment>,
//        addToBackStack: Boolean,
//        args: Bundle?, tagId: String
//    ): BaseMvpFragment? {
//        try {
//            val fragment = createFragment(fragmentClass, args, tagId)
//            val popBackStackTag = fragmentClass.name + tagId
//            val fragmentName = fragmentClass.name
//            val transaction = supportFragmentManager.beginTransaction()
//
//            transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, 0, R.anim.fade_out)
//
//            transaction.add(R.id.vContainer, fragment, fragmentName)
//            if (addToBackStack) {
//                transaction.addToBackStack(popBackStackTag)
//            }
//            transaction.commit()
//            return fragment
//        } catch (e: Exception) {
//            //Log.e(this.getClass().getName(), Log.getStackTraceString(e))
//        }
//        return null
//    }

    @Throws(Exception::class)
    fun createFragment(
        fragmentClass: Class<out BaseMvpFragment>,
        args: Bundle?,
        tagId: String
    ): BaseMvpFragment {
        val fragment = fragmentClass.newInstance()
        var bundle: Bundle? = args
        if (bundle == null) {
            bundle = Bundle()
        }
        bundle.putString("name", fragmentClass.name + tagId)
        fragment.setHasOptionsMenu(true)
        fragment.arguments = args
        return fragment
    }

    fun getDisplayWidth(): Int {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        return displayMetrics.widthPixels
    }

//    fun image(path: String?): String? {
//        if (path == null)
//            return null
//
//        return BuildConfig.IMAGES_PATH + path
//    }
//
//    override fun showProductCantBeReadyDueToTime() {
//        BaseAlertDialog(
//            this,
//            title = getString(R.string.dish_cannot_be_add_due_to_time),
//            positiveTitle = R.string.ok,
//            negativeActionIsHidden = true
//        ).show()
//    }
}

interface OnPermissionListener {
    fun onGranted()
    fun onDenied()
}