package nr.billing.usage

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class NewRelicAccountServiceSpec extends Specification {

    NewRelicAccountService newRelicAccountService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new NewRelicAccount(...).save(flush: true, failOnError: true)
        //new NewRelicAccount(...).save(flush: true, failOnError: true)
        //NewRelicAccount newRelicAccount = new NewRelicAccount(...).save(flush: true, failOnError: true)
        //new NewRelicAccount(...).save(flush: true, failOnError: true)
        //new NewRelicAccount(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //newRelicAccount.id
    }

    void "test get"() {
        setupData()

        expect:
        newRelicAccountService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<NewRelicAccount> newRelicAccountList = newRelicAccountService.list(max: 2, offset: 2)

        then:
        newRelicAccountList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        newRelicAccountService.count() == 5
    }

    void "test delete"() {
        Long newRelicAccountId = setupData()

        expect:
        newRelicAccountService.count() == 5

        when:
        newRelicAccountService.delete(newRelicAccountId)
        sessionFactory.currentSession.flush()

        then:
        newRelicAccountService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        NewRelicAccount newRelicAccount = new NewRelicAccount()
        newRelicAccountService.save(newRelicAccount)

        then:
        newRelicAccount.id != null
    }
}
