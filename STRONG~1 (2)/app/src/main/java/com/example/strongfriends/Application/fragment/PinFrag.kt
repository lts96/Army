package com.example.strongfriends.Application.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.strongfriends.R


class PinFrag : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pin, container, false)
    }

    companion object {
        fun newInstance(): PinFrag {
            val fragment = PinFrag()
            return fragment
        }
    }

    interface callPinListener {

        fun changeToPin()

    }


}
