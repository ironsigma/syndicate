<?php
namespace IzyLab\FeedParserBundle\Tests\Parser;

use IzyLab\FeedParserBundle\Parser\FeedParser;
use IzyLab\FeedParserBundle\Parser\InvalidXmlException;

class ParserTest extends \PHPUnit_Framework_TestCase {
    protected $postList;
    protected $parser;

    public function setup() {
        $this->postList = array();
        $this->parser = new FeedParser($this);
    }

    public function testEmptyXml() {
        try {
            $xml = '';
            $this->parser->parseString($xml);
        } catch (InvalidXmlException $ex) {
            $this->assertEquals(5, $ex->getCode());
            $this->assertEquals(1, $ex->getLineNumber());
            $this->assertEquals(1, $ex->getColumnNumber());
            return;
        }
        $this->fail("Exception expected");
    }

    public function testParseString() {
        $this->parser->parseString($this->rss2_0);

        $this->assertCount(4, $this->postList);
        $post = $this->postList[0];
        $this->assertEquals('Star City', $post['title']);
        $this->assertEquals('http://liftoff.msfc.nasa.gov/news/2003/news-starcity.asp', $post['link']);
        $this->assertEquals(strtotime("Tue, 03 Jun 2003 09:39:21 GMT"), $post['published']);
        $this->assertEquals(md5("http://liftoff.msfc.nasa.gov/2003/06/03.html#item573"), $post['guid']);
        $this->assertEquals("How do Americans get ready to work with Russians aboard the International "
            . "Space Station? They take a crash course in culture, language and protocol at Russia's "
            . "<a href=\"http://howe.iki.rssi.ru/GCTC/gctc_e.htm\">Star City</a>.", $post['text']);

        $post = $this->postList[1];
        $this->assertNull($post['title']);
        $this->assertNull($post['link']);
        $this->assertEquals(strtotime("Fri, 30 May 2003 11:06:42 GMT"), $post['published']);
        $this->assertEquals(md5("http://liftoff.msfc.nasa.gov/2003/05/30.html#item572"), $post['guid']);
        $this->assertEquals("Sky watchers in Europe, Asia, and parts of Alaska and Canada will "
            . "experience a <a href=\"http://science.nasa.gov/headlines/y2003/30may_solareclipse.htm\">"
            . "partial eclipse of the Sun</a> on Saturday, May 31st.", $post['text']);

        $post = $this->postList[2];
        $this->assertEquals("The Engine That Does More", $post['title']);
        $this->assertEquals("http://liftoff.msfc.nasa.gov/news/2003/news-VASIMR.asp", $post['link']);
        $this->assertEquals(strtotime("Tue, 27 May 2003 08:37:32 GMT"), $post['published']);
        $this->assertEquals(md5("http://liftoff.msfc.nasa.gov/2003/05/27.html#item571"), $post['guid']);
        $this->assertEquals("Before man travels to Mars, NASA hopes to design new engines that will "
            . "let us fly through the Solar System more quickly.  The proposed VASIMR engine would "
            . "do that.", $post['text']);

        $post = $this->postList[3];
        $this->assertEquals("Astronauts' Dirty Laundry", $post['title']);
        $this->assertEquals("http://liftoff.msfc.nasa.gov/news/2003/news-laundry.asp", $post['link']);
        $this->assertEquals(strtotime("Tue, 20 May 2003 08:56:02 GMT"), $post['published']);
        $this->assertEquals(md5("http://liftoff.msfc.nasa.gov/2003/05/20.html#item570"), $post['guid']);
        $this->assertEquals("Compared to earlier spacecraft, the International Space Station has many "
            . "luxuries, but laundry facilities are not one of them.  Instead, astronauts have "
            . "other options.", $post['text']);
    }

    public function postCreated($post) {
        $this->postList[] = $post;
    }

    protected $rss2_0 = <<<EOT
<?xml version="1.0"?>
<rss version="2.0">
   <channel>
      <title>Liftoff News</title>
      <link>http://liftoff.msfc.nasa.gov/</link>
      <description>Liftoff to Space Exploration.</description>
      <language>en-us</language>
      <pubDate>Tue, 10 Jun 2003 04:00:00 GMT</pubDate>
      <lastBuildDate>Tue, 10 Jun 2003 09:41:01 GMT</lastBuildDate>
      <docs>http://blogs.law.harvard.edu/tech/rss</docs>
      <generator>Weblog Editor 2.0</generator>
      <managingEditor>editor@example.com</managingEditor>
      <webMaster>webmaster@example.com</webMaster>
      <item>
         <title>Star City</title>
         <link>http://liftoff.msfc.nasa.gov/news/2003/news-starcity.asp</link>
         <description>How do Americans get ready to work with Russians aboard the International Space Station? They take a crash course in culture, language and protocol at Russia's &lt;a href="http://howe.iki.rssi.ru/GCTC/gctc_e.htm"&gt;Star City&lt;/a&gt;.</description>
         <pubDate>Tue, 03 Jun 2003 09:39:21 GMT</pubDate>
         <guid>http://liftoff.msfc.nasa.gov/2003/06/03.html#item573</guid>
      </item>
      <item>
         <description>Sky watchers in Europe, Asia, and parts of Alaska and Canada will experience a &lt;a href="http://science.nasa.gov/headlines/y2003/30may_solareclipse.htm"&gt;partial eclipse of the Sun&lt;/a&gt; on Saturday, May 31st.</description>
         <pubDate>Fri, 30 May 2003 11:06:42 GMT</pubDate>
         <guid>http://liftoff.msfc.nasa.gov/2003/05/30.html#item572</guid>
      </item>
      <item>
         <title>The Engine That Does More</title>
         <link>http://liftoff.msfc.nasa.gov/news/2003/news-VASIMR.asp</link>
         <description>Before man travels to Mars, NASA hopes to design new engines that will let us fly through the Solar System more quickly.  The proposed VASIMR engine would do that.</description>
         <pubDate>Tue, 27 May 2003 08:37:32 GMT</pubDate>
         <guid>http://liftoff.msfc.nasa.gov/2003/05/27.html#item571</guid>
      </item>
      <item>
         <title>Astronauts' Dirty Laundry</title>
         <link>http://liftoff.msfc.nasa.gov/news/2003/news-laundry.asp</link>
         <description>Compared to earlier spacecraft, the International Space Station has many luxuries, but laundry facilities are not one of them.  Instead, astronauts have other options.</description>
         <pubDate>Tue, 20 May 2003 08:56:02 GMT</pubDate>
         <guid>http://liftoff.msfc.nasa.gov/2003/05/20.html#item570</guid>
      </item>
   </channel>
</rss>
EOT;
}
