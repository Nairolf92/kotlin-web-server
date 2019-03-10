package fr.iim.iwm.a5.kelnerowski.florian.kotlin

import io.ktor.freemarker.FreeMarkerContent

class ArticleListControllerImpl(private val model : Model) : ArticleListController {

    override fun startFM(userSession: UserSession?): FreeMarkerContent {
        val articles = model.getArticleList()
        val map = mapOf("articles" to articles, "userSession" to userSession)
        return FreeMarkerContent("index.ftl", map)
    }
}