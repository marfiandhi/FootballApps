package divascion.marfiandhi.footballapps.utils

import kotlinx.coroutines.experimental.Unconfined
import kotlin.coroutines.experimental.CoroutineContext

/**
 * Created by Marfiandhi on 10/11/2018.
 */
class TestContextProvider : CoroutineContextProvider() {
    override val main: CoroutineContext = Unconfined
}