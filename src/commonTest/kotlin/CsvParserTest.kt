import buzz.angus.CsvParser
import kotlin.test.Test
import kotlin.test.assertEquals

class CsvParserTest {
    private val csvParser = CsvParser()

    @Test
    fun `parse handles unquoted csv spans`() {
        val data = """
            name,age,department
            John Smith,28,Engineering
        """.trimIndent()

        val parsed = csvParser.parse(data)
        println(parsed)
        assertEquals(
            mapOf(
                "name" to "John Smith",
                "age" to "28",
                "department" to "Engineering"
            ), parsed[0]
        )
    }

    @Test
    fun `parse handles quoted csv spans`() {
        val data = """
            name,age,department
            "John Smith",28,"Engineering"
        """.trimIndent()

        val parsed = csvParser.parse(data)
        assertEquals(
            mapOf(
                "name" to "John Smith",
                "age" to "28",
                "department" to "Engineering"
            ), parsed[0]
        )
    }

    @Test
    fun `parse handles complex csv spans`() {
        val data = """
            "Name","Age","Bio","Favorite Quote","Data"
            "John ""Johnny"" O'Connor",25,"Software Engineer
            Works on:
            - Web Development
            - Mobile Apps
            - Database Design

            Hobbies: Gaming, Reading","He always says: ""Code is poetry, and bugs are just... creative license!""

            His motto: ""If it works, don't touch it.""${'"'},"JSON: {""skills"": [""JS"", ""Python""], ""active"": true}"
            "Sarah Smith",,""${'"'}The Mystery Woman""

            Nobody knows her real age.
            She claims to be:
            - 25 years old
            - 30 years old  
            - ""Ageless""

            Her business card says: ""Age is just a number, darling!""${'"'},"She loves quoting Shakespeare: ""To be or not to be, that is the question.""${'"'},"CSV: ""name,value
            experience,""5+ years""
            location,""NYC, NY""
            status,active""${'"'}
            "",42,"Empty name field test
            This person has no name but:

            - Valid age: 42
            - Complex bio with multiple lines
            - Various punctuation: !@#$%^&*()","Quote with commas, semicolons; and ""quotes"" inside: ""Life is like a CSV file - you never know what's coming next, but you have to parse it anyway.""${'"'},"Mixed: ""key1"": ""value1"", ""key2"": ""value2""${'"'}
            "Bob,Roberts",35,"Name contains comma!
            Other issues:
            - Comma in name field
            - Description spans multiple paragraphs

            First paragraph here.

            Second paragraph here with more content.

            Final paragraph.","His favorite saying: ""Don't put all your eggs in one basket, unless it's a really good basket.""${'"'},"Array: [""item1"", ""item2"", ""item3""]"
        """.trimIndent()

        val parsed = csvParser.parse(data)

        assertEquals(
            mapOf(
                "Name" to "John \"Johnny\" O'Connor",
                "Age" to "25",
                "Bio" to """
                Software Engineer
                Works on:
                - Web Development
                - Mobile Apps
                - Database Design
    
                Hobbies: Gaming, Reading
            """.trimIndent(),
                "Favorite Quote" to """
                He always says: "Code is poetry, and bugs are just... creative license!"
    
                His motto: "If it works, don't touch it."
            """.trimIndent(),
                "Data" to "JSON: {\"skills\": [\"JS\", \"Python\"], \"active\": true}"
            ), parsed[0]
        )

        assertEquals(
            mapOf(
                "Name" to "Sarah Smith",
                "Age" to "",
                "Bio" to """
                "The Mystery Woman"

                Nobody knows her real age.
                She claims to be:
                - 25 years old
                - 30 years old  
                - "Ageless"
    
                Her business card says: "Age is just a number, darling!"
            """.trimIndent(),
                "Favorite Quote" to "She loves quoting Shakespeare: \"To be or not to be, that is the question.\"",
                "Data" to """
                CSV: "name,value
                experience,"5+ years"
                location,"NYC, NY"
                status,active"
            """.trimIndent(),
            ), parsed[1]
        )

        assertEquals(
            mapOf(
                "Name" to "",
                "Age" to "42",
                "Bio" to """
                Empty name field test
                This person has no name but:
    
                - Valid age: 42
                - Complex bio with multiple lines
                - Various punctuation: !@#$%^&*()
            """.trimIndent(),
                "Favorite Quote" to "Quote with commas, semicolons; and \"quotes\" inside: \"Life is like a CSV file - you never know what's coming next, but you have to parse it anyway.\"",
                "Data" to "Mixed: \"key1\": \"value1\", \"key2\": \"value2\""
            ), parsed[2]
        )

        assertEquals(
            mapOf(
                "Name" to "Bob,Roberts",
                "Age" to "35",
                "Bio" to """
                Name contains comma!
                Other issues:
                - Comma in name field
                - Description spans multiple paragraphs
    
                First paragraph here.
    
                Second paragraph here with more content.
    
                Final paragraph.
            """.trimIndent(),
                "Favorite Quote" to "His favorite saying: \"Don't put all your eggs in one basket, unless it's a really good basket.\"",
                "Data" to "Array: [\"item1\", \"item2\", \"item3\"]"
            ), parsed[3]
        )
    }
}
