package divascion.marfiandhi.footballapps.model

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

/**
 * Created by Marfiandhi on 10/11/2018.
 */
class ApiRepositoryTest {
    @Test
    fun testDoRequest() {
        val apiRepository = mock(ApiRepository::class.java)
        val url = "https://www.thesportsdb.com/api/v1/json/1/eventspastleague.php?id=4328"
        apiRepository.doRequest(url)
        verify(apiRepository).doRequest(url)
    }
}