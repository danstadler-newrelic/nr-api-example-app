<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'apmInsightsData.label', default: 'ApmInsightsData')}" />
        <title>Insights Paged Data</title>
    </head>
    <body>
        <a href="#list-apmInsightsData" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
                <li><g:link action="reloadApmInsightsDetail">reload APM Insights detail</g:link>
                <li><g:link action="loadDepartments">reload departments</g:link>
                <li><g:link action="listDepartments">List Departments</g:link>
                <li><g:link action="insightsPagedData">paging test</g:link>
            </ul>
        </div>
        <div id="list-apmInsightsData" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
        </div>
    </body>
</html>
