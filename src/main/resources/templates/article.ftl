<#-- @ftlvariable name="" type="fr.iim.iwm.a5.kelnerowski.florian.kotlin.Article" -->
<#if userSessions?? >
    <#assign userSession = userSessions>
</#if>

<#include "head.ftl">
    <div class="container">
${article}
        <h1>Titre article : ${article.title}</h1>
        <p>Texte article : ${article.text}</p>
        <p>Commentaires : ${article.commentary.textArticle}</p>

        <form action="/addArticleCommentary" method="post">
            <div class="form-group">
                <label for="labelCommentaire">Commentaire</label>
                <input type="text" class="form-control" id="commentary" placeholder="Entrer le commentaire" name="commentary">
                <input type="hidden" name="idArticle" value=${article.id}>
            </div>
            <button type="submit" class="btn btn-primary">Envoyer</button>
        </form>
    </div>
<#include "footer.ftl">