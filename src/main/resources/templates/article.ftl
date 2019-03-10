<#-- @ftlvariable name="" type="fr.iim.iwm.a5.kelnerowski.florian.kotlin.Commentary" -->
<#-- @ftlvariable name="" type="fr.iim.iwm.a5.kelnerowski.florian.kotlin.Article" -->
<#if userSession?? >
    <#assign userSession = userSession>
</#if>

<#include "head.ftl">
    <div class="container">
        <h1>Titre article : ${article.title}</h1>
        <p>Texte article : ${article.text}</p>
        <p>Commentaires : </p>
        <#if commentaries??>
            <ul>
            <#list commentaries as commentary>
                <li>${commentary.textArticle}</li>
            </#list>
            </ul>
        </#if>


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