package divascion.marfiandhi.footballapps.model

import java.net.URL

/**
 * Created by Marfiandhi on 10/6/2018.
 */
class ApiRepository {

    fun doRequest(url: String): String {
        return URL(url).readText()
    }
}