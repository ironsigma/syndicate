<?php
namespace IzyLab\FeedParserBundle\Tests\Parser;

use IzyLab\FeedParserBundle\Parser\UrlReader;
use \Exception;

class UrlReaderTest extends \PHPUnit_Framework_TestCase {
    public function testUrlReader() {
        $ur = new UrlReader('http://www.google.com/robots.txt');
        $text = $ur->read();
        $text = substr($text, 0, 10);

        $this->assertEquals('User-agent', $text);
        $this->assertTrue($ur->eof());

        $text = $ur->read();
        $this->assertNull($text);
    }
}
