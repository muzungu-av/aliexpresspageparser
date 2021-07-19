The program parses one page of aliexpress.
It can do this from different sources of Url or Html-File previously saved (see folder ./files)

_Note_: In the case of a URL target, threads will be used.

#### Requirements
* OOP
* Parse 100 products.
* Do it in a minute
* use Java 8 and libraries
* don't use any Framevorks (e.x. Selenium)

#### Description app.properties

* **target** - may be **url** or **file**
* **target_Url_Resource** - The Url to download the resource
* **target_File_Resource** - HTML-file from ./files
* **elements_container_selector** - selector when we work with html

#### How run
* git clone https://github.com/muzungu-av/aliexpresspageparser.git
* cd ./aliexpresspageparser/
* _to change the target edit the file __./resources/app.properties__ if need_
* mvn clean && mvn package && java -jar ./target/aliexpress-page-parser-1.0-SNAPSHOT.jar