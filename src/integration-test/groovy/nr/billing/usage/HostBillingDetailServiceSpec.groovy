package nr.billing.usage

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class HostBillingDetailServiceSpec extends Specification {

    HostBillingDetailService hostBillingDetailService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new HostBillingDetail(...).save(flush: true, failOnError: true)
        //new HostBillingDetail(...).save(flush: true, failOnError: true)
        //HostBillingDetail hostBillingDetail = new HostBillingDetail(...).save(flush: true, failOnError: true)
        //new HostBillingDetail(...).save(flush: true, failOnError: true)
        //new HostBillingDetail(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //hostBillingDetail.id
    }

    void "test get"() {
        setupData()

        expect:
        hostBillingDetailService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<HostBillingDetail> hostBillingDetailList = hostBillingDetailService.list(max: 2, offset: 2)

        then:
        hostBillingDetailList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        hostBillingDetailService.count() == 5
    }

    void "test delete"() {
        Long hostBillingDetailId = setupData()

        expect:
        hostBillingDetailService.count() == 5

        when:
        hostBillingDetailService.delete(hostBillingDetailId)
        sessionFactory.currentSession.flush()

        then:
        hostBillingDetailService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        HostBillingDetail hostBillingDetail = new HostBillingDetail()
        hostBillingDetailService.save(hostBillingDetail)

        then:
        hostBillingDetail.id != null
    }
}
