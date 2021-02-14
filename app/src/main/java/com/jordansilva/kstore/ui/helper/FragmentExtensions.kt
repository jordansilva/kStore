package com.jordansilva.kstore.ui.helper

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.jordansilva.kstore.R

fun Fragment.navigateTo(fragment: Fragment, tag: String, sharedElements: List<Pair<View, String>> = emptyList(), shouldAddToBackStack: Boolean = true) {
    parentFragmentManager.commit {
        setReorderingAllowed(true)
        sharedElements.forEach { (view, value) -> addSharedElement(view, value) }
        replace(R.id.container, fragment, tag)
        if (shouldAddToBackStack) addToBackStack(null)
    }
}