import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat

infix fun <T> T.`should be result of`(taskResult: () -> T) {
    assertThat(this, `is`(equalTo(taskResult())))
}