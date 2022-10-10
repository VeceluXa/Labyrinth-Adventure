package com.danilovfa.labyrinthadventure.model

import com.danilovfa.labyrinthadventure.R

val wallsTextures = arrayOf(R.drawable.wall_left, R.drawable.wall_right, R.drawable.wall_up, R.drawable.wall_down,
    R.drawable.wall_left_up, R.drawable.wall_right_up, R.drawable.wall_right_down, R.drawable.wall_left_down,
    R.drawable.wall_left_right, R.drawable.wall_up_down,
    R.drawable.wall_left_up_down, R.drawable.wall_right_up_down, R.drawable.wall_left_right_up, R.drawable.wall_left_right_down)

val roomsTextures = arrayOf(R.drawable.room_empty, R.drawable.room_elevator)



val roomsNames = arrayOf("elevator", "stairs", "spawn", "exit", "prison cell", "library",
    "experimental chamber", "study class", "server room", "morgue",
    "maintenance room", "engines haul", "")

// TODO

class Room(wallId: Int, roomId: Int) {
    val wallTexture = wallsTextures[wallId]
//    val roomTexture = roomsTextures[roomId]

    val roomName = when(roomId) {
        1 -> "Elevator"
        2 -> "Stairs"
        3 -> "Prison cell"
        4 -> "Library"
        5 -> "Experimental chamber"
        6 -> "Study class"
        7 -> "Server room"
        8 -> "Morgue"
        9 -> "Maintenance room"
        10 -> "Engines haul"
        11 -> "Exit"
        else -> ""
    }

    val wallName = when(wallId) {
        0 -> "Left"
        1 -> "Right"
        2 -> "Up"
        3 -> "Down"
        4 -> "Left|Up"
        5 -> "Right|Up"
        6 -> "Right|Down"
        7 -> "Left|Down"
        8 -> "Left|Right"
        9 -> "Up|Down"
        10 -> "Left|Up|Down"
        11 -> "Right|Up|Down"
        12 -> "Left|Right|Up"
        13 -> "Left|Right|Down"
        else -> ""
    }

    val roomDescription: String
        get() {
            var str = ""
            if (roomName != "") str += "You have entered $roomName. "
            str += when (roomName) {
                "Elevator" -> "You can go to a different floor"
                "Stairs" -> "You can go to a different floor"
                "Prison cell" -> "It looks like someone was there a long time ago"
                "Library" -> "It is full of books in some kind of alien language"
                "Experimental chamber" -> "It looks like someone was tortured here"
                "Study class" -> "There are a lot of desks with tablets on them"
                "Server room" -> "There are a lot of computers"
                "Morgue" -> "It has a lot of dead people. I hope I will not be one of them"
                "Maintenance room" -> "It looks like this room is used for storing some sort of cleaning instruments"
                "Engines haul" -> "The engines are very loud"
                "Exit" -> "You can finally escape this damn ship"
                else -> ""
            }
            str += ".\n"
            return str
        }

    val directions: String
        get() {
            var str = "You can go in following directions: "
            val directions =
                listOf("Right", "Left", "Up", "Down").subtract(wallName.split("|").toSet()).toList()
            if (directions.size == 1) str += "${directions[0]}."
            if (directions.size == 2) str += "${directions[0]} and ${directions[1]}."
            if (directions.size == 3) str += "${directions[0]}, ${directions[1]} and ${directions[2]}."
            if (directions.size == 4) str += "${directions[0]}, ${directions[1]}, ${directions[2]} and ${directions[3]}."
            str += "\n"
            return str
        }

    fun inspect(): String {
        var inspectStr = ""
        inspectStr += roomDescription
        inspectStr += directions
        return inspectStr
    }
}


