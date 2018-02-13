package nr.billing.usage

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class NewRelicAccountController {

    NewRelicAccountService newRelicAccountService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond newRelicAccountService.list(params), model:[newRelicAccountCount: newRelicAccountService.count()]
    }

    def show(Long id) {
        respond newRelicAccountService.get(id)
    }

    def create() {
        respond new NewRelicAccount(params)
    }

    def save(NewRelicAccount newRelicAccount) {
        if (newRelicAccount == null) {
            notFound()
            return
        }

        try {
            newRelicAccountService.save(newRelicAccount)
        } catch (ValidationException e) {
            respond newRelicAccount.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'newRelicAccount.label', default: 'NewRelicAccount'), newRelicAccount.id])
                redirect newRelicAccount
            }
            '*' { respond newRelicAccount, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond newRelicAccountService.get(id)
    }

    def update(NewRelicAccount newRelicAccount) {
        if (newRelicAccount == null) {
            notFound()
            return
        }

        try {
            newRelicAccountService.save(newRelicAccount)
        } catch (ValidationException e) {
            respond newRelicAccount.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'newRelicAccount.label', default: 'NewRelicAccount'), newRelicAccount.id])
                redirect newRelicAccount
            }
            '*'{ respond newRelicAccount, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        newRelicAccountService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'newRelicAccount.label', default: 'NewRelicAccount'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'newRelicAccount.label', default: 'NewRelicAccount'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
