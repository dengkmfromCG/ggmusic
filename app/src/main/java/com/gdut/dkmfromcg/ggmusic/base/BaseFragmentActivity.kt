package com.gdut.dkmfromcg.ggmusic.base


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout

import com.qmuiteam.qmui.util.QMUIStatusBarHelper

/**
 * Created by dkmFromCG on 2017/9/20.
 * function:
 */
abstract class BaseFragmentActivity : AppCompatActivity() {

    protected abstract val contextViewId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        QMUIStatusBarHelper.translucent(this)
        val layout = FrameLayout(this)
        layout.id = contextViewId
        setContentView(layout)
    }

    override fun onBackPressed() {
        popBackStack()
    }

    /**
     * 获取当前fragment

     * @return
     */
    val currentFragment: BaseFragment?
        get() = supportFragmentManager.findFragmentById(contextViewId) as BaseFragment

    fun startFragment(fragment: BaseFragment) {
        val transitionConfig = fragment.onFetchTransitionConfig()
        val tagName = fragment.javaClass.simpleName
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(transitionConfig.enter, transitionConfig.exit, transitionConfig.popenter, transitionConfig.popout)
                .replace(contextViewId, fragment, tagName)
                .addToBackStack(tagName)
                .commit()
    }

    /**
     * 返回
     */
    fun popBackStack() {
        if (supportFragmentManager.backStackEntryCount <= 1) {
            val fragment = currentFragment
            if (fragment == null) {
                finish()
                return
            }
            val transitionConfig = fragment.onFetchTransitionConfig()
            val toExec = fragment.onLastFragmentFinish()
            if (toExec != null) {
                if (toExec is BaseFragment) {
                    startFragment(toExec)
                } else if (toExec is Intent) {
                    finish()
                    startActivity(toExec)
                    overridePendingTransition(transitionConfig.popenter, transitionConfig.popout)
                } else {
                    throw Error("can not handle the result in onLastFragmentFinish")
                }
            } else {
                finish()
                overridePendingTransition(transitionConfig.popenter, transitionConfig.popout)
            }
        } else {
            supportFragmentManager.popBackStackImmediate()
        }
    }

    /**
     * <pre>
     * 返回到clazz类型的Fragment，
     * 如 Home --> List --> Detail，
     * popBackStack(Home.class)之后，就是Home

     * 如果堆栈没有clazz或者就是当前的clazz（如上例的popBackStack(Detail.class)），就相当于popBackStack()
    </pre> *

     * @param clazz
     */
    fun popBackStack(clazz: Class<out BaseFragment>) {
        supportFragmentManager.popBackStack(clazz.simpleName, 0)
    }

    /**
     * <pre>
     * 返回到非clazz类型的Fragment

     * 如果上一个是目标clazz，则会继续pop，直到上一个不是clazz。
    </pre> *

     * @param clazz
     */
    fun popBackStackInclusive(clazz: Class<out BaseFragment>) {
        supportFragmentManager.popBackStack(clazz.simpleName, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }


}
