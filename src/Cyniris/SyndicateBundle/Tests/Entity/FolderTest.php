<?php
namespace Cyniris\SyndicateBundle\Tests\Entity;

use Symfony\Bundle\FrameworkBundle\Test\WebTestCase;
use Symfony\Bundle\FrameworkBundle\Console\Application;
use Symfony\Component\Console\Output\NullOutput;
use Symfony\Component\Console\Input\ArrayInput;
use Doctrine\Bundle\DoctrineBundle\Command\DropDatabaseDoctrineCommand;
use Doctrine\Bundle\DoctrineBundle\Command\CreateDatabaseDoctrineCommand;
use Doctrine\Bundle\DoctrineBundle\Command\Proxy\CreateSchemaDoctrineCommand;

use Cyniris\SyndicateBundle\Entity\Folder;
use Cyniris\SyndicateBundle\Entity\User;

class FolderTest extends DoctrineTestCase {
    protected $user;

    public function setUp() {
        parent::setUp();
        $this->user = new User();
        $this->user->setUserName('user1');
        $this->user->setPassword(md5('123'.'mypass'));
        $this->user->setSalt('123');
        $this->em->persist($this->user);
    }

    public function testPersist() {
        $folder = new Folder();
        $folder->setName('My Folder');
        $folder->setUser($this->user);

        $this->em->persist($folder);
        $this->em->flush();

        $this->assertNotNull($folder->getId());

        $fetched = $this->em
            ->getRepository('CynirisSyndicateBundle:Folder')
            ->find($folder->getId());

        $this->assertNotNull($fetched);
        $this->assertEquals('My Folder', $fetched->getName());
        $this->assertEquals($this->user, $fetched->getUser());
    }
}

