# nr-billing-usage
Grails app which uses the Usage and Insights APIs to help with departmental chargebacks

<br>

This app requires 3 external configuration files, which you MUST NOT check in to source.

<br>
All config files use ## to indicate comment lines.
App probably doesn't handle blank lines so well so don't leave any in there.
<br>

<br><b>.starter-accounts</b>

format:<br>
name:accountID:[valid insights API query key]


<b>.starter-apikeys</b>

This just should have one non-comment line, with the word "usage" and then a valid Usage API key:
<br>usage:[valid key]


<b>.starter-departments</b>

This is a mapping of applications to departments, and also groups departments into 'environments', 
see below for how environments are used for pricing.
Any app in your data, not mapped here, will just be listed in [no department]
each row is [app name]:[department name]:[environment name]
<br>example:
<br>my application:my department:PROD

Then, you can add more lines for a mapping of environment -> pricing.
This will be used in the UI to compute a grand total price based on the grand total hours for the department.
<br>example:
<br>PRICING:PROD:.50
<br>PRICING:DEV:.30

