package fr.iim.iwm.a5.kelnerowski.florian.kotlin

import io.ktor.freemarker.FreeMarkerContent
import io.ktor.http.HttpStatusCode

class ArticleControllerImpl(private val model : Model) : ArticleController {
    override fun startFM(userSession: UserSession?, idArticle: Int): Any {
        val article = model.getArticle(idArticle)
        val commentaries = model.getArticleCommentaries(idArticle)
        val map = mapOf("article" to article, "commentaries" to commentaries, "userSession" to userSession)
        if(article !== null) return FreeMarkerContent("article.ftl", map)
        return HttpStatusCode.NotFound
    }

    override fun startAddArticle(userSession: UserSession?): Any {
        val map = mapOf("userSession" to userSession)
        return FreeMarkerContent("addArticle.ftl", map)
    }

    override fun addArticle(userSession: UserSession?, article: Article): Any {
        val addArticle = model.addArticle(article)
        return if(addArticle !== null) {
            val map = mapOf("userSession" to userSession,"addArticle" to "addArticle")
            FreeMarkerContent("addArticle.ftl", map)
        } else {
            val map = mapOf("userSession" to userSession)
            FreeMarkerContent("addArticle.ftl", map)
        }
    }
}