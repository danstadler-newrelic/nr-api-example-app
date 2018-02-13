package nr.billing.usage

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class CredentialsController {

    CredentialsService credentialsService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond credentialsService.list(params), model:[credentialsCount: credentialsService.count()]
    }

    def show(Long id) {
        respond credentialsService.get(id)
    }

    def create() {
        respond new Credentials(params)
    }

    def save(Credentials credentials) {
        if (credentials == null) {
            notFound()
            return
        }

        try {
            credentialsService.save(credentials)
        } catch (ValidationException e) {
            respond credentials.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'credentials.label', default: 'Credentials'), credentials.id])
                redirect credentials
            }
            '*' { respond credentials, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond credentialsService.get(id)
    }

    def update(Credentials credentials) {
        if (credentials == null) {
            notFound()
            return
        }

        try {
            credentialsService.save(credentials)
        } catch (ValidationException e) {
            respond credentials.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'credentials.label', default: 'Credentials'), credentials.id])
                redirect credentials
            }
            '*'{ respond credentials, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        credentialsService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'credentials.label', default: 'Credentials'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'credentials.label', default: 'Credentials'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
