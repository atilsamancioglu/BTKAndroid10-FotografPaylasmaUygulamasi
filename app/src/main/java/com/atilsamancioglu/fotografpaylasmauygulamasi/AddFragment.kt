package com.atilsamancioglu.fotografpaylasmauygulamasi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.atilsamancioglu.fotografpaylasmauygulamasi.databinding.FragmentAddBinding

class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.uploadImageView.setOnClickListener { imageViewClicked(it) }
        binding.uploadButton.setOnClickListener { uploadClicked(it) }

    }

    fun uploadClicked(view: View) {

    }

    fun imageViewClicked(view: View) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}