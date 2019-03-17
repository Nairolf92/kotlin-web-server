package fr.iim.iwm.a5.kelnerowski.florian.kotlin

interface ArticleController {
    fun startFM(userSession: UserSession?, idArticle: Int): Any
    fun startAddArticle(userSession: UserSession?): Any
    fun addArticle(userSession: UserSession?, article: Article): Any

}