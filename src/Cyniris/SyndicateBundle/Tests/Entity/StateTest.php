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
use Cyniris\SyndicateBundle\Entity\User;
use Cyniris\SyndicateBundle\Entity\State;

class StateTest extends DoctrineTestCase {
    protected $post;
    protected $user;

    public function setUp() {
        parent::setUp();
        $now = new \DateTime('Today');
        $this->post = new Post();
        $this->post->setTitle('New 32 Core Chip');
        $this->post->setPublished($now);
        $this->post->setText('The new Intel 32 core chip will be called, Kick@55');
        $this->post->setLink('http://techmuch.com/32corechip');
        $this->post->setGuid(md5($this->post->getLink()));
        $this->em->persist($this->post);

        $this->user = new User();
        $this->user->setUserName('user1');
        $this->user->setPassword(md5('123'.'mypass'));
        $this->user->setSalt('123');
        $this->em->persist($this->user);
    }

    public function testPersist() {

        $state = new State();
        $state->setRead(true);
        $state->setStared(true);
        $state->setUser($this->user);
        $state->setPost($this->post);

        $this->em->persist($state);
        $this->em->flush();

        $this->assertNotNull($state->getId());

        $fetched = $this->em
            ->getRepository('CynirisSyndicateBundle:State')
            ->find($state->getId());

        $this->assertNotNull($fetched);
        $this->assertTrue($state->getRead());
        $this->assertTrue($state->getStared());
        $this->assertEquals($this->post, $fetched->getPost());
        $this->assertEquals($this->user, $fetched->getUser());
    }
}

