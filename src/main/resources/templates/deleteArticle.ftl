<#if userSession?? >
    <#assign userSession = userSession>
</#if>

<#include "head.ftl">
    <div class="container">
        <#if idCommentary??>
            <h1>Commentaire ${idCommentary} supprimé</h1>
        <#else>
            <h1>Ce commentaire n'existe pas ou a déjà été supprimé</h1>
        </#if>
    </div>
<#include "footer.ftl">