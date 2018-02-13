package nr.billing.usage

import org.springframework.web.context.request.RequestContextHolder; 

class DepartmentService {

  def loadDepartments() {

    def session = RequestContextHolder.currentRequestAttributes().getSession()
    def priceMatrix = session.priceMatrix ? session.priceMatrix : [:]

    File keyfile
    def line
    def app = ""
    def dept = ""
    def envr = ""
    keyfile = new File(".starter-departments")
    if (!keyfile.isFile()) {
      println ("problem reading your .starter-departments file, not setting any departments up for you ")
    } else {
      // clear all dept names
      ApmInsightsData.executeUpdate('update ApmInsightsData set department = null')
      keyfile.withReader { reader ->
       while ((line = reader.readLine())!=null) {
          if (line.startsWith("##")) {
            // do nothing
          }
          else if (line.startsWith("PRICING")) {
            def tokens = line.split(':')
            def envrPricing = tokens[1]
            def pricePerHour = tokens[2]
            priceMatrix[envrPricing] = pricePerHour
            session.priceMatrix = priceMatrix
          }
          else {
            def tokens = line.split(':')
            app = tokens[0]
            dept = tokens[1]
            envr = tokens[2]
            ApmInsightsData d = ApmInsightsData.findWhere(name:app)
            if (d) { 
              d.department = dept
              d.environment = envr
              if (!d.save(flush:true)) {
                d.errors.allErrors.each {
                  println it
                }
              }
            }
          }
        }
        // set the rest to [no department]
        ApmInsightsData.executeUpdate("update ApmInsightsData set department = '[no department]' where department is null")
      }
    }
  }

}
