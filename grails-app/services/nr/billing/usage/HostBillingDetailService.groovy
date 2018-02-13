package nr.billing.usage

import grails.gorm.services.Service

@Service(HostBillingDetail)
interface HostBillingDetailService {

    HostBillingDetail get(Serializable id)

    List<HostBillingDetail> list(Map args)

    Long count()

    void delete(Serializable id)

    HostBillingDetail save(HostBillingDetail hostBillingDetail)

}