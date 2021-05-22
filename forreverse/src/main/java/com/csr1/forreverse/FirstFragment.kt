package com.csr1.forreverse

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FirstFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_first, container, false)
        val tv = v.findViewById<TextView>(R.id.tv_first)

        val mainViewModel = ViewModelProvider(
                requireActivity(), ViewModelProvider.AndroidViewModelFactory.getInstance(
                requireActivity().application)).get(MainViewModel::class.java)
        tv.setOnClickListener { mainViewModel.dummyDataLive.apply { value = value?.plus(1) } }
        mainViewModel.dummyDataLive.observe(viewLifecycleOwner, Observer { tv.setText("" + mainViewModel.dummyDataLive.value) })
        return v
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                FirstFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}