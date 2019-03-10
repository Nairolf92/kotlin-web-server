package fr.iim.iwm.a5.kelnerowski.florian.kotlin

interface Model {
    fun getArticleList(): List<Article>
    fun getArticle(id: Int): Article?
    fun setArticleCommentary(commentary: Commentary): Any?
    fun getArticleCommentaries(idArticle: Int): List<Commentary>?
    fun deleteCommentary(idCommentary : Int) : Any?
}