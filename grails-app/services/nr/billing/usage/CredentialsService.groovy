package nr.billing.usage

import grails.gorm.services.Service

@Service(Credentials)
interface CredentialsService {

    Credentials get(Serializable id)

    List<Credentials> list(Map args)

    Long count()

    void delete(Serializable id)

    Credentials save(Credentials credentials)

}