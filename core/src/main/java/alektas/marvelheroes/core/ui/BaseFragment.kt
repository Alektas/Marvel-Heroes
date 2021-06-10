package alektas.marvelheroes.core.ui

import alektas.marvelheroes.core.ui.utils.viewBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<Binding : ViewBinding, VM : BaseViewModel> : Fragment() {

    protected var binding by viewBinding<Binding>()
    protected abstract val viewModel: VM

    protected abstract fun bind(inflater: LayoutInflater, container: ViewGroup?): Binding

    protected abstract val initView: Binding.(v: View) -> Unit
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onCreate()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = bind(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(binding, view)
    }
    
    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }
    
    override fun onPause() {
        super.onPause()
        viewModel.onPause()
    }
    
    override fun onStart() {
        super.onStart()
        viewModel.onStart()
    }
    
    override fun onStop() {
        super.onStop()
        viewModel.onStop()
    }

}
