package nr.billing.usage

import grails.plugins.rest.client.RestBuilder

class InsightsService {

    def insightsApi = "https://insights-api.newrelic.com/v1/accounts"

    // insights doesn't have a paging feature like the REST api does, but you still
    // might want to page out rows at a time, and then work with timestamps as 
    // page markers
    def insightsPagedData(Integer accountId, String insightsKey, String query, Integer pageSize, Integer maxPages) {

      RestBuilder rest = new RestBuilder()
      def response = []

      def nextPage = "${insightsApi}/${String.valueOf(accountId)}/query?nrql={nrql}&source_id={source_id}"
      def initialQuery = "${query} LIMIT ${pageSize}" 
      def qp = [
        nrql: initialQuery,
        source_id: "${String.valueOf(accountId)}"
      ]

      def lastTimestamp = -1
      def pageNumber = 0

      println "======== calling data: ========="
      while (pageNumber < maxPages && nextPage && nextPage != "" && nextPage.startsWith("https://insights-api.newrelic.com")) {
        println "page: ${pageNumber}"
        println nextPage
        println qp["nrql"]
        def resp
        resp = rest.get(nextPage, qp) {
          header 'X-Query-Key', "${insightsKey}"
        }
        println "----- status code: ${resp.statusCode}"
        //println resp.body

        resp.json.results.events[0].each {
          lastTimestamp = it.timestamp
          response.add(it)
        }

        // try paging by timestamps here
        def nextQuery = "${initialQuery} WHERE timestamp < ${lastTimestamp}"
        qp["nrql"] = nextQuery

        pageNumber++
 
      }
      response
    }


    def insightsFacetedData(Integer accountId, String insightsKey, String query){
      RestBuilder rest = new RestBuilder()
      def response = []
      def linksMap

      def nextPage = "${insightsApi}/${String.valueOf(accountId)}/query?nrql={nrql}&source_id={source_id}"
      def qp = [
        nrql: query,
        source_id: "${String.valueOf(accountId)}"
      ]

      println "======== calling data: ========="
      while (nextPage && nextPage != "" && nextPage.startsWith("https://insights-api.newrelic.com")) {
        println nextPage
        def resp
        resp = rest.get(nextPage, qp) {
          header 'X-Query-Key', "${insightsKey}"
        }
        println "----- status code: ${resp.statusCode}"
        //println resp.body
        resp.json.facets.each {
          response.add(it)
        }
        // haven't yet tested with need for paging
        //linksMap = buildNavLinks(resp.getHeaders().link)
        //nextPage = linksMap["nextPage"]
        nextPage = ""
      }
      response
    }

    def apmInsightsDataWriteToDB(List data) {
      data.each { row ->
        ApmInsightsData d = new ApmInsightsData(
          name:row.name,
          transactionCount:row.results[0].count,
          hostCount:row.results[1].uniqueCount
        )
        if (!d.save(flush:true)) {
          d.errors.allErrors.each {
            println it
          }
        }
      }
    }
}
