package dicoding.david.footballapps

import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat

class UtilsTest {

    @Test
    fun testToSimpleString() {
        val dateFormat = SimpleDateFormat("MM/dd/yyyy")
        val date = dateFormat.parse("10/18/2018")
        assertEquals("Thu, 18 Oct 2018", Utils().toSimpleString(date))
    }
}