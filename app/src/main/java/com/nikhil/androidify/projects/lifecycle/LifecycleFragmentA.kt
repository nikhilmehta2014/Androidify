package com.nikhil.androidify.projects.lifecycle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nikhil.androidify.R
import com.nikhil.androidify.databinding.FragmentLifecycleABinding
import timber.log.Timber

class LifecycleFragmentA : Fragment() {

    private var _binding: FragmentLifecycleABinding? = null
    private val binding
        get() = _binding!!

    companion object {

        private const val TAG = "LifecycleFragmentA"

        @JvmStatic
        fun newInstance() = LifecycleFragmentA()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("$TAG -> onCreate()")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Timber.d("$TAG -> onCreateView()")
        _binding = FragmentLifecycleABinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("$TAG -> onViewCreated()")

        setClickListeners()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Timber.d("$TAG -> onActivityCreated()")
    }

    override fun onStart() {
        super.onStart()
        Timber.d("$TAG -> onStart()")
    }

    override fun onResume() {
        super.onResume()
        Timber.d("$TAG -> onResume()")
    }

    override fun onPause() {
        super.onPause()
        Timber.d("$TAG -> onPause()")
    }

    override fun onStop() {
        super.onStop()
        Timber.d("$TAG -> onStop()")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.d("$TAG -> onDestroyView()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("$TAG -> onDestroy()")
    }

    override fun onDetach() {
        super.onDetach()
        Timber.d("$TAG -> onDetach()")
    }

    private fun setClickListeners() {
        _binding?.btnFragA?.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.contentFrame, LifecycleFragmentB.newInstance())
                .commit()
        }
    }
}