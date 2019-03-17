package fr.iim.iwm.a5.kelnerowski.florian.kotlin

import io.ktor.http.HttpStatusCode

class MysqlModel(url : String, user : String?, password: String?) : Model {

    val connectionPool = ConnectionPool(url, user, password)

    override fun getArticleList(): List<Article> {
        val articles = ArrayList<Article>()

        connectionPool.use { connection ->
            connection.prepareStatement("SELECT id, title, text FROM articles").use { stmt ->
                val results = stmt.executeQuery()
                while (results.next()) {
                    articles.add(Article(
                        results.getInt("id"),
                        results.getString("title"),
                        results.getString("text")
                    ))
                }
            }
        }
        return articles
    }

    override fun getArticle(id: Int): Article? {
        connectionPool.use { connection ->
            connection.prepareStatement("SELECT * FROM articles WHERE articles.id = ?").use { stmt ->
                stmt.setInt(1, id)
                val results = stmt.executeQuery()
                val found = results.next()
                if (found) {
                    return Article(
                        results.getInt("id"),
                        results.getString("title"),
                        results.getString("text")
                    )
                }
            }
        }
        return null
    }

    override fun addArticle(article: Article): Any? {
        connectionPool.use { connection ->
            connection.prepareStatement("INSERT INTO articles(title,text) VALUES (?, ?)").use { stmt ->
                stmt.setString(1, article.title)
                stmt.setString(2, article.text)
                stmt.execute()
            }
        }
        return HttpStatusCode.Accepted
    }

    override fun getArticleCommentaries(idArticle: Int): List<Commentary>? {
        val commentaries = ArrayList<Commentary>()

        connectionPool.use { connection ->
            connection.prepareStatement("SELECT * FROM commentary WHERE idArticle = ?").use { stmt ->
                stmt.setInt(1, idArticle)
                val results = stmt.executeQuery()
                while (results.next()) {
                    commentaries.add(
                        Commentary(
                            results.getInt("id"),
                            results.getInt("idArticle"),
                            results.getString("textArticle")
                        )
                    )
                }
            }
        }
        return commentaries
    }

    override fun addArticleCommentary(commentary: Commentary): Any? {
        connectionPool.use { connection ->
            connection.prepareStatement("INSERT INTO commentary(idArticle,textArticle) VALUES (?, ?)").use { stmt ->
                stmt.setInt(1, commentary.idArticle)
                stmt.setString(2, commentary.textArticle)
            stmt.execute()
            }
        }
        return null
    }

    override fun deleteCommentary(idCommentary: Int): Any? {
        connectionPool.use { connection ->
            connection.prepareStatement("DELETE FROM commentary WHERE commentary.id = ?").use { stmt ->
                stmt.setInt(1, idCommentary)
                stmt.execute()
            }
        }
        return null
    }
}