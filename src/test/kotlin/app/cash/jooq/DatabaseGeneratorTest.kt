package app.cash.jooq

import org.jooq.codegen.GenerationTool
import org.jooq.meta.jaxb.Configuration
import org.jooq.meta.jaxb.Database
import org.jooq.meta.jaxb.ForcedType
import org.jooq.meta.jaxb.Generate
import org.jooq.meta.jaxb.Generator
import org.jooq.meta.jaxb.Property
import org.jooq.meta.jaxb.Target
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

/**
 * This test will generate some jOOQ model classes from the database schema described in
 * the `src/test/resources/database.sql` file.
 *
 * The generator configuration specified here uses
 * the [EncryptionAwareJavaGenerator] code generator.
 * There are a few things we'd like to test here:
 * 1. columns that have a VARBINARY type, and a [org.jooq.Converter] associated with them
 *    shouldn't be encrypted.
 * 2. columns that have a VARBINARY type, no converter associated with them,
 *    and no encryption key associated with the column name shouldn't be encrypted.
 * 3. columns that have a VARBINARY type, and no converter associated with them should be encrypted.
 *
 * When this test runs, it'll write the generated code into the `jooq-generated` directory.
 * Changes to the `database.sql` file mentioned above will result in changes to the generated code.
 * Please make sure that schema specified in `database.sql` and the generated code are in sync
 * by committing any changes to the generated code.
 */
internal class DatabaseGeneratorTest {

  @Test
  fun main() {
    val config = Configuration()
      .withGenerator(
        Generator()
          .withTarget(
            Target()
              .withDirectory("jooq-generated")
              .withPackageName("app.cash.jooq.generated")
          )
          .withGenerate(
            Generate()
              .withDaos(true)
              .withComments(false)
          )
          .withName("app.cash.jooq.EncryptionAwareJavaGenerator")
          .withDatabase(
            Database()
              .withName("org.jooq.meta.extensions.ddl.DDLDatabase")
              .withProperties(
                // Specify the location of your SQL script.
                // You may use ant-style file matching, e.g. /path/**/to/*.sql
                //
                // Where:
                // - ** matches any directory subtree
                // - * matches any number of characters in a directory / file name
                // - ? matches a single character in a directory / file name
                Property()
                  .withKey("scripts")
                  .withValue("src/test/resources/database.sql"),

                // The sort order of the scripts within a directory, where:
                //
                // - semantic: sorts versions, e.g. v-3.10.0 is after v-3.9.0 (default)
                // - alphanumeric: sorts strings, e.g. v-3.10.0 is before v-3.9.0
                // - flyway: sorts files the same way as flyway does
                // - none: doesn't sort directory contents after fetching them from the directory
                Property()
                  .withKey("sort")
                  .withValue("semantic"),

                // The default schema for unqualified objects:
                //
                // - public: all unqualified objects are located in the PUBLIC (upper case) schema
                // - none: all unqualified objects are located in the default schema (default)
                //
                // This configuration can be overridden with the schema mapping feature
                Property()
                  .withKey("unqualifiedSchema")
                  .withValue("none"),

                // The default name case for unquoted objects:
                //
                // - as_is: unquoted object names are kept unquoted
                // - upper: unquoted object names are turned into upper case (most databases)
                // - lower: unquoted object names are turned into lower case (e.g. PostgreSQL)
                Property()
                  .withKey("defaultNameCase")
                  .withValue("as_is"),

                // Turn on/off ignoring contents between such tokens. Defaults to true
                Property()
                  .withKey("parseIgnoreComments")
                  .withValue("true")
              )
              .withForcedTypes(
                ForcedType()
                  .withIncludeExpression("forced_type_customers\\.email_address")
                  .withUserType("String")
                  .withConverter(
                    "org.jooq.Converter.ofNullable(" +
                      "byte[].class, " +
                      "String.class," +
                      "source -> new String(source), " +
                      "source -> source.getBytes()" +
                      ")"
                  )
              )
          )
      )
      .withBasedir("src/test")

    assertDoesNotThrow { GenerationTool.generate(config) }
  }
}
