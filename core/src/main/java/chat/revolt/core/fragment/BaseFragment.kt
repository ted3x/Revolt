/*
 * Created by Tedo Manvelidze(ted3x) on 2/9/22, 1:33 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/9/22, 1:33 AM
 */

package chat.revolt.core.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.viewbinding.ViewBinding
import chat.revolt.core.view_model.BaseViewModel
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment<VM: BaseViewModel, VB : ViewBinding>(private val viewBinder: ViewBinder<VB>) : Fragment() {

    abstract val viewModel: VM

    private var _binding: VB? = null
    protected val binding: VB
        get() = _binding
            ?: throw IllegalStateException("Binding was not created yet or already destroyed!")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = viewBinder.invoke(LayoutInflater.from(context), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.snackBarMessage.observe { showSnackBar(it) }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected fun showSnackBar(message: String, length: Int = Snackbar.LENGTH_SHORT) {
        Snackbar.make(requireView(), message, length).show()
    }

    protected fun <T> LiveData<T>.observe(callback: (T) -> Unit) {
        this.observe(viewLifecycleOwner, {
            callback.invoke(it)
        })
    }
}

private typealias ViewBinder<T> = (LayoutInflater, ViewGroup?, Boolean) -> T