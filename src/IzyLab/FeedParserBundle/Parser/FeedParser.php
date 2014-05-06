<?php
namespace IzyLab\FeedParserBundle\Parser;

class FeedParser {
    private $parser = null;
    private $observers = array();

    private $data;
    private $post;

    public function startElement($parser, $name, $attrs) {
        switch ($name) {
        // new entry
        case 'ITEM':
        case 'ENTRY':
            $this->post = array(
                'title' => null,
                'link' => null,
                'guid' => null,
                'text' => null,
                'published' => null,
            );
            break;

        // clear data
        case 'PUBDATE':
        case 'PUBLISHED':
        case 'UPDATED':
        case 'DESCRIPTION':
        case 'CONTENT':
        case 'TITLE':
        case 'GUID':
        case 'ID':
            $this->data = '';
            break;

        // get link if in href
        case 'LINK':
            if ($this->post && isset($attrs['HREF'])) {
                $this->post['link'] = $this->data = $attrs['HREF'];
            } else {
                $this->data = '';
            }
            break;
        }
    }

    public function content($parser, $data) {
        $this->data .= $data;
    }

    public function endElement($parser, $name) {
        if ($this->post === null) {
            return;
        }

        switch ($name) {
        // close entry
        case 'ITEM':
        case 'ENTRY':
            // if no GUID generate one
            if (!isset($this->post['guid'])) {
                $this->post['guid'] = md5($this->post['title'].$this->post['published']);
            }
            // notify
            $this->notifyObservers();
            break;

        case 'DESCRIPTION':
        case 'CONTENT':
            $this->post['text'] = $this->data;
            break;

        case 'TITLE':
            $this->post['title'] = $this->data;
            break;

        case 'PUBDATE':
        case 'PUBLISHED':
        case 'UPDATED':
            $time = strtotime($this->data);
            if ($time !== false) {
                $this->post['published'] = $time;
            }
            break;

        case 'ID':
        case 'GUID':
            $this->post['guid'] = md5($this->data);
            break;

        case 'LINK':
            // if link hasn't been set by previous <link href=...
            if (!isset($this->post['link'])) {
                $this->post['link'] = $this->data;
            }
            break;
        }
    }

    public function __construct($observer=null) {
        if ($observer != null) {
            $this->addObserver($observer);
        }
    }

    public function __desctruct() {
        $this->freeParser();
    }

    public function parseReader($reader) {
        while ($data = $reader->read()) {
            $this->parseString($data, $reader->eof());
        }
    }

    public function parseString($string, $eof=true) {
        $this->initParser();
        if (!xml_parse($this->parser, $string, $eof)) {
            throw new InvalidXmlException(
                        xml_error_string(xml_get_error_code($this->parser)),
                        xml_get_error_code($this->parser),
                        xml_get_current_line_number($this->parser),
                        xml_get_current_column_number($this->parser));
        }
    }

    public function addObserver($observer) {
        $this->observers[] = $observer;
    }

    private function notifyObservers() {
        foreach ($this->observers as $obj) {
            $obj->postCreated($this->post);
        }
    }

    private function freeParser() {
        $this->post = null;
        $this->data = '';
        if ($this->parser != null) {
            xml_parser_free($this->parser);
        }
    }

    private function initParser() {
        if ($this->parser != null) {
            return;
        }

        // create parser
        $this->parser = xml_parser_create();

        // init parser
        xml_set_object($this->parser, $this);
        xml_set_element_handler($this->parser, "startElement", "endElement");
        xml_set_character_data_handler($this->parser, 'content');
    }

}
