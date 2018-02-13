<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <title>Billing by Department</title>
    </head>
    <body>
        <a href="#list-apmInsightsData" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link action="index">Back to Insights data</g:link>
                <li><g:link action="listDepartments">Back to Department List</g:link>
            </ul>
        </div>
        <div id="list-apmInsightsData" class="content scaffold-list" role="main">
            <h1>Billing by Department</h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>

            <div>
                <g:each in="${appMap}" var="app">
                  <br>
                  <div><strong>${app.key}</strong></div>
                  <div>environment: ${app.value.environment}</div>
                  <div>
                    <table>
                      <thead>
                        <td><strong>Hosts</strong></td><td><strong>Hours Used</strong></td>
                      </thead>
                      <g:each in="${app.value.usageData}" var="usage">
                        <tr>
                          <td>${usage.name}</td>
                          <td>${usage.hostDetail ? usage.hostDetail.hoursUsed[0] : 'not found'}</td>
                        </tr>
                      </g:each>
                      <tr><td><strong>TOTAL HOURS</strong></td><td><strong>${app.value.totalHours}</strong></td>
                      <tr>
                        <td><strong>TOTAL $</strong></td>
                        <td>
                          <strong>
                            <g:formatNumber number="${app.value.totalDollars}" type="currency" currencyCode="USD" />
                          </strong>
                        </td>
                    </table>
                  </div>
                </g:each>
                <br>
                <div><strong>TOTAL HOURS FOR DEPARTMENT: ${totalHoursDepartment}</strong></div>
                <div>
                  <strong>TOTAL $$ FOR DEPARTMENT: 
                    <g:formatNumber number="${totalDollarsDepartment}" type="currency" currencyCode="USD" />
                  </strong>
                </div>
            </div>
        </div>
    </body>
</html>
