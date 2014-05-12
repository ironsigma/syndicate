<?php
namespace Cyniris\SyndicateBundle\Tests\Entity;

use Symfony\Bundle\FrameworkBundle\Test\WebTestCase;
use Symfony\Bundle\FrameworkBundle\Console\Application;
use Symfony\Component\Console\Output\NullOutput;
use Symfony\Component\Console\Input\ArrayInput;
use Doctrine\Bundle\DoctrineBundle\Command\DropDatabaseDoctrineCommand;
use Doctrine\Bundle\DoctrineBundle\Command\CreateDatabaseDoctrineCommand;
use Doctrine\Bundle\DoctrineBundle\Command\Proxy\CreateSchemaDoctrineCommand;

use Cyniris\SyndicateBundle\Entity\Update;
use Cyniris\SyndicateBundle\Entity\Feed;

class UpdateTest extends DoctrineTestCase {
    protected $feed;

    public function setUp() {
        parent::setUp();
        $this->feed = new Feed();
        $this->feed->setName("TechMunch");
        $this->feed->setUrl('http://techmuch.com/');
        $this->feed->setUpdateFrequency(10);
        $this->em->persist($this->feed);
    }

    public function testPersist() {
        $now = new \DateTime('Today');
        $update = new Update();
        $update->setUpdated($now);
        $update->setTotalCount(1234);
        $update->setNewCount(245);
        $update->setFeed($this->feed);

        $this->em->persist($update);
        $this->em->flush();

        $this->assertNotNull($update->getId());

        $fetched = $this->em
            ->getRepository('CynirisSyndicateBundle:Update')
            ->find($update->getId());

        $this->assertNotNull($fetched);
        $this->assertEquals($now, $fetched->getUpdated());
        $this->assertEquals(1234, $fetched->getTotalCount());
        $this->assertEquals(245, $fetched->getNewCount());
        $this->assertEquals($this->feed, $fetched->getFeed());
    }
}

