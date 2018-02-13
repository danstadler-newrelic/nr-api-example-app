package nr.billing.usage

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class ApmInsightsDataServiceSpec extends Specification {

    ApmInsightsDataService apmInsightsDataService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new ApmInsightsData(...).save(flush: true, failOnError: true)
        //new ApmInsightsData(...).save(flush: true, failOnError: true)
        //ApmInsightsData apmInsightsData = new ApmInsightsData(...).save(flush: true, failOnError: true)
        //new ApmInsightsData(...).save(flush: true, failOnError: true)
        //new ApmInsightsData(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //apmInsightsData.id
    }

    void "test get"() {
        setupData()

        expect:
        apmInsightsDataService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<ApmInsightsData> apmInsightsDataList = apmInsightsDataService.list(max: 2, offset: 2)

        then:
        apmInsightsDataList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        apmInsightsDataService.count() == 5
    }

    void "test delete"() {
        Long apmInsightsDataId = setupData()

        expect:
        apmInsightsDataService.count() == 5

        when:
        apmInsightsDataService.delete(apmInsightsDataId)
        sessionFactory.currentSession.flush()

        then:
        apmInsightsDataService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        ApmInsightsData apmInsightsData = new ApmInsightsData()
        apmInsightsDataService.save(apmInsightsData)

        then:
        apmInsightsData.id != null
    }
}
