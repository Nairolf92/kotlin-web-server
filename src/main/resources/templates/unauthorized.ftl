<#-- @ftlvariable name="" type="fr.iim.iwm.a5.kelnerowski.florian.kotlin.Commentary" -->
<#-- @ftlvariable name="" type="fr.iim.iwm.a5.kelnerowski.florian.kotlin.Article" -->
<#if userSession?? >
    <#assign userSession = userSession>
</#if>

<#include "head.ftl">
    <div class="container">
        <h1>Vous devez être connecté en tant qu'administrateur pour effectuer cette action</h1>
    </div>
<#include "footer.ftl">