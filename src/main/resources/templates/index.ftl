<#-- @ftlvariable name="" type="fr.iim.iwm.a5.kelnerowski.florian.kotlin.UserSession" -->
<#-- @ftlvariable name="" type="fr.iim.iwm.a5.kelnerowski.florian.kotlin.IndexData" -->
<#if userSession?? >
    <#assign userSession = userSession>
</#if>
<#include "head.ftl">
    <section class="jumbotron text-center">
        <div class="container">
            <#if userSession?? >
                <h1 class="jumbotron-heading">Bienvenue <strong>${userSession.user}</strong></h1>
            <#else>
                <h1 class="jumbotron-heading">Bienvenue ! </h1>
            </#if>
            <h2 class="jumbotron-heading">Liste des articles</h2>
            <#list articles as article>
                <p class="text-muted"><a href="/articles/${article.id}">${article.title}</a></p>
            </#list>
        </div>
    </section>

<#include "footer.ftl">
