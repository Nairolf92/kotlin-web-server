package fr.iim.iwm.a5.kelnerowski.florian.kotlin

import io.ktor.freemarker.FreeMarkerContent

class ArticleListControllerImpl(private val model : Model) : ArticleListController {

    override fun startFM(): FreeMarkerContent {
        val articles = model.getArticleList()
        return FreeMarkerContent("index.ftl", mapOf("articles" to articles))
    }
}