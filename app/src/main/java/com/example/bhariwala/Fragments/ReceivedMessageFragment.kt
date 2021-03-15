package com.example.bhariwala.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bhariwala.R

/**
 * A simple [Fragment] subclass.
 * Use the [ReceivedMessageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReceivedMessageFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_received_message, container, false)
    }

}