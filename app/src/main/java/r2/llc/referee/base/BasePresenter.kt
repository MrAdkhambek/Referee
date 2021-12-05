package r2.llc.referee.base

interface BasePresenter<V> {
    fun attachView(view: V)
    fun detachView()
}