<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <title>List Departments</title>
    </head>
    <body>
        <a href="#list-apmInsightsData" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
                <li><g:link action="index">APM data</g:link>
            </ul>
        </div>
        <div id="list-apmInsightsData" class="content scaffold-list" role="main">
            <h1>Departments</h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>

            <div>
              <ul>
                <g:each in="${departmentList}">
                  <li><g:link action="billingByDepartment"
                          params='[deptName:"${it}"]'>${it}</g:link>
                  </li>
                </g:each>
              </ul>
            </div>
        </div>
    </body>
</html>
