package com.fanalite.rulesapp.view.fragments.add

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fanalite.rulesapp.R
import com.fanalite.rulesapp.databinding.FragmentRuleDetailBinding
import com.fanalite.rulesapp.view.TAG
import com.fanalite.rulesapp.viewmodels.RegexViewModel
import com.fanalite.rulesapp.models.Language

import com.fanalite.rulesapp.models.RegexModel
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [AddRuleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddRuleFragment : Fragment() {
    private var _binding: FragmentRuleDetailBinding? = null
    private val binding get() = _binding!!

    private val mRegexViewModel: RegexViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRuleDetailBinding.inflate(inflater, container, false)

        binding.btnRegexAdd.setOnClickListener {

            saveRegexToRepository();
        }

        return binding.root
    }

    private fun saveRegexToRepository() {
        if(validateData()) {
            val regexName: String = binding.etName.text.toString().trim { it <= ' ' }
            val regexStr: String = binding.etRegex.text.toString().trim { it <= ' ' }

            val language: Language = when {
                binding.rbJava.isChecked -> {
                    Language.JAVA
                }
                binding.rbPython.isChecked -> {
                    Language.PYTHON
                }
                else -> {
                    Language.UNKNOWN
                }
            }

            val id: String = mRegexViewModel.generateId()
            val regexModel = RegexModel(id, regexName, language, regexStr)
            Log.d(TAG, "regexModel: $regexModel")

            // We are assuming add at top
            mRegexViewModel.insertData(regexModel)

            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_SHORT).show()

            //Navigate back to ListFragment
            findNavController().navigate(R.id.action_addRuleFragment_to_rulesListFragment)
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