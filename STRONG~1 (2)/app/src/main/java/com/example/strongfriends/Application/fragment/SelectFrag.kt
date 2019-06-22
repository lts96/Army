package com.example.strongfriends.Application.fragment


import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.strongfriends.R
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_select.*
import kotlinx.android.synthetic.main.fragment_sign.*

class SelectFrag : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select, container, false)
    }

    companion object{
        fun newInstance(): SelectFrag {
            val fragment=SelectFrag()
            return fragment
        }
    }

    interface callSelectListener {
        // TODO: Update argument type and name
        fun selectToLogin()
        fun selectToSign()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setListener()
    }

    fun setListener(){
        gotoLogin.setOnClickListener {
            (activity as SelectFrag.callSelectListener).selectToLogin()
        }
        gotoSign.setOnClickListener {
            (activity as SelectFrag.callSelectListener).selectToSign()
        }
    }

}
