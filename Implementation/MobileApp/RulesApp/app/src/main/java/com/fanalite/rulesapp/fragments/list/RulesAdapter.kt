package com.fanalite.rulesapp.fragments.list

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fanalite.rulesapp.databinding.ItemRuleLayoutBinding
import com.fanalite.rulesapp.models.RegexModel

/**
 * An adapter class for RulesList adapter.
 */
open class RulesListAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
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
                binding.tvItemName.text = name
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