package nr.billing.usage

class ApmInsightsData {

    String name
    Integer transactionCount
    Integer hostCount
    String department
    String environment

    static constraints = {
      name()
      department(nullable:true)
      environment (nullable:true)
      transactionCount (nullable:true)
      hostCount (nullable:true)
    }

    static mapping = {
      name index: 'name_idx'
    }

}
