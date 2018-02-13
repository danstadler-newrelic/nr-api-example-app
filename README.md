# nr-billing-usage
Grails app which uses the Usage and Insights APIs to help with departmental chargebacks

<br>

This app requires 3 external configuration files, which you MUST NOT check in to source.

<b>.starter-accounts</b>

format:<br>
name:accountID:[valid insights API query key]


<b>.starter-apikeys</b>

This just should have one non-comment line, with the word "usage" and then a valid Usage API key:
<br>usage:[valid key]


<b>.starter-departments</b>

This is a mapping of applications to departments
any app in your data, not mapped here, will just be listed in [no department]
each row is [app name]:[department name]
<br>example:
<br>my application:my department

<br>
All config files use ## to indicate comment lines.
App probably doesn't handle blank lines so well so don't leave any in there.
