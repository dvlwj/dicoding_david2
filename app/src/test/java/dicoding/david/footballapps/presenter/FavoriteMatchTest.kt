package dicoding.david.footballapps.presenter

import dicoding.david.footballapps.view.FavoriteMatch
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