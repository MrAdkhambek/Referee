package r2.llc.referee.main.mvp

data class MainModelImpl(
    override val topScore: Long = 0,
    override val bottomScore: Long = 0
) : MainContract.Model