package dicoding.david.footballapps.loadData

import org.junit.Test
import org.mockito.Mockito

class FavoriteMatchTest{
    @Test
    fun testLoadData(){
        val checkDB = Mockito.mock(FavoriteMatch::class.java)
        checkDB.loadData()
        Mockito.verify(checkDB).loadData()
    }
}