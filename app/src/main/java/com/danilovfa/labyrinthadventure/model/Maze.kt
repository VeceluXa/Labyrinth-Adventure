package com.danilovfa.labyrinthadventure.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.util.Log
import androidx.core.graphics.get
import androidx.core.graphics.set
import com.danilovfa.labyrinthadventure.R
import kotlin.concurrent.fixedRateTimer
import kotlin.random.Random
import kotlin.random.nextInt

class Maze(private val context: Context) {
    lateinit var mapBitmap: Bitmap
    var playerX = 0
    var playerY = 0
    lateinit var possibleDirections: Array<String>
    lateinit var currentRoom: Room
    var currentFloor = 0

    private var maze = arrayOf(
        arrayOf(
            arrayOf(Room(10,1), Room(5, 0), Room(10, 9), Room(2, 0), Room(5, 0)),
            arrayOf(Room(12, 5), Room(7, 0), Room(5, 0), Room(8, 0), Room(8, 0)),
            arrayOf(Room(0, 0), Room(11, 8), Room(8, 0), Room(8, 0), Room(8, 0)),
            arrayOf(Room(8, 0), Room(10, 9), Room(1, 0), Room(13, 3), Room(8, 0)),
            arrayOf(Room(7, 0), Room(9, 0), Room(3, 0), Room(9, 0), Room(6, 0))
        ),
        arrayOf(
            arrayOf(Room(10,1), Room(5, 0), Room(10, 9), Room(2, 0), Room(5, 0)),
            arrayOf(Room(12, 5), Room(7, 0), Room(5, 0), Room(8, 0), Room(8, 0)),
            arrayOf(Room(0, 0), Room(11, 8), Room(8, 0), Room(8, 0), Room(8, 0)),
            arrayOf(Room(8, 0), Room(10, 9), Room(1, 0), Room(13, 3), Room(8, 0)),
            arrayOf(Room(7, 0), Room(9, 0), Room(3, 0), Room(9, 0), Room(6, 0))
        ),
        arrayOf(
            arrayOf(Room(10,1), Room(5, 0), Room(10, 9), Room(2, 0), Room(5, 0)),
            arrayOf(Room(12, 5), Room(7, 0), Room(5, 0), Room(8, 0), Room(8, 0)),
            arrayOf(Room(0, 0), Room(11, 8), Room(8, 0), Room(8, 0), Room(8, 0)),
            arrayOf(Room(8, 0), Room(10, 9), Room(1, 0), Room(13, 3), Room(8, 0)),
            arrayOf(Room(7, 0), Room(9, 0), Room(3, 0), Room(9, 0), Room(6, 0))
        ),
        arrayOf(
            arrayOf(Room(10,1), Room(5, 0), Room(10, 9), Room(2, 0), Room(5, 0)),
            arrayOf(Room(12, 5), Room(7, 0), Room(5, 0), Room(8, 0), Room(8, 0)),
            arrayOf(Room(0, 0), Room(11, 8), Room(8, 0), Room(8, 0), Room(8, 0)),
            arrayOf(Room(8, 0), Room(10, 9), Room(1, 0), Room(13, 3), Room(8, 0)),
            arrayOf(Room(7, 0), Room(9, 0), Room(3, 0), Room(9, 0), Room(6, 0))
        ),
    )

    private var floor = maze[0]

    private var floorDiscovered = arrayOf(
        arrayOf(
            arrayOf(false, false, false, false, false),
            arrayOf(false, false, false, false, false),
            arrayOf(false, false, false, false, false),
            arrayOf(false, false, false, false, false),
            arrayOf(false, false, false, false, false)
        ),
        arrayOf(
            arrayOf(false, false, false, false, false),
            arrayOf(false, false, false, false, false),
            arrayOf(false, false, false, false, false),
            arrayOf(false, false, false, false, false),
            arrayOf(false, false, false, false, false)
        ),
        arrayOf(
            arrayOf(false, false, false, false, false),
            arrayOf(false, false, false, false, false),
            arrayOf(false, false, false, false, false),
            arrayOf(false, false, false, false, false),
            arrayOf(false, false, false, false, false)
        ),
        arrayOf(
            arrayOf(false, false, false, false, false),
            arrayOf(false, false, false, false, false),
            arrayOf(false, false, false, false, false),
            arrayOf(false, false, false, false, false),
            arrayOf(false, false, false, false, false)
        ),
    )

    init {
        generateMaze()
    }

    private fun generateMaze() {
        mapBitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888)

        // Place player in random coordinate
        playerX = Random.nextInt(0..4)
        playerY = Random.nextInt(0..4)
        currentRoom = maze[currentFloor][playerY][playerX]
        floorDiscovered[currentFloor][playerY][playerX] = true
        drawRoom(playerX, playerY)
        moveToRoom(currentFloor, playerX, playerY)
//        drawMaze(mapBitmap, floor)

    }

    private fun moveToRoom(floor: Int, x: Int, y: Int) {
        playerX = x
        playerY = y
        currentRoom = maze[currentFloor][playerY][playerX]
        drawRoom(x, y)
        drawPlayer(x, y)
        if (!floorDiscovered[currentFloor][y][x]) floorDiscovered[currentFloor][y][x] = true
        val walls = maze[currentFloor][y][x].wallName.split("|")
        val directions = arrayOf("Up", "Down", "Left", "Right")

        directions.subtract(walls.toSet()).forEach {
            when(it) {
                "Up" -> drawQuestion(x, y - 1)
                "Down" -> drawQuestion(x, y + 1)
                "Left" -> drawQuestion(x - 1, y)
                "Right" -> drawQuestion(x + 1, y)
            }
        }

        possibleDirections = directions.subtract(walls.toSet()).toTypedArray()
    }

    private fun moveToFloor(floor: Int) {

    }

    fun moveUp() {
        drawRoom(playerX, playerY)
        moveToRoom(currentFloor, playerX, playerY - 1)
    }

    fun moveDown() {
        drawRoom(playerX, playerY)
        moveToRoom(currentFloor, playerX, playerY + 1)
    }

    fun moveLeft() {
        drawRoom(playerX, playerY)
        moveToRoom(currentFloor, playerX - 1, playerY)
    }

    fun moveRight() {
        drawRoom(playerX, playerY)
        moveToRoom(currentFloor, playerX + 1, playerY)
    }

    fun interact() {
        when(currentRoom.roomName) {
            "Elevator" -> interactElevator()
            "Exit" -> interactExit()
        }
    }

    private fun interactElevator() {
        currentFloor++
//        floorDiscovered[currentFloor][playerY][playerX] = true
        clearMap()
        moveToRoom(currentFloor, playerX, playerY)
    }

    private fun interactExit() {
        // TODO make win message
    }

    private fun drawRoom(x: Int, y: Int) {
        val wallBitmap =
            BitmapFactory.decodeResource(context.resources, maze[currentFloor][y][x].wallTexture)
        for (i in 0 until 100)
            for (j in 0 until 100)
                mapBitmap[i + 100 * x, j + 100 * y] = wallBitmap[i, j]


        if (currentRoom.roomName != "") {
            for (i in 40 until 60) {
                for (j in 40 until 60) {
                    mapBitmap[i + 100 * x, j + 100 * y] = Color.BLACK
                }
            }
        }
    }

    private fun drawPlayer(x: Int, y: Int) {
        val playerBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.player)
        for (i in 10 until 90) {
            for (j in 10 until 90) {
                mapBitmap[i + 100 * x, j + 100 * y] = playerBitmap[i, j]
            }
        }
    }

    private fun drawQuestion(x: Int, y: Int) {
        Log.println(Log.INFO, "question", "$x $y")
        if (floorDiscovered[currentFloor][y][x]) return
        val questionBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.room_question)
        for (i in 0 until 100) {
            for (j in 0 until 100) {
                if (questionBitmap[i, j] != Color.WHITE)
                    mapBitmap[i + 100 * x, j + 100 * y] = questionBitmap[i, j]
            }
        }
    }

    private fun drawMaze(bitmap: Bitmap, floor: Array<Array<Room>>) {
        for (i in floor.indices) {
            for (j in floor.indices) {
                drawRoom(j, i)
            }
        }
    }

    private fun clearMap() {
        for (i in 0 until 500)
            for (j in 0 until 500)
                mapBitmap[j, i] = Color.WHITE
    }
}