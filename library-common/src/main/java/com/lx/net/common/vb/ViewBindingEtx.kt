package com.lx.net.common.vb

import android.view.LayoutInflater
import android.view.View
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

/**
 * AppCompatActivity中使用 example
 *
 * class MainActivity : AppCompatActivity() {
 *
 *    private val binding by viewBinding(ActivityMainBinding::inflate)
 *
 *    override fun onCreate(savedInstanceState: Bundle?) {
 *        super.onCreate(savedInstanceState)
 *        binding.textHome.text = "example"
 *    }
 * }
 */
@MainThread
inline fun <reified VB : ViewBinding> AppCompatActivity.viewBinding(
    noinline inflater: (LayoutInflater) -> VB
) = lazy {
    inflater(layoutInflater).also { setContentView(it.root) }
}

@MainThread
inline fun <reified VB : ViewBinding> AppCompatActivity.viewBinding2(
    noinline inflater: (LayoutInflater) -> VB
) = lazy {
    inflater(layoutInflater)
}

/**
 *  Fragment中使用   example
 *
 *  class HomeFragment : Fragment(R.layout.fragment_home) {
 *
 *      private val binding by viewBinding(FragmentHomeBinding::bind)
 *
 *       override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
 *          super.onViewCreated(view, savedInstanceState)
 *            binding.textHome.text = "example"
 *
 *       }
 * }
 */
@MainThread
inline fun <reified VB : ViewBinding> viewBinding(noinline bindView: (View) -> VB) =
    FragmentViewBindingDelegate(bindView)