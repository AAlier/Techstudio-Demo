package com.techstudio.extensions

import android.content.Context
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.MainThread
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.techstudio.R
import com.techstudio.data.network.errors.ServerException

fun AppCompatActivity.replaceFragment(
    fragment: Fragment,
    @IdRes layoutId: Int = android.R.id.content,
    addToBackStack: Boolean = true,
    tag: String = fragment::class.java.name,
) {
    supportFragmentManager
        .beginTransaction()
        .replace(layoutId, fragment, tag)
        .apply { if (addToBackStack) addToBackStack(tag) }
        .commit()
}

fun Fragment.replaceFragment(
    fragment: Fragment,
    @IdRes layoutId: Int = android.R.id.content,
    addToBackStack: Boolean = true,
    tag: String = fragment::class.java.name
) {
    // Since it may be used by fragments that are nested and contained in child FM
    requireActivity().supportFragmentManager.beginTransaction()
        .replace(layoutId, fragment)
        .apply { if (addToBackStack) addToBackStack(tag) }
        .commitAllowingStateLoss()
}

fun Fragment.addFragment(
    fragment: Fragment,
    @IdRes layoutId: Int = android.R.id.content,
    addToBackStack: Boolean = true,
    tag: String = fragment::class.java.name
) {
    if (fragment.isAdded) return
    requireActivity().supportFragmentManager.beginTransaction()
        .add(layoutId, fragment)
        .apply { if (addToBackStack) addToBackStack(tag) }
        .commitAllowingStateLoss()
}

fun Fragment.removeFragment(
    fragment: Fragment
) {
    if (!fragment.isAdded) return
    requireActivity().supportFragmentManager.beginTransaction()
        .remove(fragment)
        .commitAllowingStateLoss()
}

@MainThread
fun <T> LiveData<T>.observeNonNull(
    owner: LifecycleOwner,
    onChanged: (T) -> Unit
): Observer<T> {
    val wrappedObserver = Observer<T> { t -> t?.let { onChanged.invoke(t) } }
    observe(owner, wrappedObserver)
    return wrappedObserver
}

fun Throwable.parse(context: Context): String {
    return if (this is ServerException) {
        return this.getErrorMessage(context)
    } else {
        context.getString(R.string.general_error_message)
    }
}

fun Fragment.toastShort(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun Fragment.toastShort(@StringRes messageRes: Int) {
    Toast.makeText(requireContext(), messageRes, Toast.LENGTH_SHORT).show()
}