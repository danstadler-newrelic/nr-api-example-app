package nr.billing.usage

import grails.gorm.services.Service

@Service(NewRelicAccount)
interface NewRelicAccountService {

    NewRelicAccount get(Serializable id)

    List<NewRelicAccount> list(Map args)

    Long count()

    void delete(Serializable id)

    NewRelicAccount save(NewRelicAccount newRelicAccount)

}