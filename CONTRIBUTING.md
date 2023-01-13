Contributing
============

If you would like to contribute code to this project you can do so through GitHub by
forking the repository and sending a pull request.

When submitting code, please make every effort to follow existing conventions
and style in order to keep the code as readable as possible. Please also make
sure to add appropriate unit tests, and verify your code compiles and passes 
tests by running `./gradlew test`.

Before your code can be accepted into the project you must also sign the
[Individual Contributor License Agreement (CLA)][1].

Verified commits and releases
=============================

This library deals with security and encryption, and as such, we require that
every code change in it will be [signed as well][2].

These signatures help us attest that code changes were made by real people, and provide
an additional layer of security.

In addition to only allowing verified commits, we also require signing every release tag
with a key that matches one of the allowed signers listed in `config/allowed_release_signers`.  
Release tags are verified during the release workflow in `.github/workflows/release.yaml`.

[1]: https://spreadsheets.google.com/spreadsheet/viewform?formkey=dDViT2xzUHAwRkI3X3k5Z0lQM091OGc6MQ&ndplr=1
[2]: https://docs.github.com/en/authentication/managing-commit-signature-verification/signing-commits