package lt.tlistas.loginn.backend.aspect

import lt.tlistas.core.service.CollaboratorService
import lt.tlistas.loginn.backend.exception.CollaboratorNotFoundException
import lt.tlistas.mobile.number.confirmation.service.AuthenticationService
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.core.annotation.Order
import org.springframework.web.reactive.function.server.ServerRequest

@Aspect
@Order(1)
class CollaboratorAspect(private val collaboratorService: CollaboratorService,
                         private val authenticationService: AuthenticationService) {

    @Before("execution(* lt.tlistas.loginn.backend.handler.ConfirmationHandler.*(..)) && args(req)")
    fun collaboratorExistsByMobileNumber(req: ServerRequest) {
        if (!collaboratorService.existsByMobileNumber(req.pathVariable("mobileNumber")))
            throw CollaboratorNotFoundException()
    }

    @Before("execution(* lt.tlistas.loginn.backend.handler.CollaboratorHandler.*(..))&& args(req)")
    fun collaboratorExistsById(req: ServerRequest) {
        if (!collaboratorService.existsById(getUserId(req)!!))
            throw CollaboratorNotFoundException()
    }

    private fun getUserId(req: ServerRequest) = authenticationService.getUserId(getToken(req))

    private fun getToken(req: ServerRequest) = req.headers().header("auth-token")[0]
}