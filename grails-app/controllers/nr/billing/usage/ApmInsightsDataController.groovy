package nr.billing.usage

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class ApmInsightsDataController {

    ApmInsightsDataService apmInsightsDataService
    def insightsService
    def departmentService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def loadDepartments() {
      departmentService.loadDepartments()
      redirect (action:"index")
    }

    // testing some paging by timestamp
    def insightsPagedData() {

      // TODO get current account from session
      NewRelicAccount acct = NewRelicAccount.first()

      def query = "SELECT appName, databaseCallCount FROM Transaction since 1 week ago"

      def pageSize = 5
      def maxPages = 10

      def data = insightsService.insightsPagedData(acct.accountId, acct.insightsKey, query, pageSize, maxPages)
  
      data.eachWithIndex { it, index -> println "${index}: ${it.timestamp}: ${it.appName}: ${it.databaseCallCount}" } 
   
    } 

    def listDepartments() {

      // TODO get current account from session
      NewRelicAccount acct = NewRelicAccount.first()

      // find the distinct list of department names
      def dl = ApmInsightsData.executeQuery('select department from ApmInsightsData group by department')

      render (view:"listDepartments", model:[departmentList:dl])

    }

    def billingByDepartment() {

      def dn = params["deptName"]
      // TODO get current account from session
      NewRelicAccount acct = NewRelicAccount.first()

      def appMap = [:]
      def data
      def totalHoursDepartment = 0
      def al = ApmInsightsData.findAllWhere(department:dn)

      def priceMatrix = session.priceMatrix ? session.priceMatrix : [:]
      Float totalDollarsDepartment = 0.0

      al.each { a ->

        def appResults = [:]

        data = billingForApp(a, acct)
        appResults["usageData"] = data["usageData"]
        appResults["totalHours"] = data["totalHours"]

        def pricePerHour = priceMatrix[a.environment] ? Float.parseFloat(priceMatrix[a.environment]) : 0
        def totalDollars = data["totalHours"] * pricePerHour
        appResults["totalDollars"] = totalDollars
        appResults["environment"] = a.environment

        appMap[a.name]=appResults

        totalHoursDepartment += data["totalHours"]
        totalDollarsDepartment += totalDollars

      }

      render (view:"billingByDepartment", model:[
                                            appMap:appMap,
                                            totalHoursDepartment:totalHoursDepartment,
                                            totalDollarsDepartment:totalDollarsDepartment,
                                          ])

    }

    def reloadApmInsightsDetail() {

      // truncate the table of apm detail
      ApmInsightsData.executeUpdate('delete from ApmInsightsData')

      // TODO get current account from session
      NewRelicAccount acct = NewRelicAccount.first()

      def query = "SELECT count(*), uniqueCount(host) FROM Transaction WHERE monthOf(timestamp) = 'January 2018' since 2 months ago FACET appName limit 500"

      def data = insightsService.insightsFacetedData(acct.accountId, acct.insightsKey, query)
      // data.each{println it}

      insightsService.apmInsightsDataWriteToDB(data)

      redirect(action: "index")

    }

    def index(Integer max) {
        params.max = 50
        respond apmInsightsDataService.list(params), model:[apmInsightsDataCount: apmInsightsDataService.count()]
    }

    def billingForApp(ApmInsightsData appData, NewRelicAccount acct) {

      def query = "SELECT uniqueCount(appName), count(*) FROM Transaction WHERE monthOf(timestamp) = 'January 2018'  AND appName = '${appData.name}' since 2 months ago FACET host  limit 500"

      def totalHours = 0

      def usageData = insightsService.insightsFacetedData(acct.accountId, acct.insightsKey, query)
      usageData.each { d ->

        def c = HostBillingDetail.createCriteria()
        def res = c.list {
          eq("hostId", d.name, [ignoreCase: true])
          order("hostId", "asc")
        }

        if (res && res.hoursUsed[0]) {
          totalHours += res.hoursUsed[0]
        }

        d["hostDetail"] = res
      }

      def result = [:]
      result["usageData"] = usageData
      result["totalHours"] = totalHours

      result

    }

    def show(Long id) {

      // TODO get current account from session
      NewRelicAccount acct = NewRelicAccount.first()

      def appData = apmInsightsDataService.get(id)

      def data = billingForApp(appData, acct)

      render (view:"show", model:[
                            apmInsightsData:appData, 
                            usageData:data["usageData"], 
                            totalHours:data["totalHours"]
                           ])

    }

    def create() {
        respond new ApmInsightsData(params)
    }

    def save(ApmInsightsData apmInsightsData) {
        if (apmInsightsData == null) {
            notFound()
            return
        }

        try {
            apmInsightsDataService.save(apmInsightsData)
        } catch (ValidationException e) {
            respond apmInsightsData.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'apmInsightsData.label', default: 'ApmInsightsData'), apmInsightsData.id])
                redirect apmInsightsData
            }
            '*' { respond apmInsightsData, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond apmInsightsDataService.get(id)
    }

    def update(ApmInsightsData apmInsightsData) {
        if (apmInsightsData == null) {
            notFound()
            return
        }

        try {
            apmInsightsDataService.save(apmInsightsData)
        } catch (ValidationException e) {
            respond apmInsightsData.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'apmInsightsData.label', default: 'ApmInsightsData'), apmInsightsData.id])
                redirect apmInsightsData
            }
            '*'{ respond apmInsightsData, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        apmInsightsDataService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'apmInsightsData.label', default: 'ApmInsightsData'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'apmInsightsData.label', default: 'ApmInsightsData'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
