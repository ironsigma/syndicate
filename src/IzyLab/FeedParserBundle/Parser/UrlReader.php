<?php
namespace IzyLab\FeedParserBundle\Parser;

class UrlReader {
    protected $read;
    protected $url;

    public function __construct($url) {
        $this->url = $url;
    }

    public function read() {
        if ($this->read) {
            return null;
        }

        $curl = curl_init($this->url);
        curl_setopt($curl, CURLOPT_USERAGENT, "Syndicate/0.1.alpha (http://syndicate.axisym3.net/)");
        curl_setopt($curl, CURLOPT_HEADER, 0);
        curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($curl, CURLOPT_TIMEOUT, 10);
        $data = curl_exec($curl);
        curl_close($curl);
        $this->read = true;
        return $data;
    }

    public function eof() {
        return $this->read;
    }

    public function getUrl() {
        return $this->url;
    }
}
