package com.g2.santurtziapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.g2.santurtziapp.R
import com.g2.santurtziapp.SharedApp
import com.g2.santurtziapp.activitidades.DrawerActivity
import com.g2.santurtziapp.activitidades.MainActivity
import com.g2.santurtziapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)

        binding.passwordInput.text.clear()

        val m: MainActivity? = activity as MainActivity?

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                m?.replaceFragment(PartidasFragment(), 1)

            }

        })

        binding.logButton.setOnClickListener{

            var password: String = m!!.quitarEspacios(binding.passwordInput.text.toString())

            if (password.isNotEmpty()) {

                if (password == "123456Aa") {

                    SharedApp.tipousu.tipo = "profesor"
                    m.checkUserMode()
                    m.replaceFragment(ProfesorFragment(), 1)

                } else {

                    Toast.makeText(requireContext(), R.string.logError, Toast.LENGTH_SHORT).show()

                }

            } else {

                Toast.makeText(requireContext(), R.string.noContra, Toast.LENGTH_SHORT).show()

            }

        }//onClick

        return binding.root

    }//onCreateView (inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View?

}//LoginFragment()