package fr.iim.iwm.a5.kelnerowski.florian.kotlin

import io.ktor.freemarker.FreeMarkerContent
import io.ktor.http.HttpStatusCode

class ArticleControllerImpl(private val model : Model) : ArticleController {

    override fun startFM(userSession: UserSession?, articleId: Int): Any {
        val article = model.getArticle(articleId)
        val map = mapOf("article" to article, "userSessions" to userSession)
        if(article !== null) return FreeMarkerContent("article.ftl", map)
        return HttpStatusCode.NotFound
    }
}