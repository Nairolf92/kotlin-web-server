package fr.iim.iwm.a5.kelnerowski.florian.kotlin

import io.ktor.freemarker.FreeMarkerContent

interface CommentaryController {
    fun addCommentary(commentary: Commentary): Any?
    fun deleteCommentary(userSession: UserSession?, idCommentary: Int): FreeMarkerContent
}