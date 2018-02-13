package nr.billing.usage

import grails.plugins.rest.client.RestBuilder

class UsageApiService {

    def usageApi = "https://account.newrelic.com/api/v1/accounts"

    def apmUsageData(Integer accountId, String usageKey){
      RestBuilder rest = new RestBuilder()
      def response = []
      def nextPage = "${usageApi}/${String.valueOf(accountId)}/usages/apm?start_date=2018-01-01&end_date=2018-02-01&include_subaccounts=true&csv=false"
      def linksMap

      println "======== calling apm data: ========="
      while (nextPage && nextPage != "" && nextPage.startsWith("https://account.newrelic.com")) {

        println nextPage

        def resp = rest.get(nextPage) {
          header 'Api-Key', "${usageKey}"
        }
        println "----- status code: ${resp.statusCode}"
        //println resp.body
        resp.json.usages.each {
          response.add(it)
        }
        // usage API doesn't seem to have a paging feature - just dumps you one big response.
        //linksMap = buildNavLinks(resp.getHeaders().link)
        //nextPage = linksMap["nextPage"]
        nextPage = ""
      }
      response
    }

    def apmUsageDataWriteToDB(List data) {

      data.each { row ->

        HostBillingDetail d = new HostBillingDetail(
          accountId: row.account_id,
          hostId: row.host_name,
          hostProvider: row.host_provider,
          instanceType: row.instance_type,
          instanceSize: row.instance_size,
          hoursUsed: row.hours_used,
          totalRam: row.total_ram,
          logicalProcessors: row.logical_processors,
          dockerContainerId: row.docker_container_id,
          businessRule: row.business_rule,
          cloudAvailabilityZone: row.availability_zone,
          cloudInstanceId: row.instance_id
        )

        if (!d.save(flush:true)) {
          d.errors.allErrors.each {
            println it
          }
        }

      }
    }
}
