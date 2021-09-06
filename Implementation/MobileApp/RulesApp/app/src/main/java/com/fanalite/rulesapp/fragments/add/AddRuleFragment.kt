package com.fanalite.rulesapp.fragments.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fanalite.rulesapp.R


/**
 * A simple [Fragment] subclass.
 * Use the [AddRuleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddRuleFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_rule, container, false)
    }

}