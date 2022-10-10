package com.danilovfa.labyrinthadventure.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.danilovfa.labyrinthadventure.R
import com.danilovfa.labyrinthadventure.databinding.FragmentMainGameBinding
import com.danilovfa.labyrinthadventure.model.Maze
import com.danilovfa.labyrinthadventure.model.OnSwipeTouchListener


class MainGame : Fragment() {
    private lateinit var binding: FragmentMainGameBinding
    private lateinit var maze: Maze

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_game, container, false)
        val view = binding.root
        maze = Maze(this.requireContext())
        binding.textView.text = maze.currentRoom.directions
        binding.imageView.setImageBitmap(maze.mapBitmap)

        // Move player
        onButtonLeft()
        onButtonRight()
        onButtonUp()
        onButtonDown()
        onButtonInteract()
        onButtonInspect()

        // Inflate the layout for this fragment
        return view
    }

    private fun onButtonDown() {
        binding.buttonDown.setOnClickListener {
            if ("Down" in maze.possibleDirections) {
                maze.moveDown()
                binding.textView.text = maze.currentRoom.directions
                binding.imageView.setImageBitmap(maze.mapBitmap)
            } else
                Toast.makeText(context, "Can't move Down!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onButtonUp() {
        binding.buttonUp.setOnClickListener {
            if ("Up" in maze.possibleDirections) {
                maze.moveUp()
                binding.textView.text = maze.currentRoom.directions
                binding.imageView.setImageBitmap(maze.mapBitmap)
            } else
                Toast.makeText(context, "Can't move Up!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onButtonRight() {
        binding.buttonRight.setOnClickListener {
            if ("Right" in maze.possibleDirections) {
                maze.moveRight()
                binding.textView.text = maze.currentRoom.directions
                binding.imageView.setImageBitmap(maze.mapBitmap)
            } else
                Toast.makeText(context, "Can't move Right!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onButtonLeft() {
        binding.buttonLeft.setOnClickListener {
            if ("Left" in maze.possibleDirections) {
                maze.moveLeft()
                binding.textView.text = maze.currentRoom.directions
                binding.imageView.setImageBitmap(maze.mapBitmap)
            } else
                Toast.makeText(context, "Can't move Left!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onButtonInteract() {
        binding.buttonInteract.setOnClickListener {
            maze.interact()
            binding.imageView.setImageBitmap(maze.mapBitmap)

            if (maze.currentRoom.roomName == "Exit") {
                val myDialogFragment = WinDialogFragment()
                myDialogFragment.show(parentFragmentManager, "dialog")
                NavHostFragment.findNavController(this).navigate(R.id.action_mainGame_to_mainMenu)
            }
        }
    }

    private fun onButtonInspect() {
        binding.buttonInspect.setOnClickListener {
            binding.textView.text = maze.currentRoom.inspect()
        }
    }

    private fun changeFloor() {

    }



//    private fun onTouchListener(view: View) {
//        view.setOnTouchListener(OnSwipeTouchListener(view.context))
//    }
}

