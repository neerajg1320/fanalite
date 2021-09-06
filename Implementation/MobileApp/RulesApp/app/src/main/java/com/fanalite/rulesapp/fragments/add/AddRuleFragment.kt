package com.fanalite.rulesapp.fragments.add

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fanalite.rulesapp.TAG
import com.fanalite.rulesapp.databinding.FragmentAddRuleBinding
import com.fanalite.rulesapp.models.RegexModel


/**
 * A simple [Fragment] subclass.
 * Use the [AddRuleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddRuleFragment : Fragment() {
    private var _binding: FragmentAddRuleBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddRuleBinding.inflate(inflater, container, false)

        binding.btnRegexAdd.setOnClickListener {

            saveRegexToRepository();
        }

        return binding.root
    }

    private fun saveRegexToRepository() {
        if(validateData()) {
            val regexName: String = binding.etName.text.toString().trim { it <= ' ' }
            val regexStr: String = binding.etRegex.text.toString().trim { it <= ' ' }

            val language: String = when {
                binding.rbJava.isChecked -> {
                    "Java"
                }
                binding.rbPython.isChecked -> {
                    "Python"
                }
                else -> {
                    "Unknown"
                }
            }

            val regexModel = RegexModel(regexName, language, regexStr)
            Log.d(TAG, "regexModel: ${regexModel}")
        }
    }

    private fun validateData(): Boolean {
        return when {
            TextUtils.isEmpty(binding.etName.text.toString().trim { it <= ' ' }) -> {
                Log.d(TAG, "Please enter Rule name")
                false
            }

            TextUtils.isEmpty(binding.etRegex.text.toString().trim { it <= ' ' }) -> {
                Log.d(TAG, "Please enter Regular Expression")
                false
            }

            else -> {
                true
            }
        }
    }

}