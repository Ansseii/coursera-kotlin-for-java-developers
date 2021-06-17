import org.junit.Assert
import org.junit.Test

class TasksTest {

    private fun testIdentifier(sample: String, expected: Boolean) {
        val check = isValidIdentifier(sample)
        Assert.assertEquals("Wrong answer for sample: '$sample'", expected, check)
    }

    @Test
    fun `identifier is valid`() {
        testIdentifier("name", true)
        testIdentifier("_name", true)
        testIdentifier("_12", true)
    }

    @Test
    fun `identifier is not valid`() {
        testIdentifier("", false)
        testIdentifier("012", false)
        testIdentifier("no$", false)
        testIdentifier("^no321", false)
    }
}