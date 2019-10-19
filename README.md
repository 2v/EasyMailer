# EasyMailer

EasyMailer is a HTML parser that will take a file written in EasyMailer language and turn it into an email newsletter.
For example, a a file written in EasyMailer might look like:

```
[theme] white_blue_liquid (a large library of themes will come by default, and more can be created by contributors)
[logo] http://example.com/logo.png
[banner] http://example.com/banner.png
[title] EasyMailer Newsletter!
[centered-image] http://example.com/image.png
[paragraph] Hello, this is the first EasyMailer newsletter. I hope you enjoy!
[blue-white-divider-start]
[paragraph] Elements of this newsletter have been chosen by typing the name of various standalone HTML elements, and
then they are parsed together into an HTML newsletter. Thats pretty cool!
[blue-white-divider-end]
[footer]
```

