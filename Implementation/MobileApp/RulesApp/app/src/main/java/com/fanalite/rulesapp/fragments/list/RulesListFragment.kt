package com.fanalite.rulesapp.fragments.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.fanalite.rulesapp.R
import com.fanalite.rulesapp.databinding.FragmentRulesListBinding


/**
 * A simple [Fragment] subclass.
 * Use the [RulesListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RulesListFragment : Fragment() {
    private var _binding: FragmentRulesListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRulesListBinding.inflate(inflater, container, false)

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_rulesListFragment_to_addRuleFragment)
        }

        return binding.root
    }


}