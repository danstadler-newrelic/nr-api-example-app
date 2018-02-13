package nr.billing.usage

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class CredentialsServiceSpec extends Specification {

    CredentialsService credentialsService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Credentials(...).save(flush: true, failOnError: true)
        //new Credentials(...).save(flush: true, failOnError: true)
        //Credentials credentials = new Credentials(...).save(flush: true, failOnError: true)
        //new Credentials(...).save(flush: true, failOnError: true)
        //new Credentials(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //credentials.id
    }

    void "test get"() {
        setupData()

        expect:
        credentialsService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Credentials> credentialsList = credentialsService.list(max: 2, offset: 2)

        then:
        credentialsList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        credentialsService.count() == 5
    }

    void "test delete"() {
        Long credentialsId = setupData()

        expect:
        credentialsService.count() == 5

        when:
        credentialsService.delete(credentialsId)
        sessionFactory.currentSession.flush()

        then:
        credentialsService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Credentials credentials = new Credentials()
        credentialsService.save(credentials)

        then:
        credentials.id != null
    }
}
