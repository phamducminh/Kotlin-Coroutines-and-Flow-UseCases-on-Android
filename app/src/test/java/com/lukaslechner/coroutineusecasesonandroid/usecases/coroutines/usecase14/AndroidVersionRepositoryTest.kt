package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase14

import junit.framework.Assert.assertEquals
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Test

class AndroidVersionRepositoryTest {

    @Test
    fun `loadRecentAndroidVersions() should continue to load and store android versions when calling scope gets cancelled`() {
        runTest {

            val fakeDatabase = FakeDatabase()
            val fakeApi = FakeApi()

            val repository = AndroidVersionRepository(
                database = fakeDatabase,
                scope = this,
                api = fakeApi
            )

            val viewModelScope = TestScope(testScheduler)
            val job = viewModelScope.launch {
                repository.loadAndStoreRemoteAndroidVersions()
            }

            runCurrent()

            assertEquals(false, fakeDatabase.insertedIntoDb)
            viewModelScope.cancel()
            assertEquals(true, job.isCancelled)

            advanceTimeBy(1)
            runCurrent()

            assertEquals(true, fakeDatabase.insertedIntoDb)
        }
    }

}