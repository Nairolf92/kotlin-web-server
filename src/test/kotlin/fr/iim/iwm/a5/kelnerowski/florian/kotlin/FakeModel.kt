package fr.iim.iwm.a5.kelnerowski.florian.kotlin

class FakeModel : Model {
    override fun deleteCommentary(idCommentary: Int): Any? {
        return null
    }

    override fun addArticleCommentary(commentary: Commentary): Any? {
        return null
    }

    override fun addArticle(article: Article): Any? {
        return null
    }

    override fun getArticleCommentaries(idArticle: Int): List<Commentary>? {
        return listOf(Commentary(null, 42, "commentaire article 42"))
    }

    override fun getArticleList(): List<Article> {
        return listOf(Article(42, "Super Titre"))
    }

    override fun getArticle(id: Int): Article? {
        return if (id == 42)
            Article(42, "Title", "Text value")
        else
            null
    }
}