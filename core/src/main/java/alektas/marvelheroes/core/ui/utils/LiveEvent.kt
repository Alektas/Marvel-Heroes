package alektas.marvelheroes.core.ui.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class LiveEvent<T>() : MutableLiveData<T>() {
    private val isPending = AtomicBoolean(false)

    constructor(value: T) : this() {
        setValue(value)
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, Observer { t: T ->
            if (isPending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        })
    }

    override fun setValue(value: T) {
        isPending.set(true)
        super.setValue(value)
    }

}