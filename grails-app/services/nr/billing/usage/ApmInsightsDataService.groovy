package nr.billing.usage

import grails.gorm.services.Service

@Service(ApmInsightsData)
interface ApmInsightsDataService {

    ApmInsightsData get(Serializable id)

    List<ApmInsightsData> list(Map args)

    Long count()

    void delete(Serializable id)

    ApmInsightsData save(ApmInsightsData apmInsightsData)

}