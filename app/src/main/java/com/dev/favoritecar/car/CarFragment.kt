package com.dev.favoritecar.car

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dev.favoritecar.R
import com.dev.favoritecar.databinding.CarFragmentBinding
import com.dev.favoritecar.model.Car
import com.dev.favoritecar.base.BaseApplication
import com.raywenderlich.android.monsters.repository.CarData

class CarFragment : Fragment() {

    private lateinit var binding: CarFragmentBinding
    private  val billingHelper by lazy {
        (requireActivity().application as BaseApplication).appContainer.bill
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = CarFragmentBinding.inflate(layoutInflater).apply {
            carFragment = this@CarFragment

            this.lifecycleOwner = lifecycleOwner
        }
        return binding.root
    }

    fun getCars(): Car {
        return CarData.getMyCar(requireContext())
    }




    fun passToCarStoreFragment(){

        findNavController().navigate(R.id.carStoreFragment)
    }

}