<?php
namespace Cyniris\SyndicateBundle\Tests\Entity;

use Symfony\Bundle\FrameworkBundle\Test\WebTestCase;
use Symfony\Bundle\FrameworkBundle\Console\Application;
use Symfony\Component\Console\Output\NullOutput;
use Symfony\Component\Console\Input\ArrayInput;
use Doctrine\Bundle\DoctrineBundle\Command\DropDatabaseDoctrineCommand;
use Doctrine\Bundle\DoctrineBundle\Command\CreateDatabaseDoctrineCommand;
use Doctrine\Bundle\DoctrineBundle\Command\Proxy\CreateSchemaDoctrineCommand;

use Cyniris\SyndicateBundle\Entity\User;

class UserTest extends DoctrineTestCase {
    public function testPersist() {
        $user = new User();
        $user->setUserName('user1');
        $user->setPassword(md5('123mypass'));
        $user->setSalt('123');

        $this->em->persist($user);
        $this->em->flush();

        $this->assertNotNull($user->getId());

        $fetched = $this->em
            ->getRepository('CynirisSyndicateBundle:User')
            ->find($user->getId());

        $this->assertNotNull($fetched);
        $this->assertEquals('user1', $fetched->getUserName());
        $this->assertEquals(md5('123mypass'), $fetched->getPassword());
        $this->assertEquals('123', $fetched->getSalt());
    }
}

