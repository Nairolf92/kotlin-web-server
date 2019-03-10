<#-- @ftlvariable name="" type="fr.iim.iwm.a5.kelnerowski.florian.kotlin.Commentary" -->
<#-- @ftlvariable name="" type="fr.iim.iwm.a5.kelnerowski.florian.kotlin.Article" -->
<#if userSession?? >
    <#assign userSession = userSession>
</#if>

<#include "head.ftl">
    <div class="container">
        <#if userSession?? >
            <h1>Vous êtes déjà connecté</h1>
        <#elseif wrongLogin?? >
            <h1>Identifiants invalides, réesayez</h1>
            <form action="/login" method="post">
                <div class="form-group">
                    <label for="labelUsername">Pseudo</label>
                    <input type="text" class="form-control" placeholder="Entrer le Pseudo" name="username">
                    <label for="labelPassword">Mot de passe</label>
                    <input type="password" class="form-control" placeholder="Entrer le Mot de passe" name="password">
                </div>
                <button type="submit" class="btn btn-primary">Envoyer</button>
            </form>
        <#else>
            <#if message??>
                <h1>${message}</h1>
            </#if>
            <p>Note: Utiliser le meme utilisateur et mot de passe pour cette démo</p>

            <form action="/login" method="post">
                <div class="form-group">
                    <label for="labelUsername">Pseudo</label>
                    <input type="text" class="form-control" placeholder="Entrer le Pseudo" name="username">
                    <label for="labelPassword">Mot de passe</label>
                    <input type="password" class="form-control" placeholder="Entrer le Mot de passe" name="password">
                </div>
                <button type="submit" class="btn btn-primary">Envoyer</button>
            </form>
        </#if>
    </div>
<#include "footer.ftl">