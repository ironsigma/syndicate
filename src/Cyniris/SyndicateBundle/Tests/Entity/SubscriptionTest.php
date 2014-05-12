<?php
namespace Cyniris\SyndicateBundle\Tests\Entity;

use Symfony\Bundle\FrameworkBundle\Test\WebTestCase;
use Symfony\Bundle\FrameworkBundle\Console\Application;
use Symfony\Component\Console\Output\NullOutput;
use Symfony\Component\Console\Input\ArrayInput;
use Doctrine\Bundle\DoctrineBundle\Command\DropDatabaseDoctrineCommand;
use Doctrine\Bundle\DoctrineBundle\Command\CreateDatabaseDoctrineCommand;
use Doctrine\Bundle\DoctrineBundle\Command\Proxy\CreateSchemaDoctrineCommand;

use Cyniris\SyndicateBundle\Entity\Subscription;
use Cyniris\SyndicateBundle\Entity\Feed;
use Cyniris\SyndicateBundle\Entity\Folder;

class SubscriptionTest extends DoctrineTestCase {
    protected $feed;
    protected $folder;

    public function setUp() {
        parent::setUp();
        $this->feed = new Feed();
        $this->feed->setName("TechMunch");
        $this->feed->setUrl('http://techmuch.com/');
        $this->feed->setUpdateFrequency(10);
        $this->em->persist($this->feed);

        $this->folder = new Folder();
        $this->folder->setName('My Folder');
        $this->em->persist($this->folder);
    }

    public function testPersist() {
        $now = new \DateTime('Today');
        $sub = new Subscription();
        $sub->setFeed($this->feed);
        $sub->setFolder($this->folder);

        $this->em->persist($sub);
        $this->em->flush();

        $this->assertNotNull($sub->getId());

        $fetched = $this->em
            ->getRepository('CynirisSyndicateBundle:Subscription')
            ->find($sub->getId());

        $this->assertNotNull($fetched);
        $this->assertEquals($this->feed, $fetched->getFeed());
        $this->assertEquals($this->folder, $fetched->getFolder());
    }
}

