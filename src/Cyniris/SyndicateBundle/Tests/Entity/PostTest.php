<?php
namespace Cyniris\SyndicateBundle\Tests\Entity;

use Symfony\Bundle\FrameworkBundle\Test\WebTestCase;
use Symfony\Bundle\FrameworkBundle\Console\Application;
use Symfony\Component\Console\Output\NullOutput;
use Symfony\Component\Console\Input\ArrayInput;
use Doctrine\Bundle\DoctrineBundle\Command\DropDatabaseDoctrineCommand;
use Doctrine\Bundle\DoctrineBundle\Command\CreateDatabaseDoctrineCommand;
use Doctrine\Bundle\DoctrineBundle\Command\Proxy\CreateSchemaDoctrineCommand;

use Cyniris\SyndicateBundle\Entity\Post;
use Cyniris\SyndicateBundle\Entity\Feed;

class PostTest extends DoctrineTestCase {
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
        $post = new Post();
        $post->setTitle('New 32 Core Chip');
        $post->setPublished($now);
        $post->setText('The new Intel 32 core chip will be called, Kick@55');
        $post->setLink('http://techmuch.com/32corechip');
        $post->setGuid(md5($post->getLink()));
        $post->setFeed($this->feed);

        $this->em->persist($post);
        $this->em->flush();

        $this->assertNotNull($post->getId());

        $fetched = $this->em
            ->getRepository('CynirisSyndicateBundle:Post')
            ->find($post->getId());

        $this->assertNotNull($fetched);
        $this->assertEquals('New 32 Core Chip', $fetched->getTitle());
        $this->assertEquals($now, $fetched->getPublished());
        $this->assertEquals('The new Intel 32 core chip will be called, Kick@55', $fetched->getText());
        $this->assertEquals('http://techmuch.com/32corechip', $fetched->getLink());
        $this->assertEquals(md5($post->getLink()), $fetched->getGuid());
        $this->assertEquals($this->feed, $fetched->getFeed());
    }
}

