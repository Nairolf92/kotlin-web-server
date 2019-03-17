# Kotlin-web-server

Login admin :
 * admin/admin

Login simple utilisateur :
 * user/user


## Routes availables

* GET /
    * Affichage des articles
* GET /articles/{idArticle}
    * Affichage d'un article en particulier
* GET /addArticle
    * Formulaire d'ajout d'article pour admin
* POST /addArticle
    * Méthode POST pour l'ajout d'un article
* POST /addArticleCommentary
    * Méthode POST pour ajouter un commentaire à un article
* GET /commentary/delete/{idCommentary}
    * Suppression d'un commentaire en fonction de l'id donné
* GET /login
    * Page de connexion
* GET /logout
    * Page de déconnexion


