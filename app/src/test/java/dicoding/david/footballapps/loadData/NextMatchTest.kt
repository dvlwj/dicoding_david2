package dicoding.david.footballapps.loadData

import org.junit.Test
import org.mockito.Mockito

class NextMatchTest{
    @Test
    fun testLoadData(){
        val apiRepository = Mockito.mock(NextMatch::class.java)
        apiRepository.loadData()
        Mockito.verify(apiRepository).loadData()
    }
}