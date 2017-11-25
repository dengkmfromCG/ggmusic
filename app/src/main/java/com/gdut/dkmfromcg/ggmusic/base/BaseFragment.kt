package com.gdut.dkmfromcg.ggmusic.base


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethod
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout


import com.gdut.dkmfromcg.ggmusic.R
import com.qmuiteam.qmui.util.QMUIViewHelper
import com.qmuiteam.qmui.widget.QMUIWindowInsetLayout


/**
 * Created by dkmFromCG on 2017/9/20.
 * function:
 */
abstract class BaseFragment : Fragment() {


    //============================= UI ================================

    private var mBaseView: View? = null


    val baseFragmentActivity: BaseFragmentActivity?
        get() = activity as BaseFragmentActivity

    val isAttachedToActivity: Boolean
        get() = !isRemoving && mBaseView != null


    protected fun startFragment(fragment: BaseFragment) {
        val baseFragmentActivity = this.baseFragmentActivity
        if (baseFragmentActivity != null) {
            if (this.isAttachedToActivity) {
                baseFragmentActivity.startFragment(fragment)
            } else {
                Log.e("BaseFragment", "fragment not attached:" + this)
            }
        } else {
            Log.e("BaseFragment", "startFragment null:" + this)
        }
    }


    /**
     * 显示键盘
     */
    protected fun showKeyBoard() {
        val imm = activity.applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(0, InputMethod.SHOW_FORCED)
    }

    /**
     * 隐藏键盘
     */
    protected fun hideKeyBoard(): Boolean {
        //		return mBaseActivityImpl.hideKeyBoard();
        val imm = activity.applicationContext
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return imm.hideSoftInputFromWindow(activity.findViewById<View>(android.R.id.content)
                .windowToken, 0)
    }

    ////////界面跳转动画
    class TransitionConfig(val enter: Int, val exit: Int=0, val popenter: Int=0, val popout: Int)


    //============================= 生命周期 ================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = onCreateView()
        if (translucentFull()) {
            if (mBaseView is QMUIWindowInsetLayout) {
                view.fitsSystemWindows = false
                mBaseView = view
            } else {
                mBaseView = QMUIWindowInsetLayout(activity)
                (mBaseView as QMUIWindowInsetLayout).addView(view, FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
            }
        } else {
            view.fitsSystemWindows = true
            mBaseView = view
        }
        QMUIViewHelper.requestApplyInsets(activity.window)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mBaseView!!.parent != null) {
            (mBaseView!!.parent as ViewGroup).removeView(mBaseView)
        }
        return mBaseView
    }

    protected fun popBackStack() {
        baseFragmentActivity!!.popBackStack()
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        if (!enter && parentFragment != null && parentFragment.isRemoving) {
            // This is a workaround for the bug where child fragments disappear when
            // the parent is removed (as all children are first removed from the parent)
            // See https://code.google.com/p/android/issues/detail?id=55228
            val doNothingAnim = AlphaAnimation(1f, 1f)
            doNothingAnim.duration = R.integer.qmui_anim_duration.toLong()
            return doNothingAnim
        }

        // bugfix: 使用scale enter时看不到效果， 因为两个fragment的动画在同一个层级，被退出动画遮挡了
        // http://stackoverflow.com/questions/13005961/fragmenttransaction-animation-to-slide-in-over-top#33816251
        if (nextAnim != R.anim.scale_enter || !enter) {
            return super.onCreateAnimation(transit, enter, nextAnim)
        }
        try {
            val nextAnimation = AnimationUtils.loadAnimation(context, nextAnim)
            nextAnimation.setAnimationListener(object : Animation.AnimationListener {

                private var mOldTranslationZ: Float = 0.toFloat()

                override fun onAnimationStart(animation: Animation) {
                    if (view != null) {
                        mOldTranslationZ = ViewCompat.getTranslationZ(view)
                        ViewCompat.setTranslationZ(view, 100f)
                    }
                }

                override fun onAnimationEnd(animation: Animation) {
                    if (view != null) {
                        view!!.postDelayed({
                            //延迟回复z-index,如果退出动画更长，这里可能会失效
                            ViewCompat.setTranslationZ(view, mOldTranslationZ)
                        }, 100)

                    }
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })
            return nextAnimation
        } catch (ignored: Exception) {

        }

        return null
    }

    //============================= 新流程 ================================

    /**
     * onCreateView

     * @return
     */
    protected abstract fun onCreateView(): View


    /**
     * 沉浸式处理，返回false，则状态栏下为内容区域; 返回true, 则状态栏下为padding区域

     * @return
     */
    protected fun translucentFull(): Boolean {
        return false
    }


    /**
     * 如果是最后一个Fragment,finish后执行的方法

     * @return
     */
    fun onLastFragmentFinish(): Any? {
        return null
    }

    /**
     * 转场动画控制

     * @return
     */
    fun onFetchTransitionConfig(): TransitionConfig {
        return SLIDE_TRANSITION_CONFIG
    }

    companion object {
        private val TAG = BaseFragment::class.java.simpleName

        // 资源，放在业务初始化，会在业务层
        protected val SLIDE_TRANSITION_CONFIG = TransitionConfig(
                R.anim.slide_in_right, R.anim.slide_out_left,
                R.anim.slide_in_left, R.anim.slide_out_right)
        protected val SCALE_TRANSITION_CONFIG = TransitionConfig(
                R.anim.scale_enter, R.anim.slide_still, R.anim.slide_still,
                R.anim.scale_exit)
    }
}

