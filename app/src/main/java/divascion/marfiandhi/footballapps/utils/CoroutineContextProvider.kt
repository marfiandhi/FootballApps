package divascion.marfiandhi.footballapps.utils

import kotlinx.coroutines.experimental.android.UI
import kotlin.coroutines.experimental.CoroutineContext

/**
 * Created by Marfiandhi on 10/6/2018.
 */
open class CoroutineContextProvider {
    open val main: CoroutineContext by lazy { UI }
}