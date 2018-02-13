package nr.billing.usage

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class HostBillingDetailController {

    HostBillingDetailService hostBillingDetailService
    def usageApiService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]


    def reloadHostDetail() {

      // truncate the table of host detail 
      HostBillingDetail.executeUpdate('delete from HostBillingDetail')

      // TODO get current account from session
      NewRelicAccount acct = NewRelicAccount.first()
      Credentials creds = Credentials.first()

      def apmUsage = usageApiService.apmUsageData(acct.accountId, creds.usageKey)
      // apmUsage.each{println it}

      usageApiService.apmUsageDataWriteToDB(apmUsage)

      redirect(action: "index")

    }


    def index(Integer max) {
        params.max = 50
        respond hostBillingDetailService.list(params), model:[hostBillingDetailCount: hostBillingDetailService.count()]
    }

    def show(Long id) {
        respond hostBillingDetailService.get(id)
    }

    def create() {
        respond new HostBillingDetail(params)
    }

    def save(HostBillingDetail hostBillingDetail) {
        if (hostBillingDetail == null) {
            notFound()
            return
        }

        try {
            hostBillingDetailService.save(hostBillingDetail)
        } catch (ValidationException e) {
            respond hostBillingDetail.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'hostBillingDetail.label', default: 'HostBillingDetail'), hostBillingDetail.id])
                redirect hostBillingDetail
            }
            '*' { respond hostBillingDetail, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond hostBillingDetailService.get(id)
    }

    def update(HostBillingDetail hostBillingDetail) {
        if (hostBillingDetail == null) {
            notFound()
            return
        }

        try {
            hostBillingDetailService.save(hostBillingDetail)
        } catch (ValidationException e) {
            respond hostBillingDetail.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'hostBillingDetail.label', default: 'HostBillingDetail'), hostBillingDetail.id])
                redirect hostBillingDetail
            }
            '*'{ respond hostBillingDetail, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        hostBillingDetailService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'hostBillingDetail.label', default: 'HostBillingDetail'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'hostBillingDetail.label', default: 'HostBillingDetail'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
