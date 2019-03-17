package fr.iim.iwm.a5.kelnerowski.florian.kotlin

import io.ktor.freemarker.FreeMarkerContent

class CommentaryControllerImpl(private val model : Model) : CommentaryController {

    override fun addCommentary(commentary: Commentary): Any? {
        model.addArticleCommentary(commentary)
        return null
    }

    override fun deleteCommentary(userSession: UserSession?, idCommentary: Int): FreeMarkerContent {
        if(userSession?.user == "admin") {
            val deleteCommentary = model.deleteCommentary(idCommentary)
            return if (deleteCommentary != null) {
                val map = mapOf("userSession" to userSession,"idCommentary" to idCommentary)
                FreeMarkerContent("deleteArticle.ftl", map)
            } else{
                val map = mapOf("userSession" to userSession)
                FreeMarkerContent("deleteArticle.ftl", map)
            }
        }
        val map = mapOf("userSession" to userSession)
        return FreeMarkerContent("unauthorized.ftl", map)
    }
}