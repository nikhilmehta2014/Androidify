package com.nikhil.androidify.launchmodes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nikhil.androidify.R
import timber.log.Timber

class NormalFragment : Fragment() {

    companion object {

        private const val TAG = "NormalFragment"

        @JvmStatic
        fun newInstance() = NormalFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("$TAG -> onCreate()")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.d("$TAG -> onCreateView()")
        return inflater.inflate(R.layout.fragment_normal, container, false)
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

    // This is not getting called, why?
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("$TAG -> onViewCreated()")
    }
}