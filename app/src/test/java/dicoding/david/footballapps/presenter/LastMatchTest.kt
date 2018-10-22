package dicoding.david.footballapps.presenter

import dicoding.david.footballapps.view.LastMatch
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class LastMatchTest{
    @Test
    fun testLoadData(){
        val apiRepository = mock(LastMatch::class.java)
        apiRepository.loadData()
        verify(apiRepository).loadData()
    }
}