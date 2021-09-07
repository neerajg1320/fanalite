package com.fanalite.rulesapp.view.fragments.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.fanalite.rulesapp.R
import com.fanalite.rulesapp.databinding.ItemRuleLayoutBinding
import com.fanalite.rulesapp.models.RegexModel

/**
 * An adapter class for RulesList adapter.
 */
open class RulesListAdapter(val fragment: RulesListFragment) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private class RuleViewHolder(val binding: ItemRuleLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    private var rulesList = emptyList<RegexModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val binding = ItemRuleLayoutBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return RuleViewHolder(binding)
    }

//    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with (holder as RuleViewHolder) {
            with (rulesList[position]) {
                binding.tvItemName.text = title

                binding.ibDeleteProduct.setOnClickListener {
                    fragment.deleteRule(rulesList[position])
                }

                binding.ruleItem.setOnClickListener {
                    val regexModel = rulesList[position]
                    val action = RulesListFragmentDirections.actionRulesListFragmentToUpdateRuleFragment(regexModel)
                    binding.root.findNavController().navigate(action)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return rulesList.size
    }

    fun setData(rules: List<RegexModel>) {
        this.rulesList = rules
        notifyDataSetChanged()
    }
}