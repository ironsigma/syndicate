<?php
namespace Cyniris\SyndicateBundle\Tests\Entity;

use Symfony\Bundle\FrameworkBundle\Test\WebTestCase;
use Symfony\Bundle\FrameworkBundle\Console\Application;
use Symfony\Component\Console\Output\NullOutput;
use Symfony\Component\Console\Input\ArrayInput;
use Doctrine\Bundle\DoctrineBundle\Command\DropDatabaseDoctrineCommand;
use Doctrine\Bundle\DoctrineBundle\Command\CreateDatabaseDoctrineCommand;
use Doctrine\Bundle\DoctrineBundle\Command\Proxy\CreateSchemaDoctrineCommand;

use Cyniris\SyndicateBundle\Entity\Feed;

class FeedTest extends DoctrineTestCase {

    public function testPersist() {
        $feed = new Feed();
        $feed->setName('My Feed');
        $feed->setUrl('http://myfeed.com/rss');
        $feed->setUpdateFrequency(60);

        $this->em->persist($feed);
        $this->em->flush();

        $this->assertNotNull($feed->getId());

        $fetched = $this->em
            ->getRepository('CynirisSyndicateBundle:Feed')
            ->find($feed->getId());

        $this->assertNotNull($fetched);
        $this->assertEquals('My Feed', $fetched->getName());
        $this->assertEquals('http://myfeed.com/rss', $fetched->getUrl());
        $this->assertEquals(60, $fetched->getUpdateFrequency());
    }
}

