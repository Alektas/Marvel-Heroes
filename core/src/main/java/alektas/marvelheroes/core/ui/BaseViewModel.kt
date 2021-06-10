package alektas.marvelheroes.core.ui

import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    open fun onCreate() {}

    open fun onStart() {}

    open fun onResume() {}

    open fun onPause() {}

    open fun onStop() {}

}
