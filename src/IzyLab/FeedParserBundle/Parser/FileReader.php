<?php
namespace IzyLab\FeedParserBundle\Parser;

class FileReader {
    protected $file;
    protected $fp;

    public function __construct($file) {
        $this->file = $file;
    }

    public function __destruct() {
        if ($this->fp) {
            fclose($this->fp);
        }
    }

    public function read($byteCount=4096) {
        if (!$this->fp) {
            $this->open();
        }
        return fread($this->fp, $byteCount);
    }

    public function eof() {
        return feof($this->fp);
    }

    public function getFile() {
        return $this->file;
    }

    protected function open() {
        $this->fp = @fopen($this->file, 'r');
        if ($this->fp === false) {
            throw new \Exception('Could not open input file: '. $this->file);
        }
    }
}
