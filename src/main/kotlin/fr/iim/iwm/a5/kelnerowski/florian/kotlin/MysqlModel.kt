package fr.iim.iwm.a5.kelnerowski.florian.kotlin

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
            connection.prepareStatement("SELECT * FROM articles RIGHT JOIN commentary ON articles.id = commentary.idArticle WHERE articles.id = ?").use { stmt ->
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

    override fun setArticleCommentary(commentary: Commentary): Any? {
        println(commentary)
        connectionPool.use { connection ->
            connection.prepareStatement("INSERT INTO commentary(idArticle,textArticle) VALUES (?, ?)").use { stmt ->
                stmt.setInt(1, commentary.idArticle)
                stmt.setString(2, commentary.textArticle)
            stmt.execute()
            }
        }
        return null
    }
}