package com.bibbidi.habittracker.ui.common.delegate

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class FragmentViewBindingDelegate<VB : ViewBinding>(
    val fragment: Fragment,
    val viewBindingFactory: (View) -> VB
) : ReadOnlyProperty<Fragment, VB> {
    private var binding: VB? = null

    init {
        fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
            val viewLifecycleOwnerLiveDataObserver =
                Observer<LifecycleOwner?> { viewLifecycleOwner ->
                    if (viewLifecycleOwner == null) {
                        binding = null
                    }
                }

            override fun onCreate(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.observeForever(viewLifecycleOwnerLiveDataObserver)
            }

            override fun onDestroy(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.removeObserver(viewLifecycleOwnerLiveDataObserver)
            }
        })
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): VB {
        binding?.let {
            if (it.root == thisRef.view) {
                return it
            }
        }

        thisRef.view?.let { view ->
            return viewBindingFactory(view).also { this.binding = it }
        }

        error("Should not attempt to get bindings when Fragment views are destroyed")
    }
}
