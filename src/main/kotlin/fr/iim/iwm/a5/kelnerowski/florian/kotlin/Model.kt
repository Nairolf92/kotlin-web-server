package fr.iim.iwm.a5.kelnerowski.florian.kotlin

interface Model {
    fun getArticleList(): List<Article>
    fun getArticle(id: Int): Article?
    fun addArticle(article : Article): Any?
    fun addArticleCommentary(commentary: Commentary): Any?
    fun getArticleCommentaries(idArticle: Int): List<Commentary>?
    fun deleteCommentary(idCommentary : Int) : Any?
}