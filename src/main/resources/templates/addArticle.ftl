<#-- @ftlvariable name="" type="fr.iim.iwm.a5.kelnerowski.florian.kotlin.UserSession" -->
<#-- @ftlvariable name="" type="fr.iim.iwm.a5.kelnerowski.florian.kotlin.Article" -->
<#if userSession?? >
    <#assign userSession = userSession>
</#if>
<#include "head.ftl">
<section class="jumbotron text-center">
    <div class="container">
        <#if addArticle?? >
            <h1>Votre article a bien été ajouté</h1>
            <p>Retourner à la page d'accueil ici : <a href="/">Accueil</a></p>
        </#if>
    <#if userSession?? && userSession.user == "admin">
        <form action="/addArticle" method="post">
            <div class="form-group">
                <label for="labelTitle">Titre</label>
                <input type="text" class="form-control" id="title" placeholder="Entrer le titre" name="title">
                <label for="labelText">Texte</label>
                <input type="text" class="form-control" id="text" placeholder="Entrer le texte" name="text">
            </div>
            <button type="submit" class="btn btn-primary">Envoyer</button>
        </form>
    <#else>
        <h1>Vous devez être administrateur pour accéder à cette page</h1>
    </#if>
    </div>
</section>
<#include "footer.ftl">