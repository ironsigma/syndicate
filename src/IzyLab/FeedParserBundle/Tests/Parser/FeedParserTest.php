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

    public function testEmptyChannel() {
        $xml = <<<XML
<rss version="2.0"/>
XML;
        $this->parser->parseString($xml);
        $this->assertCount(0, $this->postList);

        $xml = <<<XML
<rss version="2.0">
<channel/>
</rss>
XML;
        $this->parser->parseString($xml);
        $this->assertCount(0, $this->postList);

        $xml = <<<XML
<rss version="2.0">
<channel>
    <title>Liftoff News</title>
</channel>
</rss>
XML;
        $this->parser->parseString($xml);
        $this->assertCount(0, $this->postList);
    }

    public function testLink() {
        $xml = <<<XML
<rss version="2.0">
<channel>
    <item/>
    <item>
        <link>http://liftoff.msfc.nasa.gov/news/2003/news-starcity.asp</link>
    </item>
    <item>
        <link/>
    </item>
    <item>
        <link href="http://liftoff.msfc.nasa.gov/news/2007/news-starcity.asp" />
    </item>
</channel>
</rss>
XML;
        $this->parser->parseString($xml);
        $this->assertCount(4, $this->postList);
        $this->assertNull($this->postList[0]['link']);
        $this->assertEquals('http://liftoff.msfc.nasa.gov/news/2003/news-starcity.asp', $this->postList[1]['link']);
        $this->assertEmpty($this->postList[2]['link']);
        $this->assertEquals('http://liftoff.msfc.nasa.gov/news/2007/news-starcity.asp', $this->postList[3]['link']);
    }

    public function testGuid() {
        $xml = <<<XML
<rss version="2.0">
<channel>
    <item/>
    <item>
        <guid>http://liftoff.msfc.nasa.gov/news/2003/news-starcity.asp</guid>
    </item>
    <item>
        <title>Star City</title>
    </item>
    <item>
        <title>Star City</title>
        <pubDate>Tue, 03 Jun 2003 09:39:21 GMT</pubDate>
    </item>
</channel>
</rss>
XML;
        $this->parser->parseString($xml);
        $this->assertCount(4, $this->postList);
        $this->assertEquals(md5(null.null), $this->postList[0]['guid']);
        $this->assertEquals(md5('http://liftoff.msfc.nasa.gov/news/2003/news-starcity.asp'), $this->postList[1]['guid']);
        $this->assertEquals(md5('Star City'.null), $this->postList[2]['guid']);
        $this->assertEquals(md5('Star City'.strtotime("Tue, 03 Jun 2003 09:39:21 GMT")), $this->postList[3]['guid']);
    }

    public function testPubDate() {
        $xml = <<<XML
<rss version="2.0">
<channel>
    <item>
        <pubDate>Tue, 03 Jun 2003 09:39:21 GMT</pubDate>
    </item>
    <item/>
    <item>
        <pubDate></pubDate>
    </item>
    <item>
        <pubDate>Foo</pubDate>
    </item>
    <item>
        <pubDate>Friday</pubDate>
    </item>
</channel>
</rss>
XML;
        $this->parser->parseString($xml);
        $this->assertCount(5, $this->postList);
        $this->assertEquals(strtotime("Tue, 03 Jun 2003 09:39:21 GMT"), $this->postList[0]['published']);
        $this->assertNull($this->postList[1]['published']);
        $this->assertNull($this->postList[2]['published']);
        $this->assertNull($this->postList[3]['published']);
        $this->assertNotNull($this->postList[4]['published']);
    }

    public function testParseFeeds() {
        $feeds = array($this->rss2_0, $this->atom);

        foreach ($feeds as $xml) {
            $this->parser->parseString($xml);

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
    }

    public function testParseChunks() {
        $this->parser->parseString("<?xml version=\"1.0\"?>", false);
        $this->parser->parseString("<rss version=\"2.0\">", false);
        $this->parser->parseString("<channel>", false);
        $this->parser->parseString("<item>", false);
        $this->parser->parseString("<title>Star City</title>", false);
        $this->parser->parseString("<link>http://liftoff.msfc.nasa.gov/news/2003/news-starcity.asp</link>", false);
        $this->parser->parseString("<description>How do Americans get ready to work with Russians aboard the ", false);
        $this->parser->parseString("International Space Station? They take a crash course in culture, ", false);
        $this->parser->parseString("language and protocol at Russia's &lt;a href=\"http://howe.iki.", false);
        $this->parser->parseString("rssi.ru/GCTC/gctc_e.htm\"&gt;Star City&lt;/a&gt;.</description>", false);
        $this->parser->parseString("<pubDate>Tue, 03 Jun 2003 09:39:21 GMT</pubDate>", false);
        $this->parser->parseString("<guid>http://liftoff.msfc.nasa.gov/2003/06/03.html#item573</guid>", false);
        $this->parser->parseString("</item>", false);
        $this->parser->parseString("</channel>", false);
        $this->parser->parseString("</rss>", true);

        $this->assertCount(1, $this->postList);
        $post = $this->postList[0];
        $this->assertEquals('Star City', $post['title']);
        $this->assertEquals('http://liftoff.msfc.nasa.gov/news/2003/news-starcity.asp', $post['link']);
        $this->assertEquals(strtotime("Tue, 03 Jun 2003 09:39:21 GMT"), $post['published']);
        $this->assertEquals(md5("http://liftoff.msfc.nasa.gov/2003/06/03.html#item573"), $post['guid']);
        $this->assertEquals("How do Americans get ready to work with Russians aboard the International "
            . "Space Station? They take a crash course in culture, language and protocol at Russia's "
            . "<a href=\"http://howe.iki.rssi.ru/GCTC/gctc_e.htm\">Star City</a>.", $post['text']);
    }

    public function testFileParse() {
        $fileReader = $this->getMock(
            'IzyLab\FeedParserBundle\Parser\FileReader',
            array('read', 'eof'),
            array('myfile.xml'));

        $fileReader->expects($this->at(0))
            ->method('read')
            ->will($this->returnValue('<rss><channel><item><title>Star</title></item></channel></rss>'));

        $fileReader->expects($this->at(1))
            ->method('read')
            ->will($this->returnValue(null));

        $fileReader->expects($this->once())
            ->method('eof')
            ->will($this->returnValue(true));

        $this->assertEquals('myfile.xml', $fileReader->getFile());

        $this->parser->parseReader($fileReader);
        $this->assertCount(1, $this->postList);
        $this->assertEquals('Star', $this->postList[0]['title']);
    }

    public function testUrlParse() {
        $urlReader = $this->getMock(
            'IzyLab\FeedParserBundle\Parser\UrlReader',
            array('read', 'eof'),
            array('http://yahoo.com/myrss.php'));

        $urlReader->expects($this->at(0))
            ->method('read')
            ->will($this->returnValue('<rss><channel><item><title>Star</title></item></channel></rss>'));

        $urlReader->expects($this->at(1))
            ->method('read')
            ->will($this->returnValue(null));

        $urlReader->expects($this->once())
            ->method('eof')
            ->will($this->returnValue(true));

        $this->assertEquals('http://yahoo.com/myrss.php', $urlReader->getUrl());

        $this->parser->parseReader($urlReader);
        $this->assertCount(1, $this->postList);
        $this->assertEquals('Star', $this->postList[0]['title']);
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

    protected $atom = <<<EOT
<?xml version="1.0"?>
<feed xmlns="http://www.w3.org/2005/Atom">
  <title>Liftoff News</title>
  <link href="http://liftoff.msfc.nasa.gov/"></link>
  <entry>
     <id>http://liftoff.msfc.nasa.gov/2003/06/03.html#item573</id>
     <title>Star City</title>
     <link href="http://liftoff.msfc.nasa.gov/news/2003/news-starcity.asp"/>
     <content type="text">How do Americans get ready to work with Russians aboard the International Space Station? They take a crash course in culture, language and protocol at Russia's &lt;a href="http://howe.iki.rssi.ru/GCTC/gctc_e.htm"&gt;Star City&lt;/a&gt;.</content>
     <updated>Tue, 03 Jun 2003 09:39:21 GMT</updated>
  </entry>
  <entry>
     <content type="text">Sky watchers in Europe, Asia, and parts of Alaska and Canada will experience a &lt;a href="http://science.nasa.gov/headlines/y2003/30may_solareclipse.htm"&gt;partial eclipse of the Sun&lt;/a&gt; on Saturday, May 31st.</content>
     <updated>Fri, 30 May 2003 11:06:42 GMT</updated>
     <id>http://liftoff.msfc.nasa.gov/2003/05/30.html#item572</id>
  </entry>
  <entry>
     <id>http://liftoff.msfc.nasa.gov/2003/05/27.html#item571</id>
     <title>The Engine That Does More</title>
     <link href="http://liftoff.msfc.nasa.gov/news/2003/news-VASIMR.asp"/>
     <content type="text">Before man travels to Mars, NASA hopes to design new engines that will let us fly through the Solar System more quickly.  The proposed VASIMR engine would do that.</content>
     <updated>Tue, 27 May 2003 08:37:32 GMT</updated>
  </entry>
  <entry>
     <id>http://liftoff.msfc.nasa.gov/2003/05/20.html#item570</id>
     <title>Astronauts' Dirty Laundry</title>
     <link href="http://liftoff.msfc.nasa.gov/news/2003/news-laundry.asp"/>
     <content type="text">Compared to earlier spacecraft, the International Space Station has many luxuries, but laundry facilities are not one of them.  Instead, astronauts have other options.</content>
     <updated>Tue, 20 May 2003 08:56:02 GMT</updated>
  </entry>
</feed>
EOT;
}
