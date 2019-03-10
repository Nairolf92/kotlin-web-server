package fr.iim.iwm.a5.kelnerowski.florian.kotlin

class CommentaryControllerImpl(private val model : Model) : CommentaryController {

    override fun addCommentary(commentary: Commentary): Any? {
        model.setArticleCommentary(commentary)
        return null
    }
}