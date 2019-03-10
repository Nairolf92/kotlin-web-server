package fr.iim.iwm.a5.kelnerowski.florian.kotlin

import io.ktor.freemarker.FreeMarkerContent
import io.ktor.http.HttpStatusCode

class ArticleControllerImpl(private val model : Model) : ArticleController {

    override fun startFM(userSession: UserSession?, idArticle: Int): Any {
        val article = model.getArticle(idArticle)
        println(article)
        val commentaries = model.getArticleCommentaries(idArticle)
        val map = mapOf("article" to article, "commentaries" to commentaries, "userSession" to userSession)
        if(article !== null) return FreeMarkerContent("article.ftl", map)
        return HttpStatusCode.NotFound
    }
}