package com.akinci.recipelist.common.repository

import com.akinci.recipelist.common.helper.Resource
import com.akinci.recipelist.common.network.NetworkChecker
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class BaseRepositoryImplTest {

    @MockK
    lateinit var networkChecker: NetworkChecker

    private lateinit var repository : BaseRepositoryImpl

    @MockK
    lateinit var callBackObj : SuspendingCallBack

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        repository = BaseRepositoryImpl(networkChecker)
    }

    @AfterEach
    fun tearDown() { unmockkAll() }

    class SuspendingCallBack {
        fun contentFulServiceActionCallBack() : String { return "contentFulServiceActionCallBack Body Called Successfully" }
        fun localServiceActionCallBack() : String { return "localServiceActionCallBack Body Called Successfully" }
        fun localBackupActionCallBack() { }
    }

    @Test
    fun `Network is connected, local backup action, contentful service action called returned Result Success`() = runBlockingTest {
        every { networkChecker.isNetworkConnected() } returns true
        coEvery { callBackObj.contentFulServiceActionCallBack() } returns "contentFulServiceActionCallBack Body Called Successfully"
        coEvery { callBackObj.localServiceActionCallBack() } returns "localServiceActionCallBack Body Called Successfully"
        justRun { callBackObj.localBackupActionCallBack() }

        val response = repository.callService(
            contentFulServiceAction = { callBackObj.contentFulServiceActionCallBack() },
            localServiceAction = { callBackObj.localServiceActionCallBack() },
            localBackupAction = { callBackObj.localBackupActionCallBack() }
        )

        verifySequence {
            callBackObj.contentFulServiceActionCallBack()
            callBackObj.localBackupActionCallBack()
        }

        verify (exactly = 0) {
            callBackObj.localServiceActionCallBack()
        }

        assertThat(response).isInstanceOf(Resource.Success::class.java)
        assertThat((response as Resource.Success).data).isEqualTo("contentFulServiceActionCallBack Body Called Successfully")
    }

    @Test
    fun `Network is not connected, local service action called returned Result Success`() = runBlockingTest {
        every { networkChecker.isNetworkConnected() } returns false
        coEvery { callBackObj.contentFulServiceActionCallBack() } returns "contentFulServiceActionCallBack Body Called Successfully"
        coEvery { callBackObj.localServiceActionCallBack() } returns "localServiceActionCallBack Body Called Successfully"
        justRun { callBackObj.localBackupActionCallBack() }

        val response = repository.callService(
            contentFulServiceAction = { callBackObj.contentFulServiceActionCallBack() },
            localServiceAction = { callBackObj.localServiceActionCallBack() },
            localBackupAction = { callBackObj.localBackupActionCallBack() }
        )

        verify {
            callBackObj.localServiceActionCallBack()
        }

        verify (exactly = 0) {
            callBackObj.contentFulServiceActionCallBack()
            callBackObj.localBackupActionCallBack()
        }

        assertThat(response).isInstanceOf(Resource.Success::class.java)
        assertThat((response as Resource.Success).data).isEqualTo("localServiceActionCallBack Body Called Successfully")
    }

    @Test
    fun `Exception occurred, returned Result Error`() = runBlockingTest {
        every { networkChecker.isNetworkConnected() } returns true
        coEvery { callBackObj.contentFulServiceActionCallBack() } returns "contentFulServiceActionCallBack Body Called Successfully"
        coEvery { callBackObj.localServiceActionCallBack() } returns "localServiceActionCallBack Body Called Successfully"
        coEvery { callBackObj.localBackupActionCallBack() } throws Exception("Dummy Exception")

        val response = repository.callService(
            contentFulServiceAction = { callBackObj.contentFulServiceActionCallBack() },
            localServiceAction = { callBackObj.localServiceActionCallBack() },
            localBackupAction = { callBackObj.localBackupActionCallBack() }
        )

        verify {
            callBackObj.contentFulServiceActionCallBack()
            callBackObj.localBackupActionCallBack()
        }

        verify (exactly = 0) {
            callBackObj.localServiceActionCallBack()
        }

        assertThat(response).isInstanceOf(Resource.Error::class.java)
        assertThat((response as Resource.Error).message).isEqualTo("UnExpected Service Exception.")
    }

}