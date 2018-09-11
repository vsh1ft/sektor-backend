package lt.boldadmin.sektor.backend.route

import lt.boldadmin.sektor.backend.handler.identityconfirmed.WorkLogHandler
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.BodyInserters.fromObject
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.router

class WorkLogRoutes(private val workLogHandler: WorkLogHandler) {

    fun router() = router {
        "/worklog".nest {
            accept(APPLICATION_JSON).nest {
                POST("/log-by-location", workLogHandler::logByLocation)
                GET("/project-name-of-started-work", workLogHandler::getProjectNameOfStartedWork)
                GET("/has-work-started", workLogHandler::hasWorkStarted)
                GET("/collaborator/interval-ids", workLogHandler::getIntervalIdsByCollaborator)
                "/interval".nest {
                    POST("/{intervalId}/update-description", workLogHandler::updateDescriptionByIntervalId)
                    GET("/{intervalId}/endpoints", workLogHandler::getIntervalEndpointsByIntervalId)
                    GET("/{intervalId}/description", workLogHandler::getDescriptionByIntervalId)
                    GET("/{intervalIds}/durations-sum", workLogHandler::getDurationsSumByIntervalIds)
                }
            }
        }
        GET("/healthy") { ok().body(fromObject(true)) }
    }
}