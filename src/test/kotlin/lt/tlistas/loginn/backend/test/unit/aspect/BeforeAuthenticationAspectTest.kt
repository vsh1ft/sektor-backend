package lt.tlistas.loginn.backend.test.unit.aspect

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import lt.tlistas.loginn.backend.aspect.BeforeAuthenticationAspect
import lt.tlistas.mobile.number.confirmation.api.exception.AuthenticationException
import lt.tlistas.mobile.number.confirmation.service.AuthenticationService
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.web.reactive.function.server.ServerRequest

@RunWith(MockitoJUnitRunner::class)
class BeforeAuthenticationAspectTest {

    @Mock
    private lateinit var authenticationServiceMock: AuthenticationService

    @Mock
    private lateinit var serverRequestMock: ServerRequest

    @Mock
    private lateinit var headersMock: ServerRequest.Headers

    @Rule
    @JvmField
    val expectedException = ExpectedException.none()!!

    private lateinit var beforeAuthenticationAspect: BeforeAuthenticationAspect

    @Before
    fun `Set up`() {
        beforeAuthenticationAspect = BeforeAuthenticationAspect(authenticationServiceMock)
    }

    @Test
    fun `Does not throw exception when user is authenticated`() {
        val headerList = listOf("saf654as6df48a")
        doReturn(headersMock).`when`(serverRequestMock).headers()
        doReturn(headerList).`when`(headersMock).header("auth-token")
        doReturn(true).`when`(authenticationServiceMock).tokenExists(headerList[0])

        beforeAuthenticationAspect.authenticate(serverRequestMock)

        verify(authenticationServiceMock).tokenExists(headerList[0])
    }

    @Test
    fun `Throws exception when user is unauthenticated`() {
        expectedException.expect(AuthenticationException::class.java)
        val headerList = listOf("saf654as6df48a")
        doReturn(headersMock).`when`(serverRequestMock).headers()
        doReturn(headerList).`when`(headersMock).header("auth-token")
        doReturn(false).`when`(authenticationServiceMock).tokenExists(headerList[0])

        beforeAuthenticationAspect.authenticate(serverRequestMock)
    }
}