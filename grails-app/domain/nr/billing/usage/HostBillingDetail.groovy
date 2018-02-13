package nr.billing.usage

class HostBillingDetail {

    Integer accountId
    String hostId
    String hostProvider
    String instanceType
    String instanceSize
    Integer hoursUsed
    Float totalRam
    Integer logicalProcessors
    String dockerContainerId
    String businessRule
    String cloudAvailabilityZone
    String cloudInstanceId

    static constraints = {
      accountId()
      hostId(nullable:true)
      hostProvider(nullable:true)
      instanceType(nullable:true)
      instanceSize(nullable:true)
      hoursUsed(nullable:true)
      totalRam(nullable:true)
      logicalProcessors(nullable:true)
      dockerContainerId(nullable:true)
      businessRule(nullable:true)
      cloudAvailabilityZone(nullable:true)
      cloudInstanceId(nullable:true)
    }

    static mapping = {
      hostId index: 'hostId_idx'
    }
}
