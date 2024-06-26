package com.fanalite.rulesapp.view.fragments.list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fanalite.rulesapp.R
import com.fanalite.rulesapp.view.TAG
import com.fanalite.rulesapp.viewmodels.RegexViewModel
import com.fanalite.rulesapp.models.RegexModel
import com.fanalite.rulesapp.databinding.FragmentRulesListBinding


/**
 * A simple [Fragment] subclass.
 * Use the [RulesListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RulesListFragment : Fragment() {
    private var _binding: FragmentRulesListBinding? = null
    private val binding get() = _binding!!

    private val mRegexViewModel: RegexViewModel by viewModels()
    private val adapter by lazy { RulesListAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRulesListBinding.inflate(inflater, container, false)

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_rulesListFragment_to_addRuleFragment)
        }

        createRecyclerView()

        mRegexViewModel.allData?.observe(viewLifecycleOwner, Observer { dataList ->
            dataList.let {
                Log.d(TAG, "mRegexViewModel.Observer(): dataList:size= ${dataList.size}")
                it.forEach {
                    Log.d(TAG, it.toString())
                }

                if (it.isNotEmpty()) {
                    binding.ivNoData.visibility = View.GONE
                    binding.tvNoData.visibility = View.GONE
                    binding.rvRulesList.visibility = View.VISIBLE
                } else {
                    binding.ivNoData.visibility = View.VISIBLE
                    binding.tvNoData.visibility = View.VISIBLE
                    binding.rvRulesList.visibility = View.GONE
                }

                adapter.setData(it)
            }
        })

        mRegexViewModel.queryData()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun createRecyclerView() {
        val rvRules:RecyclerView = binding.rvRulesList
        rvRules.layoutManager = LinearLayoutManager(activity)
        rvRules.setHasFixedSize(true)

        // Define adapter
        rvRules.adapter = adapter
    }

    fun deleteRule(regexModel: RegexModel) {
        mRegexViewModel.deleteItem(regexModel)
    }
}