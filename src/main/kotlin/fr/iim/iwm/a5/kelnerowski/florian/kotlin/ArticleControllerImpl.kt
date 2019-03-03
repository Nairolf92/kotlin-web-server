package fr.iim.iwm.a5.kelnerowski.florian.kotlin

import io.ktor.freemarker.FreeMarkerContent
import io.ktor.http.HttpStatusCode

class ArticleControllerImpl(private val model : Model) : ArticleController {

    override fun startFM(id: Int): Any {
        val article = model.getArticle(id)
        if(article !== null) return FreeMarkerContent("article.ftl", mapOf("article" to article))
        return HttpStatusCode.NotFound
    }
}