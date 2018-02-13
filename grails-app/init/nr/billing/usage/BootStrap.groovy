package nr.billing.usage

class BootStrap {

    def init = { servletContext ->
      loadApiKeys()
      loadAccounts()
    }

    def loadAccounts() {
      File keyfile
      def line

      def accountName
      def accountId
      def insightsKey

      keyfile = new File(".starter-accounts")

      if (!keyfile.isFile()) {
        println ("problem reading your .starter-accounts file, not setting any keys up for you ")
      } else {
        keyfile.withReader { reader ->
         while ((line = reader.readLine())!=null) {
            if (line.startsWith("##")) {
              // do nothing
            }
            else {
              def tokens = line.split(':')
              accountName = tokens[0]
              accountId = tokens[1]
              insightsKey = tokens[2]
              NewRelicAccount acct = new NewRelicAccount(
                                          accountName:accountName, 
                                          accountId:accountId, 
                                          insightsKey:insightsKey)
              if (!acct.save(flush:true)) {
                acct.errors.allErrors.each {
                  println it
                }
              }
            }
          }
        }
      }
    }

    def loadApiKeys() {
      File keyfile
      def line
      def insightsKey = ""
      def usageKey = ""
      Boolean done = false
      keyfile = new File(".starter-apikeys")
      if (!keyfile.isFile()) {
        println ("problem reading your .starter-apikeys file, not setting any keys up for you ")
      } else {
        keyfile.withReader { reader ->
         while ((line = reader.readLine())!=null) {
            if (line.startsWith("##")&&!done) {
              // do nothing
            }
            else {
              def tokens = line.split(':')
	      if (tokens[0] == "usage") { 
		usageKey = tokens[1]
	      }
              done = true
              Credentials credentials = new Credentials(usageKey:usageKey)
              if (!credentials.save(flush:true)) {
                credentials.errors.allErrors.each {
                  println it
                }
              }
            }
          }
        }
      }
    }

    def destroy = {
    }
}
