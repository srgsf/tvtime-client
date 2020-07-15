Contributing
============

**Note:** This project is in the [public domain](UNLICENSE). If you contribute any [non-trivial][8]
patches you agree with the following:

    I dedicate any and all copyright interest in this software to the
    public domain. I make this dedication for the benefit of the public at
    large and to the detriment of my heirs and successors. I intend this
    dedication to be an overt act of relinquishment in perpetuity of all
    present and future rights to this software under copyright law.

#### Would you like to contribute code?

When contributing to this repository, please first discuss the change you wish to make via [New feature][6] or a [Bug Report][5] issue.

1. Fork [tvtime-client][1].
2. Create a new branch, **make sure its name contains issue id**, make [great commits + messages][2].   
2.1 If you add or modify endpoints, make sure to also create and run basic tests ensuring they work (see `src/test`).  
2.2 Add `@Json` annotation to every field in the models. All the response and required request fields should be `final` and initialized using constructors.   
3. [Start a pull request][3] against `master`.

#### No code!
* You can [discuss a bug][4] or if it was not reported yet [submit a bug][5]!

Setup
-----

This project is built with [Maven][7], see the `pom.xml` in the root folder.


 [1]: https://github.com/srgsf/tvtime-client/fork
 [2]: http://robots.thoughtbot.com/post/48933156625/5-useful-tips-for-a-better-commit-message
 [3]: https://github.com/srgsf/tvtime-client/compare
 [4]: https://github.com/srgsf/tvtime-client/issues
 [5]: https://github.com/srgsf/tvtime-client/issues/new?assignees=&labels=bug&template=bug_report.md&title=
 [6]: https://github.com/srgsf/tvtime-client/issues/new?assignees=srgsf&labels=enhancement&template=feature_request.md&title=
 [7]: https://maven.apache.org/
 [8]: http://www.gnu.org/prep/maintain/maintain.html#Legally-Significant