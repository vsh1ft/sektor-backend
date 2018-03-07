package lt.tlistas.loginn.backend.handler

import lt.tlistas.core.api.type.Location
import lt.tlistas.core.service.CollaboratorService
import lt.tlistas.core.service.LocationWorkLogService
import lt.tlistas.crowbar.service.AuthenticationService
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyToMono
import reactor.core.publisher.Mono

open class WorkLogHandler(private val collaboratorService: CollaboratorService,
                               private val locationWorkLogService: LocationWorkLogService,
                               private val authenticationService: AuthenticationService) {

    open fun logByLocation(req: ServerRequest): Mono<ServerResponse> =
            req.bodyToMono<Location>()
                    .doOnNext { locationWorkLogService.logWork(getCollaborator(req), it) }
                    .flatMap { ServerResponse.ok().build() }

    private fun getCollaborator(req: ServerRequest) = collaboratorService.getById(getUserId(req)!!)

    private fun getUserId(req: ServerRequest) = authenticationService.getUserId(getToken(req))

    private fun getToken(req: ServerRequest) = req.headers().header("auth-token")[0]
}