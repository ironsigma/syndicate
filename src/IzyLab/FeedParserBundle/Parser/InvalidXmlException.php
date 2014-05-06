<?php
namespace IzyLab\FeedParserBundle\Parser;

use \Exception;

class InvalidXmlException extends Exception {
    protected $lineNumber;
    protected $columnNumber;

    public function __construct($message, $code, $lineNumber, $columnNumber) {
        parent::__construct($message, $code);
        $this->lineNumber = $lineNumber;
        $this->columnNumber = $columnNumber;
    }

    public function __toString() {
        return sprintf("%s, at line %d, column %d, code %d",
            $this->getMessage(), $this->lineNumber,
            $this->columnNumber, $this->getCode());
    }

    public function getLineNumber() {
        return $this->lineNumber;
    }

    public function getColumnNumber() {
        return $this->columnNumber;
    }
}
