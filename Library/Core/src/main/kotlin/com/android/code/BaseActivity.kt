package com.android.code

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 *
 * activity 基类
 * @date 2025/7/22
 * @author liweiliang
 */
abstract class BaseActivity<VDB : ViewDataBinding>(
    @param:LayoutRes private val layoutId: Int
) : AppCompatActivity() {

    lateinit var binding: VDB

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@BaseActivity, layoutId)

        initData()
        initView()
    }


    /**
     * 初始化数据，执行在[initView]之前。
     *
     * 如从Intent中获取数据。
     *
     */
    open fun initData() = Unit

    /**
     * 初始化View，执行在[initData]之后。
     *
     * 如对TextView赋值等。
     */
    open fun initView() = Unit

    /**
     * 为你的view设置一个默认的系统内容高度的Padding，如果你的页面有特殊需求，如内容需要单独延伸到状态栏或导航栏，按照以下代码重新即可。
     *
     * @param rootId 最外层布局的id
     */
    fun setDefaultSystemBarsPadding(@IdRes rootId: Int) {
        setViewSystemBarsPadding(rootId)
    }

    /**
     * Set view system bars padding
     * 为固定的布局位置设置padding
     * @param rootId
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    fun setViewSystemBarsPadding(@IdRes rootId: Int, left: Boolean = true, top: Boolean = true, right: Boolean = true, bottom: Boolean = true) {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(rootId)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(if (left) systemBars.left else 0, if (top) systemBars.top else 0, if (right) systemBars.right else 0, if (bottom) systemBars.bottom else 0)
            insets
        }
    }
}