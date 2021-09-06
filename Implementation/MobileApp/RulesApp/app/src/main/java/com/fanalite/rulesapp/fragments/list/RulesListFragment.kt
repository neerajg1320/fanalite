package com.fanalite.rulesapp.fragments.list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.fanalite.rulesapp.R
import com.fanalite.rulesapp.TAG
import com.fanalite.rulesapp.databinding.FragmentRulesListBinding
import com.fanalite.rulesapp.viewmodels.RegexViewModel



/**
 * A simple [Fragment] subclass.
 * Use the [RulesListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RulesListFragment : Fragment() {
    private var _binding: FragmentRulesListBinding? = null
    private val binding get() = _binding!!

    private val mRegexViewModel: RegexViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRulesListBinding.inflate(inflater, container, false)

        mRegexViewModel.getRegexList().observe(viewLifecycleOwner, Observer { dataList ->
            Log.d(TAG, "mRegexViewModel.Observer(): dataList:size= ${dataList.size}")

        })

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_rulesListFragment_to_addRuleFragment)
        }

        return binding.root
    }


}