<#-- @ftlvariable name="" type="fr.iim.iwm.a5.kelnerowski.florian.kotlin.IndexData" -->
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <title>CMS</title>
</head>
<body>

<#list articles as article>
    <p><a href="/articles/${article.id}">${article.title}</a></p>
</#list>

</body>
</html>