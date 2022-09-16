package com.danilovfa.labyrinthadventure.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.danilovfa.labyrinthadventure.R
import com.danilovfa.labyrinthadventure.databinding.FragmentMainMenuBinding
import kotlin.system.exitProcess

class MainMenu : Fragment() {
    private lateinit var binding: FragmentMainMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_menu, container, false)
        val view = binding.root
        onButtonStart()
        onButtonExit()
        // Inflate the layout for this fragment
        return view
    }

    private fun onButtonStart() {
        binding.buttonStartGame.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_mainMenu_to_mainGame)
        }
    }

    private fun onButtonExit() {
        binding.buttonExit.setOnClickListener {
            exitProcess(0)
        }
    }
}