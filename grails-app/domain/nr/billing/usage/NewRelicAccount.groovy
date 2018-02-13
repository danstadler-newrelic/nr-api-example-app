package nr.billing.usage

class NewRelicAccount {

    String accountName
    Integer accountId
    String insightsKey
    static constraints = {
      accountName()
      accountId()
      insightsKey()
    }
}
