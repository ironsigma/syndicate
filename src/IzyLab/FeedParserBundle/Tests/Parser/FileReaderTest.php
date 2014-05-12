<?php
namespace IzyLab\FeedParserBundle\Tests\Parser;

use IzyLab\FeedParserBundle\Parser\FileReader;
use \Exception;

class FileReaderTest extends \PHPUnit_Framework_TestCase {
    public function testFileReader() {
        $file = __FILE__;
        $fr = new FileReader($file);
        $string = $fr->read(5);

        $this->assertEquals('<?php', $string);
        $this->assertFalse($fr->eof());
    }

    public function testInvalidFile() {
        try {
            $fr = new FileReader('fooXXXbar.txt');
            $fr->read();
        } catch (Exception $ex) {
            return;
        }
        $this->fail("Exception expected");
    }
}
